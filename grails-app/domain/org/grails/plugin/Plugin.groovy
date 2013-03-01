package org.grails.plugin

import org.grails.meta.UserInfo
import org.grails.taggable.Tag
import org.grails.taggable.Taggable
import org.grails.taggable.TagLink
import org.grails.rateable.Rateable

import org.joda.time.DateTime

/*
 * author: Matthew Taylor
 */
class Plugin implements Taggable, Rateable {
    static final WHITE_LIST = [
            "title",
            "groupId",
            "summary",
            "defaultDependencyScope",
            "currentRelease",
            "organization",
            "organizationUrl",
            "documentationUrl",
            "downloadUrl",
            "scmUrl",
            "issuesUrl",
            "grailsVersion" ]

    static final DEFAULT_GROUP = "org.grails.plugins"
    static final DEFAULT_SCOPE = "compile"
    static final WIKIS = ['installation','description','faq','screenshots']
    static final VERSION_PATTERN = /^(\d+(?:\.\d+)*)([\.\-\w]*)?$/

    transient cacheService
    transient grailsApplication
    transient pluginService
    transient taggableService

    String name
    String title
    String groupId = DEFAULT_GROUP
    String summary
    String defaultDependencyScope = DEFAULT_SCOPE
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
    DateTime dateCreated
    DateTime lastUpdated
    DateTime lastReleased = new DateTime()

    List authors
    List mavenRepositories

    static hasMany = [licenses: License, mavenRepositories: String, authors: UserInfo, releases: PluginRelease]

    static searchable = {
        only = [
            'name', 'title', 'summary', 'author', 'authorEmail',
            'installation','description','faq','screenshots', 'tags',
            'featured', 'official', 'organization'
        ]
        title boost: 5.0
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
        avgRating index: "not_analyzed", store: "yes"
        ratingCount index: "not_analyzed", store: "yes"
        lastReleased index: "no", store: "yes"
        tags component: true
    }

    static transients = [
            'avgRating',
            'ratingCount',
            'fisheye',
            'tags',
            'dependencyDeclation',
            'customRepositoriesDeclaration',
            'inDefaultGroup' ]

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
        authorEmail nullable: true
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
    
    String getFisheye() {
        downloadUrl ? "${grailsApplication.config.plugins.fisheye}/grails-${name}" : ''
    }
    
    String getDependencyDeclaration() {
        return "${inDefaultGroup ? '' : groupId}:${name}:${currentRelease}"
    }
    
    String getCustomRepositoriesDeclaration() {
        if (!mavenRepositories.size()) return null

        return mavenRepositories.collect { url -> "mavenRepo \"${url}\"" }.join('\n')
    }

    boolean isInDefaultGroup() {
        return groupId == DEFAULT_GROUP
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

    Double getAvgRating() {
        // Dynamic call to method added by Rateable plugin.
        return averageRating
    }

    Integer getRatingCount() {
        return totalRatings
    }

    def onAddComment = { comment ->
        cacheService.flushWikiCache()
    }

    def isNewerThan(version) {
        pluginService.compareVersions(currentRelease, version) > 0
    }

    def isScmGitHub() {
        if (!scmUrl) return false
        def pattern = ~/.*github.*/
        def matches = pattern.matcher(scmUrl).matches()
        return matches
    }

    String toString() {
        "$name : $title"
    }

    static Collection<Tag> getAllTags() {
        TagLink.findAllByType('Plugin')
    }
}
