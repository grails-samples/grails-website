package org.grails.plugin

class PluginController {

    def pluginService

    def index() {
//        def plugins = pluginService.listNewestPluginsWithTotal()
        [
            tags: Plugin.getAllTags(),
            plugins: Plugin.list(params),
            pluginCount: Plugin.count()
        ]
//        plugins: pluginService.listNewestPluginsWithTotal()
    }

    def list() {
        def (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal()
        [
            tags: Plugin.getAllTags(),
            plugins: plugins,
            pluginCount: pluginCount
        ]
    }

    def plugin() {
        [plugin: Plugin.findByName(params.id)]
    }
}