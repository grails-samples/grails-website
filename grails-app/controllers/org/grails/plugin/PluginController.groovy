package org.grails.plugin

class PluginController {

    def pluginService

    def index() {
        [plugins: pluginService.listNewestPluginsWithTotal()]
    }

    def plugin() {
        [plugin: Plugin.findByName(params.id)]
    }
}