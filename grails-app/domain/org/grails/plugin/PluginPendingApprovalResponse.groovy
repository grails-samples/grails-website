package org.grails.plugin

import org.grails.auth.User

class PluginPendingApprovalResponse {

    static belongsTo = [user: User, pluginPendingApproval: PluginPendingApproval]

    String responseText
    ApprovalStatus status
    Date dateCreated

    static constraints = {
        status blank: false
    }

    static String defaultApprovedResponse(PluginPendingApproval pluginPendingApproval) {
        return """Hi ${pluginPendingApproval?.user?.login},

Your plugin '${pluginPendingApproval?.name}' has been approved.

Thanks for your contribution!"""
    }

    static String defaultRejectedResponse(PluginPendingApproval pluginPendingApproval) {
        return """Hi ${pluginPendingApproval.user?.login},

Your plugin '${pluginPendingApproval?.name}' has not been approved for the following reasons:

"""
    }

    // BOO ON ME!!! This is not DRY and Cross Dependent!!!
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
}
