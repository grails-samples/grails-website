package org.grails.plugin

import org.grails.auth.User
import org.grails.content.Version
import org.joda.time.DateTime
import org.grails.tags.TagNotFoundException
import org.grails.taggable.Tag
import org.grails.taggable.TagLink

class PluginService {

    static int DEFAULT_MAX = 5
    static transactional = true
    
    def grailsApplication
    def searchableService
    def wikiPageService
    
    def popularPlugins(minRatings, max = DEFAULT_MAX) {
        def ratingsComparator = new PluginComparator()
        Plugin.list(cache:true, maxResults:max).findAll {
            it.ratings.size() >= minRatings
        }.sort(ratingsComparator).reverse()
    }
    
    def newestPlugins(max = DEFAULT_MAX) {
        Plugin.withCriteria {
            order('dateCreated', 'desc')
            maxResults(max)
            cache true
        }
    }
    
    def listAllPluginsWithTotal(Map args = [max: 200]) {
        return [ Plugin.list(args), Plugin.count() ]
    }

    def listFeaturedPluginsWithTotal(Map args = [max: 200]) {
        return [ Plugin.findAllByFeatured(true, args), Plugin.countByFeatured(true) ]
    }

    def listNewestPluginsWithTotal(Map args = [max: 200]) {
        args << [cache: true, sort: "dateCreated", order: "desc" ] 
        return [ Plugin.list(args), Plugin.count() ]
    }

    def listInstalledPluginsWithTotal(Map args = [max: 200]) {
        args << [cache: true, sort: "usage", order: "desc" ] 
        return [ Plugin.findAllByUsageIsNotNull(args), Plugin.countByUsageIsNotNull() ]
    }

    def listPopularPluginsWithTotal(Map args = [max: 200]) {
        // The Rateable plugin's query only accepts pagination arguments.
        def params = [:]
        if (args["max"] != null) params["max"] = args["max"]
        if (args["offset"] != null) params["offset"] = args["offset"]
        return [
                Plugin.listOrderByAverageRating(cache:true, *:params),
                Plugin.countRated() ]
    }

    def listRecentlyUpdatedPluginsWithTotal(Map args = [max: 200]) {
        args << [cache: true, sort: "lastReleased", order: "desc" ] 
        return [ Plugin.list(args), Plugin.count() ]
    }

    def listSupportedPluginsWithTotal(Map args = [max: 200]) {
        return [ Plugin.findAllByOfficial(true, args), Plugin.countByOfficial(true) ]
    }

    def listPluginsByTagWithTotal(Map args = [max: 200], String tagName) {
        // Start by grabbing the tag with the given name. There's unlikely to
        // be frequent changes to the 'tags' table, so caching the query makes
        // sense.
        def tag = Tag.findByName(tagName, [cache: true])

        // Make sure that there is a tag with this name. If there isn't we have
        // to notify the caller via an exception.
        if (!tag) throw new TagNotFoundException(tagName)

        // Now find all the plugins with this tag.
        def result = []
        def links = TagLink.findAllByTagAndType(tag, 'plugin', args)
        if (links) {
            result << Plugin.withCriteria {
                inList 'id', links*.tagRef
            }

            result << TagLink.countByTagAndType(tag, 'plugin')
        }
        else {
            result << [] << 0
        }

        return result
    }
    
    String createTabTitle(String pluginName, String tabName) {
        return "plugin-${pluginName}-${tabName}"
    }
    
    String extractTabName(String title) {
        def titleParts = title.split('-')
        
        // The plugin tab type is encoded in the page title in different
        // ways depending on when the page was created. The old style is
        // '$wikiType-nnn, whereas the new style is 'plugin-$pluginName-$wikiType'.
        if (titleParts[1] ==~ /\d+/) return titleParts[0]
        else return titleParts[-1]
    }
    
    def initNewPlugin(Plugin plugin, User user) {
        // Add the wiki pages for this new plugin.
        Plugin.WIKIS.each { wiki ->
            def body = ''
            if (wiki == 'installation') {
                body = "{code}grails install-plugin ${plugin.name}{code}"
            }

            // Saves don't cascade from the plugin to the wiki pages, so
            // we have to save them before saving the plugin.
            def tabContent = wikiPageService.createOrUpdatePluginTab(
                    createTabTitle(plugin.name, wiki),
                    body,
                    user)
            tabContent.save(failOnError: true)
            plugin."$wiki" = tabContent
            
            // If there is no provided doc url, we'll assume that this page is the doc.
            if (!plugin.documentationUrl) {
                plugin.documentationUrl = "${grailsApplication.config.grails.serverURL ?: ''}/plugin/${plugin.name}"
            }
        }
    }
    
