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
    static final String TEXT_CACHE = "text"

    static final transactional = false

    def cacheManager

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
        textCache.clear()
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

    protected getContentCache() { return cacheManager.getCache(CONTENT_CACHE) }
    protected getWikiCache() { return cacheManager.getCache(WIKI_CACHE) }
    protected getTextCache() { return cacheManager.getCache(TEXT_CACHE) }

}
