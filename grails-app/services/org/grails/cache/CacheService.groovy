package org.grails.cache

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 19, 2008
*/
class CacheService {
    private static final String PLUGIN_LIST_KEY = "pluginListKey"

    static final String CONTENT_CACHE = "content"
    static final String WIKI_CACHE = "wiki"
    static final String TEXT_CACHE = "text"
    static final String PLUGIN_CACHE = "plugin"

    static final transactional = false

    transient grailsCacheManager
    transient grailsCacheAdminService

    def getContent(key) {  
        return contentCache.get(key)?.get()
    }

    def putContent(key, value) {
        def old = getContent(key)
        contentCache.put key, value
        return old
    }

    def removeContent(key) {
        contentCache.evict key
    }

    def flushWikiCache() {
        wikiCache.clear()
    }

    def getWikiText(key) {
        wikiCache.get(wikiKey(key))?.get()
    }

    def removeWikiText(key) {
        wikiCache.evict wikiKey(key)
    }

    def putWikiText(key, value) {
        key = wikiKey(key)
        def old = getWikiText(key)
        wikiCache.put key, value
        return old
    }

    def getShortenedWikiText(key) {
        wikiCache.get(wikiShortKey(key))?.get()
    }

    def removeShortenedWikiText(key) {
        wikiCache.evict wikiShortKey(key)
    }

    def putShortenedWikiText(key, value) {
        key = wikiShortKey(key)
        def old = getShortenedWikiText(key)
        wikiCache.put key, value
        return old
    }

    def getPluginList() { pluginCache.get(PLUGIN_LIST_KEY)?.getValue() }

    def putPluginList(content) {
        def old = pluginList
        pluginCache.put PLUGIN_LIST_KEY, content
        return old
    }

    def removePluginList() { pluginCache.evict PLUGIN_LIST_KEY }

    def removeCachedText(id) {
        // There is currently no way to evict a particular key from the
        // templates cache.
        grailsCacheAdminService.clearTemplatesCache()
    }

    protected getContentCache() { return grailsCacheManager.getCache(CONTENT_CACHE) }
    protected getWikiCache() { return grailsCacheManager.getCache(WIKI_CACHE) }
    protected getTextCache() { return grailsCacheManager.getCache(TEXT_CACHE) }
    protected getPluginCache() { return grailsCacheManager.getCache(PLUGIN_CACHE) }

    // Workaround for GPCACHEREDIS-1.
    protected wikiKey(String key) { return "wiki##${key}" }
    protected wikiShortKey(String key) { return "shortenedwiki##${key}" }
}
