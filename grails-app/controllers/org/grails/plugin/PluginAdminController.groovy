package org.grails.plugin

class PluginAdminController {

	def scaffold = Plugin

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pluginList: Plugin.list(params), pluginTotal: Plugin.count()]
    }

    def search(String q) {
      def max = Math.min(params.max ? params.int('max') : 10, 100)
      def searchResults = Plugin.search(q, [max: max, offset: params.int('offset', 0)])
      render view:"list", model:[pluginList: searchResults.results, pluginTotal:searchResults.total]        
    }

    def show() {
        def plugin = Plugin.get(params.id)
        if (!plugin) {
            flash.message = "Plugin not found"
            redirect(action: "list")
        }
        else {
            [plugin: plugin]
        }
    }

}