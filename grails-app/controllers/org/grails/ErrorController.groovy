package org.grails

import org.grails.plugin.PluginTab
import org.grails.wiki.WikiPage

class ErrorController {
    def wikiPageService
    
    private boolean hasWikiPage(String id) {
        def wikiPage = wikiPageService.getCachedOrReal(id)
        return ((wikiPage?.instanceOf(PluginTab) && wikiPage.plugin) || wikiPage?.instanceOf(WikiPage)) 
    }
    
    def serverError() {}
    def devError() {}
    def notFound() {
        def originalUri = request.getAttribute('javax.servlet.error.request_uri')
        def parts = originalUri.split('/')
        if(parts.size()==2) {
             def wikiPageId = parts[1].decodeURL()
             if(hasWikiPage(wikiPageId)) {
                 redirect controller: "content", action: "index", id: wikiPageId
                 return false
             }   
        }
        true
    }
}