    def savePlugin(Plugin plugin, boolean failOnError = false) {
        try {
            searchableService.stopMirroring()
            
            if (!plugin.defaultDependencyScope) plugin.defaultDependencyScope = Plugin.DEFAULT_SCOPE

            def newPlugin = !plugin.id
            def savedPlugin = plugin.save(failOnError: failOnError, flush: true)
            
            if (savedPlugin) {
                if (newPlugin) savedPlugin.index()
                else plugin.reindex()
            }
            
            return savedPlugin
        }
        finally {
            searchableService.startMirroring()
        }
    }
    
    def runMasterUpdate() {
        translateMasterPlugins(generateMasterPlugins())
    }
    
    def generateMasterPlugins() {
        try {
            def pluginLoc = grailsApplication.config?.plugins?.pluginslist
            def listFile = new URL(pluginLoc)
            def listText = listFile.text
            // remove the first line of <?xml blah/>
            listText = listText.replaceAll(/\<\?xml ([^\<\>]*)\>/, '')
            def plugins = new XmlSlurper().parseText(listText)

            log.info "Found ${plugins.plugin.size()} master plugins."

            return plugins.plugin.inject([]) { pluginsList, pxml ->
                if (!pxml.release.size()) return pluginsList
                def latestRelease = pxml.@'latest-release'
                def latestReleaseNode = pxml.release.find { releaseNode ->
                    releaseNode.@version == latestRelease
                }
                def p = new Plugin()
                p.with {
                    name = pxml.@name
                    grailsVersion = getGrailsVersion(p)
                    title = latestReleaseNode.title.toString() ?: pxml.@name
                    description = new PluginTab(body:latestReleaseNode.description.toString() ?: '')
                    author = latestReleaseNode.author
                    authorEmail = latestReleaseNode.authorEmail
                    documentationUrl = replaceOldDocsWithNewIfNecessary(latestReleaseNode.documentation, name)
                    downloadUrl = latestReleaseNode.file
                    currentRelease = latestRelease
                }

                pluginsList << p
            }
        }
        catch(e) {
            log.error "Error parsing master plugin list: ${e.message}",e
            return []
        }

    }

    private def replaceOldDocsWithNewIfNecessary(oldDocs, name) {
        boolean match = oldDocs =~ /http:\/\/(www\.)?grails.org\//
        return match ? "http://grails.org/plugin/${name}" : oldDocs
    }

    def translateMasterPlugins(masters) {
        Plugin.withSession { session ->
            masters.each { master ->
                try {
                    def plugin = Plugin.findByName(master.name)
                    if (!plugin) {
                        // injecting a unique wiki page name for description
                        // pull off the desc so we don't try to save it
                        def descWiki = master.description
                        master.description = null
                        // so we need to save the master first to get its id
                        if (!master.save()) {
                            log.error "Could not save master plugin: $master.name ($master.title), version $master.currentRelease"
                            master.errors.allErrors.each { log.error "\t$it" }

                        }
                        // put the wiki page back with a unique title
                        descWiki.title = "description-${master.id}"
                        master.description = descWiki
                        log.info "No existing plugin, creating new ==> ${master.name}"
                        // before saving the master, we need to save the description wiki page
                        if (!master.description.save() && master.description.hasErrors()) {
                            master.description.errors.allErrors.each { log.error it }
                        } else {
                            def v = master.description.createVersion()
                            v.author = User.findByLogin('admin')
                            if(!v.save(flush:true)) {
                                log.warn "Can't save version ${v.title} (${v.number})"
                                v.errors.allErrors.each { log.warn it }
                            }
                        }
                        //inject dummy wikis for users to fill in
                        (Plugin.WIKIS - 'description').each { wiki ->
                            master."$wiki" = new PluginTab(title:"$wiki-${master.id}", body:'')
                            assert master."$wiki".save()
                        }
                        // give an initial release date of now
                        master.lastReleased = new DateTime()
                        if (!master.groupId) {
                                master.groupId = "org.grails.plugins"
                        }

                        // so we need to save the master first to get its id
                        //
                        // save new master plugin
                        if (!master.save()) {
                            log.error "Could not save master plugin: $master.name ($master.title), version $master.currentRelease"
                            master.errors.allErrors.each { log.error "\t$it" }
                        } else {
                            log.info "New plugin was saved from master: $master.name"
                            log.info "There are now ${Plugin.count()} plugins."
                        }
                    } else {
                        // update existing plugin
                        updatePlugin(plugin, master)
                    }
                    
                }
                finally {
                    session.flush()
                    session.clear()
                }
            }
            
        }
    }

