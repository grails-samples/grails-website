package org.grails.community

import org.grails.common.ApprovalStatus
import org.grails.auth.User
import org.joda.time.DateTime
import pl.burningice.plugins.image.ast.DBImageContainer

class Testimonial {

    String title
    String content
    String companyName
    User submittedBy
    boolean featured = false
    ApprovalStatus status = ApprovalStatus.PENDING

    static transients = ['shortDescription']

    DateTime dateCreated
    DateTime lastUpdated

    static constraints = {
        title blank: false, maxSize: 50
        status nullable: true
        submittedBy nullable: false
    }

    static namedQueries = {
        approved {
            eq "status", ApprovalStatus.APPROVED
        }
        allQuery {
            order "dateCreated", "desc"
        }
    }
}
