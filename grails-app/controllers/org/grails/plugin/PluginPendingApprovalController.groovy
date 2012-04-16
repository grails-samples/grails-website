package org.grails.plugin

class PluginPendingApprovalController {

    def scaffold = PluginPendingApproval

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [
            pluginPendingApprovalList: PluginPendingApproval.list(params),
            pluginPendingApprovalTotal: PluginPendingApproval.count()
        ]
    }

    def show() {

        def pluginPendingApproval = PluginPendingApproval.findById(params.id)

        if (pluginPendingApproval) {

            def defaultResponses = [
                'approved': PluginPendingApprovalResponse.defaultApprovedResponse(pluginPendingApproval),
                'rejected': PluginPendingApprovalResponse.defaultRejectedResponse(pluginPendingApproval),
            ]

            [ pluginPendingApproval: pluginPendingApproval, defaultResponses: defaultResponses ]

        } else {
            redirect action: 'list'
        }
    }

    def dispositionPendingPlugin() {
        def pluginPendingApproval = PluginPendingApproval.findById(params.id)
        def pluginPendingApprovalResponse = new PluginPendingApprovalResponse(
            user: request.user,
            pluginPendingApproval: pluginPendingApproval,
            status: params.int('status'),
            responseText: params.responseText
        )
        if (!pluginPendingApprovalResponse.hasErrors() && pluginPendingApprovalResponse.save(flush: true)) {
            pluginPendingApproval.setDisposition(pluginPendingApprovalResponse)
            flash.message = "Response was submitted to ${pluginPendingApproval.user?.login} (${pluginPendingApproval.user?.email})"
        } else {
            println pluginPendingApprovalResponse.errors.inspect()
            flash.message = "Unable to save response."
        }
        redirect action: 'show', id: pluginPendingApproval.id
    }

}
