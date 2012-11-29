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

    def control = { attrs, body ->
        out << g.render(template:'/common/inputControl', model:[field: attrs.field, bean: attrs.bean, title:attrs.title, desc:attrs.desc, body: body()])
    }
    
}
