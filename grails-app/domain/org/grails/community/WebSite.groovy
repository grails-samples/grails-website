package org.grails.community

import org.joda.time.DateTime
import grails.plugin.like.Popularity
import org.grails.taggable.TagLink
import org.grails.taggable.Tag
import pl.burningice.plugins.image.ast.DBImageContainer
import org.grails.common.ApprovalStatus
import org.grails.auth.User
import org.grails.content.GenericApprovalResponse
import org.grails.taggable.Taggable

@DBImageContainer(field = "preview")
class WebSite implements Taggable {

    def taggableService

    String title
    String shortDescription
    String description
    String url
    boolean featured
    ApprovalStatus status = ApprovalStatus.PENDING
    User submittedBy
    Popularity popularity = new Popularity()
    DateTime dateCreated

    static constraints = {
        title blank: false, maxSize: 50
        shortDescription blank: false, nullable: true, maxSize: 150
        description blank: false, maxSize: 5000
        url blank: false, url: true
        submittedBy nullable: false
    }

    static hasMany = [genericApprovalResponses: GenericApprovalResponse]

    static embedded = ["popularity"]

    static transients = ['genericApprovalResponses']

    static searchable = {
        only = ["title", "shortDescription", "description"]
        title boost: 2.0
    }

    static namedQueries = {
        pending {
            eq 'status', ApprovalStatus.PENDING
        }

        allQueryNoSort {
        }

        approved {
            eq "status", ApprovalStatus.APPROVED
        }

        allQuery {
            allQueryNoSort()
            order "title", "asc"
        }

        notFeaturedQueryNoSort {
            approved()
            ne "featured", true
        }

        featuredQueryNoSort {
            approved()
            eq "featured", true
        }

        notFeaturedQuery {
            notFeaturedQueryNoSort()
            order "dateCreated", "desc"
        }

        featuredQuery {
            featuredQueryNoSort()
            order "dateCreated", "desc"
        }

        newestQueryNoSort {
            approved()
        }

        newestQuery {
            newestQueryNoSort()
            order "dateCreated", "desc"
        }

        popularQueryNoSort {
            approved()
            gt "popularity.netLiked", 0
        }

        popularQuery {
            popularQueryNoSort()
            order "popularity.netLiked", "desc"
        }
    }
    
    static mapping = {
        cache true
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
}
