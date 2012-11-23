package org.grails.wiki

import org.grails.content.Content
import org.grails.content.Version

class WikiPage extends Content {
    transient cacheService

    boolean deprecated
    String deprecatedUri

    static searchable = {
        only = ['body', 'title', 'deprecated']
        title boost: 2.0
    }

    static transients = ['cacheService']
    static constraints = {
        title(blank:false, matches:/[^\/\\]+/)
        body(blank:true)
        deprecatedUri(nullable: true, blank: true)
    }

    static mapping = {
        body type: 'text'
    }

    def onAddComment = { comment ->
        cacheService?.flushWikiCache()
    }

    String toString() {
        body
    }

}