    def updatePlugin(plugin, master) {
        // Don't update the plugin if the version hasn't changed.
        if (plugin.currentRelease == master.currentRelease) return

        log.info "Updating plugin \"$plugin.name\"..."

        // these attributes are overriden by local plugin domain changes
        updatePluginAttribute('title', plugin, master)
        updatePluginAttribute('author', plugin, master)
        updatePluginAttribute('authorEmail', plugin, master)
        
        // these are always overridden by the master list
        plugin.name = master.name
        plugin.documentationUrl = master.documentationUrl
        plugin.downloadUrl = master.downloadUrl
        // if this was a release update, also update the date of release
        if (plugin.currentRelease != master.currentRelease) {
            plugin.lastReleased = new DateTime()
        }
        plugin.currentRelease = master.currentRelease
        plugin.grailsVersion = master.grailsVersion

        if (!plugin.save()) {
            log.warn "Local plugin '$plugin.name' was not updated properly... errors follow:"
            plugin.errors.allErrors.each { log.warn it }
        // I don't know why new versions need to be created here, but it's causing
        // problems because each new Version has the same number as the current
        // wiki page version. PAL
//        } else {
//            def v = plugin.description.createVersion()
//            v.author = User.findByLogin('admin')
//            if(!v.save(flush:true)) {
//                log.warn "Can't save version ${v.title} (${v.number})"
//                v.errors.allErrors.each { log.warn it }
//            }
        }
        
        log.info "Local plugin '$plugin.name' was updated with master version."
    }
    
    def updatePluginAttribute(propName, plugin, master) {
        if (master."$propName" && !plugin."$propName") {
            plugin."$propName" = master."$propName"
        }
    }

    def compareVersions(v1, v2) {
        def v1Num = new PluginVersion(version:v1)
        def v2Num = new PluginVersion(version:v2)
        v1Num.compareTo(v2Num)
    }

    def getGrailsVersion(plugin) {
        def xmlLoc = "${grailsApplication.config?.plugins?.location}/grails-${plugin.name}/tags/LATEST_RELEASE/plugin.xml"
        def xmlUrl = new URL(xmlLoc)

        try {
            def xmlText = xmlUrl.text

            def pluginXml = new XmlSlurper().parseText(xmlText)
            return pluginXml.@grailsVersion.toString()
        }
        catch (FileNotFoundException ex) {
            // If the file doesn't exist, then the plugin was published using
            // the portal 'ping'.
            return ''
        }
    }

    /**
     * Text-based search using the given Lucene-compatible query string.
     * Returns a list of Plugin instances, although they may not be fully
     * hydrated, i.e. any non-searchable properties will not be populated.
     * @param query The Lucene-compatible query string.
     * @param options A map of search modifiers, such as 'sort', 'offset'
     * and 'max'.
     */
    protected final search(String query, Map options) {
        return searchWithResults(query, options).results
    }

    /**
     * Text-based search using the given Lucene-compatible query string.
     * Returns a list of Plugin instances, although they may not be fully
     * hydrated, i.e. any non-searchable properties will not be populated.
     * @param query The Lucene-compatible query string.
     * @param category The category of plugin to constrain the search to:
     * 'featured', 'newest', 'recentlyUpdated', 'supported'. Note that
     * 'popular' is not currently supported by text-based search.
     * @param options A map of search modifiers, such as 'sort', 'offset'
     * and 'max'.
     */
    protected final search(String query, String category, Map options) {
        query = categoryToSearchConstraint(category) + " " + query
        options << optionsForCategory(category)

        return searchWithResults(query, options).results
    }

    /**
     * Same as {@link #search(String, Map)} except it supports the options
     * as named arguments.
     */
    protected final search(Map options, String query) {
        return searchWithResults(query, options).results
    }

