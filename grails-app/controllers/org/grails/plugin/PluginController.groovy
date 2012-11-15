package org.grails.plugin

import grails.converters.*
import grails.gorm.*
import org.grails.taggable.*
import org.apache.commons.codec.digest.DigestUtils
import org.grails.tags.TagNotFoundException
import org.grails.common.ApprovalStatus
import org.compass.core.engine.SearchEngineQueryParseException

class PluginController {

    def dateService
    def pluginService
    def tagService
    def wikiPageService

    def legacyHome() {
        redirect action: "list", permanent: true
    }

    def addTag(String id, String tag) {
        def p = Plugin.findByName(id)
        if(p) {
            if(request.method == "POST") {
                p.addTag(tag)
            }
            def tags = p.tags.collect {
                [  name: it]
            }
            def data = [ tagResults: tags ]
            render data as JSON            
        }
        else {
            render status: 404
        }

    }
    def removeTag(String id, String tag) {
        def p = Plugin.findByName(id)
        if(p) {
            if(request.method == "POST") {
                p.removeTag(tag)
            }
            def tags = p.tags.collect {
                [  name: it]
            }
            def data = [ tagResults: tags ]
            render data as JSON            
        }
        else {
            render status: 404
        }

    }    
    def list() {
        try {
            def maxResults = params.int("max",10)
            def offset = params.int("offset", 0)

            def plugins
            def pluginCount
            def filter = params.filter?.toString() ?: "featured"
            if (params.tag) {
                filter = "all"
                (plugins, pluginCount) = pluginService.listPluginsByTagWithTotal(params.tag, max: maxResults, offset: offset)
            }
            else {
                (plugins, pluginCount) = pluginService."list${filter.capitalize()}PluginsWithTotal"(max: maxResults, offset: offset)
            }

            def allTags = new DetachedCriteria(org.grails.taggable.Tag).property("name").list()
            def tags = tagService.getPluginTagArray()
            def model = [ allTags:allTags, tags: tags, plugins: plugins, pluginCount: pluginCount ]
            if (filter) model["activeFilter"] = filter
            if (params.tag) model["activeTag"] = params.tag
            return model
        }
        catch (TagNotFoundException ex) {
            flash.message = "Tag not found"
            redirect action: "list"
        }
    }

    def show(String id) {
        def plugin = Plugin.findByName(id)

        // Redirect to the list page if the plugin doesn't exist
        if (!plugin) {
            flash.message = "Plugin not found"
            redirect action: 'list'
            return
        }

        def tags = tagService.getPluginTagArray()
        def allTags = new DetachedCriteria(org.grails.taggable.Tag).property("name").list()
        [ plugin: plugin, tags: tags, allTags: allTags ]
    }

    def editPlugin(String id) {
        show(id)
    }

    def updatePlugin(String id) {
        def plugin = Plugin.findByName(id)
        if(plugin && params['plugin']) {
            plugin.properties['summary'] = params['plugin']
            if(plugin.save()) {
                def installation = params['plugin']['installation'] 
                def description= params['plugin']['description'] 

                try {
                  def pluginTabInstallation = wikiPageService.createOrUpdatePluginTab(
                    "plugin-${id}-installation",
                    installation.body,
                    request.user,
                    params.long('plugin.installation.version'))

                  def pluginTabDescription = wikiPageService.createOrUpdatePluginTab(
                    "plugin-${id}-description",
                    description.body,
                    request.user,
                    params.long('plugin.description.version'))              
                    if(pluginTabInstallation.hasErrors() || pluginTabDescription.hasErrors()) {
                        render view:"editPlugin", model:show(id)
                    }
                    else {
                        redirect uri:"/plugin/$id" 
                    }                  

                }
                catch( javax.persistence.OptimisticLockException e) {
                    flash.message = e.message
                    render view:"editPlugin", model:show(id)
                    return
                }
     
            } 
            else {
                render view:"editPlugin", model:show(id)
            }

        }
        else {
            render status:404
        }
    }


