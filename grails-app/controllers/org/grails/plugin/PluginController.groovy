package org.grails.plugin

class PluginController {

    def pluginService
    def tagService

    def list() {

        def plugins = []
        def pluginCount = 0
        def tag = params.tag ? params.tag.toString() : null
        def filter = params.filter ? params.filter.toString() : null

        if (tag) {
            (plugins, pluginCount) = pluginService.listPluginsByTagWithTotal([max: 200], params.tag)
        }
        else if (filter) {
            switch (filter) {
                case 'featured':
                    (plugins, pluginCount) = pluginService.listFeaturedPluginsWithTotal()
                    break
//                case 'top_installed':
//                    (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
//                    break
                case 'highest_voted':
                    (plugins, pluginCount) = pluginService.listPopularPluginsWithTotal()
                    break
                case 'recently_updated':
                    (plugins, pluginCount) = pluginService.listRecentlyUpdatedPluginsWithTotal()
                    break
                case 'newest':
                    (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
                    break
                case 'official':
                    (plugins, pluginCount) = pluginService.listPluginsByTagWithTotal([max: 200], 'springsource')
                    break
                default:
                    (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
            }
        }
        else {
            (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
        }

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