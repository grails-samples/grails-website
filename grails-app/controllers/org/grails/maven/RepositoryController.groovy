package org.grails.maven

import grails.plugin.springcache.annotations.Cacheable
import org.grails.plugin.*

/**
 * 
 *
 * @author Graeme Rocher
 */
class RepositoryController {

    /**
     * Redirects Grails SVN-style repository to the Maven repository. Request to the path:
     *
     * /grails-heroku/tags/RELEASE_1_0_SNAPSHOT/grails-heroku-1.0-SNAPSHOT.zip
     *
     * Becomes
     *
     * http://repo.grails.org/grails/plugins/org/grails/plugins/heroku/1.0-SNAPSHOT/heroku-1.0-SNAPSHOT.zip
     *
     */
    def artifact(String fullName, String plugin, String version, String type) {       
        if(plugin && version && type)
            redirect url:"http://repo.grails.org/grails/plugins/org/grails/plugins/$plugin/$version/$plugin-${version}.$type"
        else
            render status:404
    }
    
    def pluginService
    
    /**
     * Renders the plugin list as XML in a format compatible with all versions of Grails
     */
    @Cacheable("pluginMetaList")
    def list() {
        def total = Plugin.count()
        int offset = 0
        
        render(contentType:"text/xml") {
            plugins {

                Plugin.withSession { session ->
                    
                    while(total > offset) {
                        def allPlugins = Plugin.list(fetch:['releases':'eager'], offset:offset, max:10)                    
                        if(!allPlugins) break
                        
                        for(p in allPlugins) {
                            def latest = p.releases.max { it.releaseDate }
                            plugin(name:p.name, 'latest-release':latest?.releaseVersion) {
                                p.releases?.each { r ->
                                    release(version:r.releaseVersion) {
                                        title p.title
                                        author p.author
                                        authorEmail p.authorEmail
                                        description p.summary
                                        file r.downloadUrl
                                    }
                                }

                            }
                        }
                        offset += 10
                        session.clear()                        
                    }
                    

                }
                
            }
        }
        
    }
}
