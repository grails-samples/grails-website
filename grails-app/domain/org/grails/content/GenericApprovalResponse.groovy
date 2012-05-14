package org.grails.content

import org.grails.common.ApprovalStatus
import org.grails.auth.User

class GenericApprovalResponse {

    User submittedBy
    User moderatedBy
    String whatType
    Long whatId
    String responseText
    ApprovalStatus status
    Date dateCreated

    static constraints = {
        submittedBy nullable: false
        moderatedBy nullable: false
        whatType blank: false, nullable: false
        whatId nullable: false
        status nullable: false, blank: false
    }

}
