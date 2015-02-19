package org.grails.community

import org.grails.common.ApprovalStatus
import org.grails.auth.User
import org.joda.time.DateTime
import org.grails.content.GenericApprovalResponse

class Testimonial {

    String title
    String body
    String companyName
    User submittedBy
    boolean featured = false
    ApprovalStatus status = ApprovalStatus.PENDING

    DateTime dateCreated
    DateTime lastUpdated

    static constraints = {
        title blank: false, maxSize: 50
        companyName nullable: true, maxSize: 255
        body blank: false
        status nullable: false
        submittedBy nullable: false
    }
    
    static mapping = {
        cache true
    }

    static namedQueries = {
        pending {
            eq 'status', ApprovalStatus.PENDING
        }
        approved {
            eq "status", ApprovalStatus.APPROVED
        }        
        nonFeaturedApproved {
            eq "featured", false
            eq "status", ApprovalStatus.APPROVED
            order "dateCreated", "desc"
        }
        featuredApproved {
            eq "featured", true
            eq "status", ApprovalStatus.APPROVED
            order "dateCreated", "desc"
        }
        allQuery {
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

    public String toString() {
        title
    }

}
