package org.grails.helpers

import org.grails.common.ApprovalStatus

class CommonTagLib {

    static namespace = "common"

    def approvalStatus = { attrs ->
        ApprovalStatus status = attrs.status
        def type = attrs.type ?: 'label'

        def labelClass = "inverse"

        switch(status) {
            case ApprovalStatus.PENDING: labelClass = 'warning'; break;
            case ApprovalStatus.REJECTED: labelClass = 'important'; break;
            case ApprovalStatus.APPROVED: labelClass = 'success'; break;
        }

        out << "<span class=\"${type} ${type}-${labelClass}\">${status.toString()}</span>"
    }

}
