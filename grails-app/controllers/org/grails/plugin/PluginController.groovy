package org.grails.plugin

class PluginController {

    def pluginService
    def tagService

    def list() {
        def plugins = []
        def pluginCount = 0

        def filter = params.filter ? params.filter.toString() : null
        if (filter) {
            (plugins, pluginCount) = pluginService."list${filter}PluginsWithTotal"()
        }
        else {
            (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
        }

        def tags = tagService.getPluginTagArray()
        [ tags: tags, plugins: plugins, pluginCount: pluginCount ]
    }

    def listByTag() {
        def tag = params.tag ? params.tag.toString() : null
        def (plugins, pluginCount) = pluginService.listPluginsByTagWithTotal([max: 200], params.tag)
        def tags = tagService.getPluginTagArray()
        render view: 'list', model: [ tags: tags, plugins: plugins, pluginCount: pluginCount ]
    }

    def plugin() {
        def plugin = Plugin.findByName(params.id)
        def tags = tagService.getPluginTagArray()
        [ plugin: plugin, tags: tags ]
    }

    def submitPlugin() {
        def pluginPendingApproval = new PluginPendingApproval(
            user: request.user,
            status: ApprovalStatus.PENDING
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