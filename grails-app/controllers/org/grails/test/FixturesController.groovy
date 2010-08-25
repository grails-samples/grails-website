package org.grails.test

import org.grails.plugin.Plugin

class FixturesController {
    def fixtureLoader
    
    static allowedMethods = [ load: ['POST'] ]
    
    def load = {
        // Which fixture should I load?
        fixtureLoader.load(params.file)

        Plugin.withTransaction {
            Plugin.list().each { p ->
                println "Found plugin: ${p.name} - ${p.title}"
            }
        }
        
        render "Fixtures loaded"
    }
}
