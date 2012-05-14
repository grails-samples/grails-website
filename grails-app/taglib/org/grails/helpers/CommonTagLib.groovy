package org.grails.helpers

import org.grails.common.ApprovalStatus

class CommonTagLib {

    static namespace = "common"

    def approvalStatus = { attrs ->
        ApprovalStatus status = attrs.status
        def type = attrs.type ?: 'label'

        def labelClass = "inverse"
        if (status == ApprovalStatus.PENDING) {
            labelClass = "warning"
        }
        else if (status == ApprovalStatus.REJECTED) {
            labelClass = "important"
        }
        else if (status == ApprovalStatus.APPROVED) {
            labelClass = "success"
        }

        out << "<span class=\"${type} ${type}-${labelClass}\">${status.toString()}</span>"
    }

}
