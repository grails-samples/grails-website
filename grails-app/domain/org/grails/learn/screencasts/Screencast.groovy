package org.grails.learn.screencasts

import org.grails.common.ApprovalStatus
import org.grails.auth.User
import grails.plugin.like.Popularity
import org.grails.content.GenericApprovalResponse
import org.joda.time.DateTime
import org.joda.time.Days
import org.grails.taggable.Taggable
import org.grails.taggable.Tag
import org.grails.taggable.TagLink

class Screencast implements Taggable {

    def taggableService

    String title
    String description
    String videoId
    VideoHost videoHost
    ApprovalStatus status = ApprovalStatus.PENDING
    User submittedBy
    Popularity popularity = new Popularity()
    DateTime dateCreated
    DateTime lastUpdated

    static constraints = {
        title blank:false
        description nullable:true
        videoId nullable: true, blank: false
        videoHost nullable: true, blank: false
        status nullable: true
        submittedBy nullable: false
    }

    static mapping = {
        cache true
        description type:"text"
    }

    static embedded = ["popularity"]

    static transients = ['genericApprovalResponses', 'isNew']

    static searchable = {
        only = ["title"]
        tags component: true
    }

    static namedQueries = {
        pending {
            eq 'status', ApprovalStatus.PENDING
        }
        approved {
            eq "status", ApprovalStatus.APPROVED
        }
        allQuery {
            approved()
            order "dateCreated", "desc"
        }
    }

    def setDisposition(GenericApprovalResponse genericApprovalResponse) {
        this.status = genericApprovalResponse.status
        this.save(flush: true)
    }

    List<GenericApprovalResponse> getGenericApprovalResponses() {
        def query = GenericApprovalResponse.where {
            whatType == this.class.name && whatId == this.id
        }
        return query.list(sort: 'id', order: 'asc')
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

    def getIsNew() {
        (dateCreated > (new DateTime() - Days.days(14)))
    }
}
