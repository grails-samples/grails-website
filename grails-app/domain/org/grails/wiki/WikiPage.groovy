package org.grails.wiki

import org.grails.content.Content
import org.grails.content.Version

class WikiPage extends Content {

    transient cacheService

    boolean deprecated
    String deprecatedUri
    
    Version createVersion() {
        def verObject = new Version(number:version, current:this)
        verObject.title = title
        verObject.body = body
        return verObject
    }
	
    static hasMany = [versions:Version]
    static transients = ["latestVersion"]
    static searchable = [only: ['body', 'title']]

    static constraints = {
        title(blank:false, matches:/[^\/\\]+/)
        body(blank:true)
        deprecatedUri(nullable: true, blank: true)
    }

    def onAddComment = { comment ->
        cacheService?.flushWikiCache()
    }

    def getLatestVersion() {
        Version.withCriteria(uniqueResult: true) {
            eq("current", this)
            order "number", "desc"
            maxResults 1
        }
    }

    String toString() {
        body
    }

}
