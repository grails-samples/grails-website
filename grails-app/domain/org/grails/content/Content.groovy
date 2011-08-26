package org.grails.content

import org.grails.comments.*

class Content implements Serializable, Commentable {
    String title
    String body
    Boolean locked = false
    Date dateCreated
    Date lastUpdated

    static hasMany = [versions:Version]
    static transients = ["latestVersion"]

    static mapping = {
        body type:"text"
        cache 'nonstrict-read-write'
        title index: "title_idx"
    }

    static constraints = {
        title(blank:false)
        body(blank:true)
        locked(nullable:true)
    }

    def getLatestVersion() {
        Version.withCriteria(uniqueResult: true) {
            eq("current", this)
            order "number", "desc"
            maxResults 1
        }
    }
    
    Version createVersion() {
        def verObject = new Version(number:version, current:this)
        verObject.title = title
        verObject.body = body
        return verObject
    }
}