    /**
     * Text-based search using the given Lucene-compatible query string.
     * Returns a tuple containing the list of Plugin instances matching
     * the query and the total number of results. The plugins objects
     * may not be fully hydrated, i.e. any non-searchable properties will
     * not be populated.
     * @param query The Lucene-compatible query string.
     * @param options A map of search modifiers, such as 'sort', 'offset'
     * and 'max'.
     */
    protected final searchWithTotal(String query, Map options) {
        def results = searchWithResults(query, options)
        return [results.results, results.total]
    }

    /**
     * Text-based search using the given Lucene-compatible query string.
     * Returns a tuple containing the list of Plugin instances matching
     * the query and the total number of results. The plugins objects
     * may not be fully hydrated, i.e. any non-searchable properties will
     * not be populated.
     * @param query The Lucene-compatible query string.
     * @param category The category of plugin to constrain the search to:
     * 'featured', 'newest', 'recentlyUpdated', 'supported'. Note that
     * 'popular' is not currently supported by text-based search.
     * @param options A map of search modifiers, such as 'sort', 'offset'
     * and 'max'.
     */
    protected final searchWithTotal(String query, String category, Map options) {
        query = categoryToSearchConstraint(category) + " " + query
        options << optionsForCategory(category)

        return searchWithTotal(query, options)
    }

    /**
     * Same as {@link #searchWithTotal(String, Map)} except it supports the
     * options as named arguments.
     */
    protected final searchWithTotal(Map options, String query) {
        return searchWithTotal(query, options)
    }

    /**
     * Same as {@link #searchWithTotal(String, String, Map)} except it supports the
     * options as named arguments.
     */
    protected final searchWithTotal(Map options, String query, String category) {
        return searchWithTotal(query, category, options)
    }

    /**
     * Executes a Searchable search and returns the results object.
     */
    private searchWithResults(String query, Map options = [:]) {
        return Plugin.search(query, options)
    }

    /**
     * Returns a map of search options based on the given category. These
     * search options can be used to override those provided in a normal
     * search. If the category has no requirements on the search options,
     * this method returns an empty map.
     */
    private Map optionsForCategory(String category) {
        switch(category.toLowerCase()) {
        case "newest":
            return [sort: "dateCreated", order: "desc"] 

        case "recentlyUpdate":
            return [sort: "lastReleased", order: "desc"] 

        default:
            return [:]
        }
    }

    /**
     * Given a category, this method returns a query fragment that can be
     * attached to an existing Lucence-compatible query string to constrain
     * the results to plugins within that category.
     */
    private String categoryToSearchConstraint(String category) {
        switch(category.toLowerCase()) {
        case "featured":
            return "+featured:true"

        case "supported":
            return "+official:true"

        default:
            return ""
        }
    }
}

class PluginVersion implements Comparable {

    String[] version
    String tag

    public void setVersion(versionString) {
        def split = versionString.split(/[-|_]/)
        version = split[0].split(/\./)
        tag = split.size() > 1 ? split[1] : ''
    }

    public int compareTo(Object o) {
        def result = null
        version.eachWithIndex { versionElem, i ->
            // skip if we've already found a result in a previous index
            if (result != null) return

            // if this version is a snapshot and the other is not, the other is always greater
            if (tag && !o.tag) {
                result = -1
                return
            }
            
            // if the other is a snapshot and this is not, this version is always greater
            if (o.tag && !tag) {
                result = 1
                return
            }

            // make other version 0 if there really is no placeholder for it
            def otherVersion = (o.version.size() == i) ? 0 : o.version[i]

            if (versionElem > otherVersion) {
                result = 1
                return
            }
            if (versionElem < otherVersion) {
                result = -1
                return
            }
        }
        // if the comparison is equal at this point, and there are more elements on the other version, then that version
        // will be greater because it has another digit on it, otherwise the two really are equal
        if (result == null) {
            if (o.version.size() > version.size()) result = -1
            else result = 0
        }
        result
    }   
}

// sorts by averageRating, then number of votes
class PluginComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        if (o1.averageRating > o2.averageRating) return 1
        if (o1.averageRating < o2.averageRating) return -1
        // averateRatings are same, so use number of votes
        if (o1.ratings.size() > o2.ratings.size()) return 1
        if (o1.ratings.size() < o2.ratings.size()) return -1
        return 0
    }
}
