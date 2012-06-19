package org.grails.cache

import net.sf.ehcache.Ehcache
import net.sf.ehcache.Element

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 19, 2008
*/
class CacheService {
    private static final String PLUGIN_LIST_KEY = "pluginListKey"

    static transactional = false

    Ehcache contentCache
    Ehcache wikiCache
    Ehcache textCache
    Ehcache pluginListCache

    def getContent(key) {  
        contentCache.get(key)?.getValue()
    }

    def putContent(key,value) {
        def old = getContent(key)
        contentCache.put(new Element(key,value))
        return old
    }

    def removeContent(key) {
        contentCache.remove key
    }

    def flushWikiCache() {
        wikiCache.flush()
        textCache.flush()
    }
    def getWikiText(key) {
        wikiCache.get(key)?.getValue()
    }

    def removeWikiText(key) {
        wikiCache.remove key
    }

    def putWikiText(key,value) {
        def old = getWikiText(key)
        wikiCache.put(new Element(key,value))
        return old
    }

    def getPluginList() { pluginListCache.get(PLUGIN_LIST_KEY)?.getValue() }

    def putPluginList(content) {
        def old = getContent(PLUGIN_LIST_KEY)
        pluginListCache.put(new Element(PLUGIN_LIST_KEY, content))
        return old
    }

    def removePluginList() { pluginListCache.remove PLUGIN_LIST_KEY }
}
