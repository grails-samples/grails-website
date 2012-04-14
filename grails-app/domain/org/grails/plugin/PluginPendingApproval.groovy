package org.grails.plugin

import org.grails.auth.User

class PluginPendingApproval {

    static final int STATUS_PENDING_APPROVAL = 1
    static final int STATUS_APPROVED = 2
    static final int STATUS_REJECTED = 3

    static belongsTo = [ user: User ]

    String name
    String scmUrl
    String email
    int status = STATUS_PENDING_APPROVAL
    String notes

    static constraints = {
        name blank: false, unique: true, matches: /[\w-]+/
        scmUrl blank: false
        email blank: false, email: true
        status blank: false, inList: [STATUS_PENDING_APPROVAL, STATUS_APPROVED, STATUS_REJECTED]
        notes nullable: true
    }

    static mapping = {
        notes type: 'text'
    }

}
