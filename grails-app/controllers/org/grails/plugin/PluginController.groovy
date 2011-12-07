package org.grails.plugin

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springcache.annotations.*

import javax.persistence.OptimisticLockException
import javax.servlet.http.HttpServletResponse

import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.metaclass.RedirectDynamicMethod
import org.codehaus.groovy.grails.web.servlet.HttpHeaders
import org.compass.core.engine.SearchEngineQueryParseException
import org.grails.auth.Role
import org.grails.comments.*
import org.grails.taggable.*
import org.grails.tags.TagNotFoundException
import org.grails.wiki.BaseWikiController
import org.springframework.web.context.request.RequestContextHolder

class PluginController extends BaseWikiController {

    static String HOME_WIKI = 'PluginHome'
    static int PORTAL_MAX_RESULTS = 5
    static int PORTAL_MIN_RATINGS = 1
    
    static defaultAction = "home"

    static allowedMethods = [
            apiList:   'GET',
            apiShow:   'GET',
            apiUpdate: 'PUT']
    
    def taggableService
    def wikiPageService
    def pluginService
    def commentService
    def grailsUrlMappingsHolder

    def home() {
        // We only want to display 5 plugins at a time in the web interface,
        // but JSON and XML data shouldn't be limited in that way.
        def max = 0
        if (response.format == 'html') {
            max = PORTAL_MAX_RESULTS
        }

        // If no category is specified, default to 'featured' for the
        // web interface, and 'all' for JSON and XML requests.
        def fetchInstalled = params.category == "installed"
        def pluginData = listPlugins(max, (response.format == 'html' ? 'featured' : 'all'))

        withFormat {
            html {
                if (fetchInstalled && !pluginData.currentPlugins) {
                    pluginData.message = "Not enough data has been collected yet. This will start working once enough people have adopted Grails 2."
                }
                return pluginData
            }
            json {
                render transformPlugins(pluginData.currentPlugins, pluginData.category) as JSON
            }
            xml {
                renderMapAsXml transformPlugins(pluginData.currentPlugins, pluginData.category), "plugins"
            }
        }
    }

    def legacyHome() {
        redirect action: "home", permanent: true
    }

    def browseByName = {
        params.sort = "name"
        params.order = "asc"

        def (currentPlugins, totalPlugins) = pluginService.listAllPluginsWithTotal(params)
        currentPlugins = currentPlugins.groupBy { it.name ? it.name[0].toUpperCase() : 'A' }

        return [currentPlugins: currentPlugins, totalPlugins: totalPlugins]
    }

    def forum = {}

    def all = {
        render view:"home", model:[
                originAction:"all",
                pluginList:Plugin.list(max:10, offset: params.offset?.toInteger(), cache:true, sort:"name") ]
    }

    def list = {
        redirect action: "browseByName", params: params, permanent: true
    }

    def show = {
        def plugin = byName(params)
        if (!plugin) {
            return redirect(action:'createPlugin', params:params)
        }

        def userRating
        if (request.user) {
            userRating = plugin.userRating(request.user)
        }

        // TODO: figure out why plugin.ratings.size() is always 1
        render view:'showPlugin', model:[plugin:plugin, userRating: userRating]
    }

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

    /**
     * Plugin 'ping'. Should only be accessible from a PUT. It extracts
     * the location of the plugin's deployment repository from the request
     * and queues up a job to update the plugin's details in the database
     * from the POM and plugin descriptor stored in the repository.
     */
    def apiUpdate() {

        // Start by getting the named plugin if it exists.
        def plugin = Plugin.findByName(params.name)

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

    def editPlugin = {
        def plugin = Plugin.get(params.id)
        if(plugin) {
            if(request.method == 'POST') {
                // Update the plugin's properties, but exclude 'zombie'
                // because only an administrator can set that.
                bindData plugin, params, [ "zombie" ]
                if (!plugin.validate()) {
                    return render(view:'editPlugin', model: [plugin:plugin])
                }
                if (!plugin.isNewerThan(params.currentRelease)) {
                    plugin.lastReleased = new Date();
                }
                // Update 'zombie' if we have an administrator.
                if (SecurityUtils.subject.hasRole(Role.ADMINISTRATOR)) {
                    plugin.zombie = params.zombie ?: false
                }
                
                pluginService.savePlugin(plugin)
                redirect(action:'show', params:[name:plugin.name])
            } else {
                return render(view:'editPlugin', model: [plugin:plugin])
            }
        } else {
            response.sendError 404
        }
    }

    def createPlugin = {
        // just in case this was an ad hoc creation where the user logged in during the creation...
        if (params.name) params.name = params.name - '?action=login'
        def plugin = new Plugin(params)
        if(request.method == 'POST') {
            pluginService.initNewPlugin(plugin, request.user)
            
            if (pluginService.savePlugin(plugin)) {
                redirect(action:'show', params: [name:plugin.name])
            } else {
                render(view:'createPlugin', model:[plugin:plugin])
            }
        } else {
            render(view:'createPlugin', model:[plugin:plugin])
        }
    }

    def deletePlugin = {
        def plugin = byName(params)
        log.warn "Deleting Plugin: $plugin"
        plugin.delete()
        redirect(view:'index')
    }

    def search = {
        if(params.q) {
            try {
                def searchResult = Plugin.search(params.q, offset: params.offset)
                searchResult.results = searchResult.results.findAll{it}.unique { it.title }
                flash.message = "Found $searchResult.total results!"
                flash.next()
                render view: "searchResults", model: [searchResult: searchResult]
            }
            catch (SearchEngineQueryParseException ex) {
                render view: "searchResults", model: [parseException: true]
            }
            catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ex) {
                render view: "searchResults", model: [clauseException: true]
            }
        }
        else {
            redirect(action:'home')
        }
   }

