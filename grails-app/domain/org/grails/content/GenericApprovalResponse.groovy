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
        whatType blank: false
        status blank: false
    }
    
    static mapping = {
        cache true
    }

}
