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
    def fixTabs() {
        Thread.start {
            try {
                Plugin.withTransaction { status ->
                    Plugin.withNewSession { session ->
                        def total = Plugin.count()
                        def offset = 0

                        while(offset < total) {
                            def plugins = Plugin.list(offset:offset, max:10)
                            for(p in plugins) {
                                boolean pluginUpdated = false
                                if(p.description.title != "plugin-${p.name}-description".toString()) {
                                    def key = "plugin-${p.name}-description"

                                    def existing = PluginTab.findByTitle(key)
                                    if(existing && existing.version == 0) {
                                        existing.body = p.description.body
                                        p.description = existing
                                        p.save()
                                    } 
                                    else {
                                        p.description.title = key
                                        p.description.save(flush:true)
                                        pluginUpdated = true                                        
                                    }
                                }
                                if(p.installation.title != "plugin-${p.name}-installation".toString()) {

                                    def key = "plugin-${p.name}-installation"
                                    def existing = PluginTab.findByTitle(key)
                                    if(existing && existing.version == 0) {
                                        existing.body = p.installation.body
                                        p.installation = existing
                                        p.save()
                                    } 
                                    else {
                                        p.installation.title = key
                                        p.installation.save(flush:true)
                                        pluginUpdated = true                                        
                                    }
                                }
                                if(p.faq.title != "plugin-${p.name}-faq".toString()) {

                                    def key = "plugin-${p.name}-faq"
                                    def existing = PluginTab.findByTitle(key)
                                    if(existing && existing.version == 0) {
                                        existing.body = p.faq.body
                                        p.faq= existing
                                        p.save()
                                    } 
                                    else {
                                        p.faq.title = key
                                        p.faq.save(flush:true)
                                        pluginUpdated = true                                        
                                    }
                                }
                                if(p.screenshots.title != "plugin-${p.name}-screenshots".toString()) {

                                    def key = "plugin-${p.name}-screenshots"
                                    def existing = PluginTab.findByTitle(key)
                                    if(existing && existing.version == 0) {
                                        existing.body = p.screenshots.body
                                        p.screenshots= existing
                                        p.save()
                                    } 
                                    else {
                                        p.screenshots.title = key
                                        p.screenshots.save(flush:true)
                                        pluginUpdated = true                                        
                                    }
                                }                                                
                                if(pluginUpdated) {
                                    log.info "plugin $p.name updated"
                                }
                                else {
                                    log.info "Plugin $p.name is up-to-date"
                                }
                            }
                            offset += 10
                            session.flush()
                            session.clear()
                        }

                    }
                    
                }

            }
            catch(e) {
                log.error "${e.class.name}, Message: ${e.message}", e
            }
        }
       

        render "Thread started."
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

        p.properties = params

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