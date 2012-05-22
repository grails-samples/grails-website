package org.grails.learn

import grails.plugin.like.Popularity
import org.joda.time.DateTime
import org.grails.taggable.Tag
import org.grails.taggable.TagLink
import org.grails.taggable.Taggable
import org.grails.common.ApprovalStatus
import org.grails.auth.User
import org.grails.content.GenericApprovalResponse
import org.joda.time.Days

class Tutorial implements Taggable {

    def taggableService

    String title
    String description
    String url
    ApprovalStatus status = ApprovalStatus.PENDING
    User submittedBy
    Popularity popularity = new Popularity()
    DateTime dateCreated

    static constraints = {
        title blank: false, maxSize: 50
        description blank: false, maxSize: 5000
        url blank: false
        status nullable: true
        submittedBy nullable: false
    }

    static hasMany = [genericApprovalResponses: GenericApprovalResponse]

    static embedded = ["popularity"]

    static transients = ['genericApprovalResponses', 'isNew']

    static searchable = {
        only = ["title", "description", "tags"]
        tags component: true
    }

    static namedQueries = {
        approved {
            eq "status", ApprovalStatus.APPROVED
        }
        allQuery {
            approved()
            order "dateCreated", "desc"
        }
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

    def getIsNew() {
        (dateCreated > (new DateTime() - Days.days(14)))
    }
}
