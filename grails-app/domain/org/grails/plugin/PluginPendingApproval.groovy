package org.grails.plugin

import org.grails.auth.User
import org.grails.common.ApprovalStatus
import org.grails.content.GenericApprovalResponse
import org.joda.time.DateTime
import org.joda.time.Days

class PluginPendingApproval {

    String name
    String versionNumber
    String scmUrl
    ApprovalStatus status = ApprovalStatus.PENDING
    User submittedBy
    String notes
    DateTime dateCreated

    static constraints = {
        name blank: false, unique: 'versionNumber', matches:/\S+/
        versionNumber blank: false
        scmUrl blank: false
        status blank: false
        notes nullable: true, blank: true
        submittedBy nullable: false
    }

    static mapping = {
        notes type: 'text'
    }

    static namedQueries = {
        pending {
            eq('status', ApprovalStatus.PENDING)
        }
    }

    def setDisposition(GenericApprovalResponse genericApprovalResponse) {
        this.status = genericApprovalResponse.status
        this.save(flush: true)
    }

    List<GenericApprovalResponse> getGenericApprovalResponses() {
        def query = GenericApprovalResponse.where {
            whatType == this.class.name && whatId == this.id
        }
        return query.list(sort: 'id', order: 'asc')
    }

    def getIsNew() {
        (dateCreated > (new DateTime() - Days.days(14)))
    }

    String toString() {
        "PluginPendingApproval : $name ($id)"
    }
}
