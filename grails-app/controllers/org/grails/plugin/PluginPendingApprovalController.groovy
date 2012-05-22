package org.grails.plugin

import org.grails.content.GenericApprovalResponse
import org.grails.common.ApprovalStatus

class PluginPendingApprovalController {
    def pluginService
    def genericApprovalResponseService

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [
            pluginPendingApprovalList: PluginPendingApproval.list(params),
            pluginPendingApprovalTotal: PluginPendingApproval.count()
        ]
    }

    def show() {

        def pluginPendingApproval = PluginPendingApproval.get(params.id)

        if (pluginPendingApproval) {
            [ pluginPendingApproval: pluginPendingApproval ]

        } else {
            redirect action: 'list'
        }
    }

    def disposition = {
        def pluginPendingApprovalInstance = PluginPendingApproval.get(params.id)

        def genericApprovalResponse = new GenericApprovalResponse(
                submittedBy: pluginPendingApprovalInstance.submittedBy,
                moderatedBy: request.user,
                whatType: pluginPendingApprovalInstance.class.name,
                whatId: pluginPendingApprovalInstance.id,
                responseText: params.responseText,
                status: ApprovalStatus.valueOf(params.status)
        )

        if (!genericApprovalResponse.hasErrors() && genericApprovalResponse.save(flush: true)) {
            if (genericApprovalResponseService.setDispositionOfPendingApproval(genericApprovalResponse)) {
                flash.message = "Response was submitted to ${genericApprovalResponse.submittedBy?.login} (${genericApprovalResponse.submittedBy?.email})"
            }
            else {
                flash.message = "Unable to process the request including sending the email."
            }
        }
        else {
            println genericApprovalResponse.errors?.inspect()
            flash.message = "Unable to save response."
        }
        redirect action: 'show', id: pluginPendingApprovalInstance.id
    }


}
