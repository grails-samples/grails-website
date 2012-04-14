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
        def pluginPendingApproval = new PluginPendingApproval()
        pluginPendingApproval.user = request.user
        if (request.method == "POST") {
            pluginPendingApproval.name = params.name
            pluginPendingApproval.scmUrl = params.scmUrl
            pluginPendingApproval.email = params.email
            if (!pluginPendingApproval.hasErrors() && pluginPendingApproval.save(flush:true)) {
                flash.message = "Your plugin has been submitted for approval"
            }
        }
        [pluginPendingApproval: pluginPendingApproval]
    }


}