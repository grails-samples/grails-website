package org.grails.community

import org.grails.common.ApprovalStatus
import org.grails.auth.User
import org.joda.time.DateTime
import pl.burningice.plugins.image.ast.DBImageContainer
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
        status nullable: true
        submittedBy nullable: false
    }

    static namedQueries = {
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