    def latest() {

        def feedOutput = {

            def (top5, total) = pluginService.listRecentlyUpdatedPluginsWithTotal(max: 5)
            title = "Grails New Plugins Feed"
            link = "http://grails.org/Plugins"
            description = "New and recently updated Grails Plugins"

            for(item in top5) {
                entry(item.title) {
                    link = "http://grails.org/plugin/${item.name.encodeAsURL()}"
                    author = item.author
                    publishedDate = item.lastUpdated
                    item.summary
                }
            }
        }

        withFormat {
            html {
                redirect action: "home"
            }
            rss {
                render feedType: "rss", feedOutput
            }
            atom {
                render feedType: "atom", feedOutput
            }
        }

    }

    def saveTab = {
        if (!params.id) {
            render template: "/shared/remoteError", model: [code: "page.id.missing"]
        }
        else {
            try {
                PluginTab pluginTab = wikiPageService.createOrUpdatePluginTab(
                        params.id.decodeURL(),
                        params.body,
                        request.user,
                        params.long('version'))

                if (pluginTab.hasErrors()) {
                    render(template: "/content/wikiEdit", model: [
                            wikiPage: pluginTab,
                            update: params.update,
                            editFormName: params.editFormName,
                            saveUri: g.createLink(action: "saveTab", id: pluginTab.title)])
                }
                else {
                    render(template: "/content/wikiShow", model: [
                            content: pluginTab,
                            message: "wiki.page.updated",
                            update: params.update,
                            latest: pluginTab.latestVersion])
                }
            }
            catch (OptimisticLockException ex) {
                def pluginTab = new PluginTab(title: params.id.decodeURL(), body: params.body)
                render(template: "/content/wikiEdit", model: [
                        wikiPage: pluginTab,
                        update: params.update,
                        editFormName: params.editFormName,
                        saveUri: g.createLink(action: "saveTab", id: pluginTab.title),
                        error: "page.optimistic.locking.failure"])
            }
        }
    }

    def postComment = {
        def plugin = Plugin.get(params.id)
        plugin.addComment(request.user, params.comment)
        plugin.save(flush:true)
        return render(template:'/comments/comment', var:'comment', bean:plugin.comments[-1])
    }

    def addTag = {
        def plugin = Plugin.get(params.id)
        params.newTag.trim().split(',').each { newTag ->
            plugin.addTag(newTag.trim())
        }
        Plugin.reindex(plugin)
        render(template:'tags', var:'plugin', bean:plugin)
    }

    def removeTag = {
        def plugin = Plugin.get(params.id)
        plugin.removeTag(params.tagName)
        plugin.save()
        Plugin.reindex(plugin)
        render(template:'tags', var:'plugin', bean:plugin)
    }

    def showTag = {
        redirect action: "browseByTag", params: params, permanent: true
    }

    def browseByTag = {
        try {
            def maxResults = params.int('max') ?: 10
            def offset = params.int('offset') ?: 0
            def (plugins, pluginCount) = pluginService.listPluginsByTagWithTotal(params.tagName, max: maxResults, offset: offset)
            return [currentPlugins: plugins, totalPlugins: pluginCount, tagName:params.tagName, max: maxResults, offset: offset]
        }
        catch (TagNotFoundException ex) {
            render view: "tagNotFound", model: [tagName: ex.tagName ?: '', msgCode: ex.code]
        }
    }

    /**
     * Displays a cloud of all the tags attached to plugins.
     */
    def browseTags = {
        // Get hold of all the plugin tags. This service method returns a map of tag
        // names to counts, i.e. how many plugins have been tagged with each tag.
        def allPluginTags = taggableService.getTagCounts("plugin").sort()
        [tags: allPluginTags]
    }

    def showComment = {
        def link = CommentLink.findByCommentAndType(Comment.get(params.id), 'plugin', [cache:true])
        def plugin = Plugin.get(link.commentRef)
        redirect(action:'show', params:[name:plugin.name], fragment:"comment_${params.id}")
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
                author: plugin.author,
                authorEmail: plugin.authorEmail,
                authorList: plugin.authors.collect { [name: it.name, email: it.email] },
                description: plugin.summary,
                grailsVersion: plugin.grailsVersion,
                documentation: plugin.documentationUrl,
                file: plugin.downloadUrl,
                rating: plugin.avgRating ]
            
        if (plugin.issuesUrl) pluginMap.issues = plugin.issuesUrl
        if (plugin.scmUrl) pluginMap.scm = plugin.scmUrl

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
                builder."${entry.key}"(entry.value, test: "test")
            }
        }
    }

    protected byTitle(params) {
        Plugin.findByTitle(params.title.replaceAll('\\+', ' '), [cache:true])
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
