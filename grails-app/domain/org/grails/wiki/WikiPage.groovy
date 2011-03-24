package org.grails.wiki

import org.grails.content.Content
import org.grails.content.Version

class WikiPage extends Content {
    transient cacheService

    boolean deprecated
    String deprecatedUri
	
    static searchable = [only: ['body', 'title', 'deprecated']]

    static constraints = {
        title(blank:false, matches:/[^\/\\]+/, unique: true)
        body(blank:true)
        deprecatedUri(nullable: true, blank: true)
    }

    def onAddComment = { comment ->
        cacheService?.flushWikiCache()
    }

    String toString() {
        body
    }

}
