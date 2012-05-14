package org.grails.plugin

import org.grails.auth.User
import org.grails.common.ApprovalStatus

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
                ret = '<span class="badge badge-info">Pending Approval</span>'
                break
            case ApprovalStatus.APPROVED:
                ret = '<span class="badge badge-success">Approved</span>'
                break
            case ApprovalStatus.REJECTED:
                ret = '<span class="badge badge-error">Rejected</span>'
                break
            default:
                ret = '<span class="badge badge-warning">UNKNOWN</span>'
        }
        return ret
    }

    def setDisposition(PluginPendingApprovalResponse pluginPendingApprovalResponse) {
        this.status = pluginPendingApprovalResponse.status
        this.save(flush: true)
    }

}
