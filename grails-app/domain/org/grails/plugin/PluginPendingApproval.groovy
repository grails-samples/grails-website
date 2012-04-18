package org.grails.plugin

import org.grails.auth.User

class PluginPendingApproval {

    static belongsTo = [ user: User ]
    static hasMany = [ pluginPendingApprovalResponses: PluginPendingApprovalResponse ]

    String name
    String scmUrl
    String email
    ApprovalStatus status
    String notes

    static constraints = {
        name blank: false, unique: true, matches: /[\w-]+/
        scmUrl blank: false
        email blank: false, email: true
        status blank: false
        notes nullable: true
    }

    static mapping = {
        notes type: 'text'
    }

    String displayStatus() {
        def ret = ""
        switch (status) {
            case ApprovalStatus.PENDING:
                ret = 'Pending Approval'
                break
            case ApprovalStatus.APPROVED:
                ret = 'Approved'
                break
            case ApprovalStatus.REJECTED:
                ret = 'Rejected'
                break
            default:
                ret = 'UNKNOWN'
        }
        return ret
    }

    def setDisposition(PluginPendingApprovalResponse pluginPendingApprovalResponse) {
        this.status = pluginPendingApprovalResponse.status
        this.save(flush: true)

        // Trigger email sending
    }

}
