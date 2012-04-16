package org.grails.plugin

class PluginController {

    def pluginService
    def tagService

    def list() {
        def (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
        def tags = tagService.getPluginTagArray()
        [ tags: tags, plugins: plugins, pluginCount: pluginCount ]
    }

    def plugin() {
        def plugin = Plugin.findByName(params.id)
        def tags = tagService.getPluginTagArray()
        [ plugin: plugin, tags: tags ]
    }

    def submitPlugin() {
        def pluginPendingApproval = new PluginPendingApproval(
            user: request.user,
            status: PluginPendingApproval.STATUS_PENDING_APPROVAL
        )
        if (request.method == "POST") {
            pluginPendingApproval.name = params.name
            pluginPendingApproval.scmUrl = params.scmUrl
            pluginPendingApproval.email = params.email
            pluginPendingApproval.notes = params.notes
            if (!pluginPendingApproval.hasErrors() && pluginPendingApproval.save(flush:true)) {
                flash.message = "Your plugin has been submitted for approval"
                pluginPendingApproval = new PluginPendingApproval(user: request.user)
                params.clear()
            } else {
                flash.message = "Please correct the fields below"
            }
        }
        [pluginPendingApproval: pluginPendingApproval]
    }

}