package org.grails.cache

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 19, 2008
*/
class CacheService {

    static final String CONTENT_CACHE = "content"
    static final String WIKI_CACHE = "wiki"

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
        wikiCache.get(key)?.get()
    }

    def removeWikiText(key) {
        wikiCache.evict key
    }

    def putWikiText(key, value) {
        def old = getWikiText(key)
        wikiCache.put key,value
        return old
    }

    def removeCachedText(id) {
        // There is currently no way to evict a particular key from the
        // templates cache.
        grailsCacheAdminService.clearTemplatesCache()
    }

    protected getContentCache() { return grailsCacheManager.getCache(CONTENT_CACHE) }
    protected getWikiCache() { return grailsCacheManager.getCache(WIKI_CACHE) }

}
