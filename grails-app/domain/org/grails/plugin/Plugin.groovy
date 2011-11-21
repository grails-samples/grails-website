package org.grails.plugin

import org.grails.taggable.Tag
import org.grails.taggable.Taggable
import org.grails.taggable.TagLink
import org.grails.comments.Commentable
import org.grails.rateable.Rateable

/*
 * author: Matthew Taylor
 */
class Plugin implements Taggable, Commentable, Rateable {

    static final def WIKIS = ['installation','description','faq','screenshots']
    static final def VERSION_PATTERN = /^(\d+(?:\.\d+)*)([\.\-\w]*)?$/

    transient cacheService
    transient grailsApplication
    transient pluginService
    transient taggableService

    String name
    String title
    String groupId = "org.grails.plugins"
    String summary
    PluginTab description
    PluginTab installation
    PluginTab faq
    PluginTab screenshots
    String author
    String authorEmail
    String currentRelease
    String organization
    String organizationUrl
    String documentationUrl
    String downloadUrl
    String scmUrl
    String issuesUrl
    String grailsVersion        // version it was developed against

    Boolean official = false    // specifies SpringSource support
    Boolean featured = false
    boolean zombie = false
    BigDecimal usage
    Number avgRating
    Date dateCreated
    Date lastUpdated
    Date lastReleased

    static hasMany = [licenses: License]

    static searchable = {
        only = [
            'name', 'title', 'summary', 'author', 'authorEmail',
            'installation','description','faq','screenshots', 'tags',
            'featured', 'official', 'organization'
        ]
        description component: true
        installation component: true
        faq component: true
        screenshots component: true
        currentRelease index: "no", store: "yes"
        grailsVersion index: "no", store: "yes"
        organizationUrl index: "no", store: "yes"
        documentationUrl index: "no", store: "yes"
        downloadUrl index: "no", store: "yes"
        scmUrl index: "no", store: "yes"
        issuesUrl index: "no", store: "yes"
        tags component: true
    }

    static transients = ['avgRating', 'fisheye', 'tags']

    static constraints = {
        name unique: true, matches: /[\w-]+/
        title blank: false
        groupId nullable: false, blank: false
        summary nullable: true
        description nullable: true
        installation nullable: true
        faq nullable: true
        screenshots nullable: true
        author nullable: true
        organization nullable: true
        organizationUrl nullable: true
        scmUrl nullable: true, blank: true
        issuesUrl nullable: true, blank: true
        grailsVersion nullable:true, blank:true, maxLength:16
        lastReleased nullable:true
        currentRelease blank: false, matches: VERSION_PATTERN
        usage nullable: true
    }

    static mapping = {
        cache 'nonstrict-read-write'
        summary type: 'text'
        usage column: '`usage`'
    }
    
    def getFisheye() {
        downloadUrl ? "${grailsApplication.config.plugins.fisheye}/grails-${name}" : ''
    }

    Collection<Tag> getTags() {
        if (!id) {
            return []
        }
        else {
            return TagLink.findAllByTagRefAndTypeInList(
                    id,
                    taggableService.domainClassFamilies[this.class.name],
                    [cache:true]).tag
        }
    }

    def onAddComment = { comment ->
        cacheService.flushWikiCache()
    }

    def isNewerThan(version) {
        pluginService.compareVersions(currentRelease, version) > 0
    }

    String toString() {
        "$name : $title"
    }
}
