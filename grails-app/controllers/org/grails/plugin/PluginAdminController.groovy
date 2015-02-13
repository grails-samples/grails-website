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

    // One time use data migration action
    def dataSource
    def fixTabs() {
        def sql = new groovy.sql.Sql(dataSource)

        sql.withTransaction {
            sql.eachRow("select * from plugin") { row ->

                def descId = row.description_id
                def installId = row.installation_id
                def faqId = row.faq_id
                def screenshotsId = row.screenshots_id

                def currentId = descId
                def name = row.name

                updateTab(sql, descId, "description", name, row.id )
                updateTab(sql, installId, "installation", name, row.id )
                updateTab(sql, faqId, "faq", name, row.id )
                updateTab(sql, screenshotsId, "screenshots", name, row.id )
            }


        }
        render "Done."
    }

    private updateTab(sql, id, type, pluginName, pluginId) {
        def key = "plugin-$pluginName-$type".toString()
        def current = sql.firstRow("select * from content where class = ? and id = ?", ["org.grails.plugin.PluginTab", id])
        def existing = sql.firstRow("select * from content where class = ? and title = ?", ["org.grails.plugin.PluginTab", key])

        
        if(current.title != key) {
            if(existing) {
                sql.executeUpdate "update plugin set "+type+"_id = ? where id = ?", [existing.id, pluginId]
                sql.executeUpdate "update content set body = ? where id = ?", [current.body, existing.id]
            }
            else {
                sql.executeUpdate "update content set title = ? where class = ? and id = ?", [key, "org.grails.plugin.PluginTab", id]    
            }                    
        }        
    }


    def searchableService
    def update(Long id, Long version) {

        def p = Plugin.get(id)
        println "updating plugin $p"
        if (!p) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: 'Plugin'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (p.version > version) {
                println "VERSION MISMATCH"
                p.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: '${domainClass.propertyName}.label', default: 'Plugin')] as Object[],
                          "Another user has updated this Plugin while you were editing")
                render(view: "edit", model: [pluginInstance: p])
                return
            }
        }

        bindData(p, params)

        try {
            searchableService.stopMirroring()
            if (!p.save(flush: true)) {
                println "VALIDATION ERRORS ${p.errors}"
                render(view: "edit", model: [pluginInstance: p])
                return
            }
            println "SAVED!"
        }
        finally {
             searchableService.startMirroring()

        }
            flash.message = message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: 'Plugin'), p.id])
        redirect(action: "show", id: p.id)
    }
}