package org.grails.wiki

import org.grails.content.Content

/*
 * author: Matthew Taylor
 */
class WikiPageService {

    def cacheService
    
    def getCachedOrReal(id) {
         id = id.decodeURL()

         def wikiPage = cacheService.getContent(id)
            if(!wikiPage) {
                wikiPage = Content.findByTitle(id, [cache:true])
                if(wikiPage) cacheService.putContent(id, wikiPage)
            }
         return wikiPage
    }

    def pageChanged(id) {
        id = id.decodeURL()
        cacheService.removeContent(id)
    }
}