    def submitPlugin() {
        def pluginPendingApproval = new PluginPendingApproval(
            submittedBy: request.user,
            status: ApprovalStatus.PENDING
        )
        if (request.method == "POST") {
            pluginPendingApproval.name = params.name
            pluginPendingApproval.scmUrl = params.scmUrl
            pluginPendingApproval.versionNumber = params.versionNumber
            pluginPendingApproval.notes = params.notes

            pluginPendingApproval.validate()
            println "PPA: ${pluginPendingApproval.inspect()}"

            if (!pluginPendingApproval.hasErrors() && pluginPendingApproval.save(flush:true)) {
                flash.message = "Your plugin has been submitted for approval"
                redirect url: "/plugins/pending/${pluginPendingApproval?.id}"
            } else {
                flash.message = "Please correct the fields below"
                flash.next()
            }
        }
        [pluginPendingApproval: pluginPendingApproval]
    }

    def submissionReceived() {
    }

    def pendingPlugins() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def tags = tagService.getPluginTagArray()
        [
                tags: tags,
                pluginPendingApprovalList: PluginPendingApproval.pending.list(params),
                pluginPendingApprovalTotal: PluginPendingApproval.pending.count()
        ]
    }

    def showPendingPlugin() {
        def pluginPendingApprovalInstance = PluginPendingApproval.get(params.id)
        if (!pluginPendingApprovalInstance) {
            redirect action: 'pendingPlugins'
        }

        [pluginPendingApprovalInstance: pluginPendingApprovalInstance]
    }

    def search() {
        if (params.q) {
            def tags = tagService.getPluginTagArray()
            try {
                def searchResult = Plugin.search(params.q, offset: params.offset)
                searchResult.results = searchResult.results.findAll{it}.unique { it.title }

                flash.message = "Found $searchResult.total results!"
                flash.next()
                render view: "list", model: [
                        query: params.q,
                        tags: tags,
                        searchResult: searchResult, 
                        plugins: searchResult.results, 
                        pluginCount: searchResult.total,
                        otherParams: [q: params.q] ]
            }
            catch (SearchEngineQueryParseException ex) {
                render view: "list", model: [tags: tags, parseException: true]
            }
            catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ex) {
                render view: "list", model: [tags: tags, clauseException: true]
            }
        }
        else {
            redirect action: "list"
        }
    }

    //---- REST API --------------------------------------

    /**
     * Display all plugins or subset of using preferred content type. Only
     * JSON or XML supported at this point.
     */
    def apiList() {
        def pluginData = listPlugins(0, 'all')
        def pluginList = transformPlugins(pluginData.currentPlugins, pluginData.category)

        withFormat {
            json {
                render pluginList as JSON
            }
            xml {
                renderMapAsXml pluginList, "plugins"
            }
        }
    }

    /**
     * Display the details of one plugin in either JSON or XML form.
     */
    def apiShow() {
        if(params.name && params.version) {
            def p = params.name
            def v = params.version
            def release = PluginRelease.where {
                plugin.name == p && releaseVersion == v 
            }.get()

            if (release) {
                def plugin = transformPluginRelease(release)
                withFormat {
                    json {
                        render plugin as JSON        
                    }
                    xml {
                        renderMapAsXml plugin, "plugin"
                    }
                }
            }
            else {
                response.sendError 404
            }
        }
        else {
            def plugin = byName(params)
            if (!plugin) {
                response.sendError 404
                return
            }

            plugin = transformPlugin(plugin)
            withFormat {
                json {
                    render plugin as JSON
                }
                xml {
                    renderMapAsXml plugin, "plugin"
                }
            }
        }
    }

    /**
     * Plugin 'ping'. Should only be accessible from a PUT. It extracts
     * the location of the plugin's deployment repository from the request
     * and queues up a job to update the plugin's details in the database
     * from the POM and plugin descriptor stored in the repository.
     */
    def apiUpdate() {
        // Start by getting the named plugin if it exists.
        def plugin = Plugin.findByName(params.id)

        // Check the payload. There should be a 'url' parameter containing
        // the location of the repository to which the plugin was deployed.
        // If the parameter doesn't exist or it's not a URL, we return a 400.
        def data = JSON.parse(request)
        if (!data.url) {
            render contentType: "application/json", status: 400, {
                message = "No repository URI provided"
            }
            return
        }

        try {
            def uri = new URI(data.url)

            if (!uri.absolute) {
                render contentType: "application/json", status: 400, {
                    message = "Relative repository URI not supported: ${uri}"
                }
                return
            }

            // Default to it not being a snapshot release if 'isSnapshot' is not provided.
            publishEvent(new PluginUpdateEvent(this, data.name, data.version, data.group, data.isSnapshot ?: false, uri))

            render contentType: "application/json", {
                message = "OK"
            }
        }
        catch (URISyntaxException ex) {
            render contentType: "application/json", status: 400, {
                message = "Invalid repository URI: ${data.url}"
            }
            return
        }
        catch (Exception ex) {
            log.error "Plugin update failed", ex
            render contentType: "application/json", status: 500, {
                message = "Internal server error: ${ex.message}"
            }
            return
        }
    }

    protected listPlugins(defaultMax, defaultCategory) {
        def queryParams = [:]
        queryParams.offset = params.offset ?: 0
        queryParams.sort = params.sort ?: 'name'
        queryParams.order = params.order ?: 'asc'

        // If a default maximum is provided, use that. params.max is ignored
        // completely by this method.
        if (defaultMax) {
            queryParams.max = defaultMax
            params.max = defaultMax
        }
        
        log.debug "[listPlugins] Parameters: $params"

        def category = params.remove('category') ?: defaultCategory
        def currentPlugins = []
        def totalPlugins = 0

        try {
            if (params.q) {
                // Build the arguments for the search, starting with the query
                // string. We add the category if it's defined. Finally we add
                // the search options.
                def args = [params.q]
                if (category) args << category
                args << queryParams

                // Remove any sort arguments, since they can only work with untokenized
                // search fields.
                queryParams.remove("sort")
                queryParams.remove("order")

                (currentPlugins, totalPlugins) = pluginService.searchWithTotal(*args)
            }
            else {
                queryParams["fetch"] = [licenses: "select"]
                (currentPlugins, totalPlugins) = pluginService."list${category.capitalize()}PluginsWithTotal"(queryParams)
            }
            return [currentPlugins: currentPlugins, category: category,totalPlugins: totalPlugins]
        }
        catch (MissingMethodException ex) {
            log.error "Unable to list plugins for category '${category}': ${ex.message}"
            response.sendError 404
            return [:]
        }
    }

    protected transformPlugins(plugins, category = null) {
        def map = [ pluginList: plugins ? plugins.collect { p -> transformPlugin(p) } : [] ]
        if (category) map.category = category
        return map 
    }

    protected transformPlugin(plugin) {
        def pluginMap = [
                name: plugin.name,
                version: plugin.currentRelease,
                title: plugin.title,
                // These two for backwards compatibility
                author: plugin.authors[0].name,
                authorEmailMd5: DigestUtils.md5Hex(plugin.authors[0].email),
                authorList: plugin.authors.collect { [name: it.name, email: DigestUtils.md5Hex(it.email)] },
                description: plugin.summary,
                grailsVersion: plugin.grailsVersion,
                documentation: plugin.documentationUrl,
                official: plugin.official,
                licenseList: plugin.licenses.collect { l -> [name: l.name, url: l.url] },
                lastReleased: dateService.getRestDateTime(plugin.lastReleased),
                file: plugin.downloadUrl,
                rating: plugin.avgRating,
                zombie: plugin.zombie ]
            
        if (plugin.issuesUrl) pluginMap.issues = plugin.issuesUrl
        if (plugin.scmUrl) pluginMap.scm = plugin.scmUrl

        return pluginMap
    }

    protected transformPluginRelease(pluginRelease) {
        def pluginMap = transformPlugin(pluginRelease.plugin)
        pluginMap.version = pluginRelease.releaseVersion
        return pluginMap
    }
    protected renderMapAsXml(map, root = "root") {
        render contentType: "application/xml", {
            "${root}" {
                mapAsXml delegate, map
            }
        }
    }

    protected mapAsXml(builder, map) {
        for (entry in map) {
            if (entry.value instanceof Collection) {
                builder."${entry.key}" {
                    for (m in entry.value) {
                        "${entry.key - 'List'}" {
                            mapAsXml builder, m
                        }
                    }
                }
            }
            else {
                builder."${entry.key}"(entry.value)
            }
        }
    }

    protected byName(params) {
        Plugin.createCriteria().get {
            eq 'name', params.name
            join 'description'
            join 'installation'
            join 'faq'
            join 'screenshots'
            maxResults 1
            cache true
        }
    }
}
