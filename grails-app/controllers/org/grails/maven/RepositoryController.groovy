package org.grails.maven

import grails.plugin.springcache.annotations.Cacheable
import org.grails.plugin.*

import org.springframework.context.ApplicationEvent
/**
 * 
 * Responsible for adapting Grails repository conventions onto a Maven compatible repository. Currently this is hard coded to http://repo.grails.org/grails/plugins.
 * Also handles deployment of plugins using the publish action.
 * 
 * @author Graeme Rocher
 */
class RepositoryController {

    /**
     * Publishes a plugin. The expected request format is a XML payload that is the plugin descriptor with multipart files for the zip and the POM named "file" and "pom" 
     */
    def publish(PublishPluginCommand cmd) {
        def p = cmd.plugin
        def v = cmd.version
        if(cmd.hasErrors()) {
            render status:400, text:"Missing plugin data. Include name, version, pom, zip and xml in your multipart request"
        }
        else {

            log.info "Publishing plugin [$p] with version [$v]"
            def existing = PluginRelease.where {
                plugin.name == p && releaseVersion == v
            }

            if(!existing.exists()) {
                def pendingRelease = new PendingRelease(pluginName:p, pluginVersion:v, zip:cmd.zip, pom:cmd.pom, xml:cmd.xml)
                assert pendingRelease.save(flush:true) // assertion should never fail due to prior validation in command object
                publishEvent(new PluginPublishEvent(pendingRelease))
            }
            else {
                render status:403, text:"Plugin [$p] already published for version [$v]"
            }
        }
    }
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
                        def allPlugins = Plugin.list(fetch:[releases:'select'], offset:offset, max:10)                    
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
class PublishPluginCommand {
    String plugin
    String version
    byte[] zip
    byte[] pom
    byte[] xml

    static constraints = {
        plugin blank:false
        version blank:false
        zip nullable:false, size:0..10000000
        pom nullable:false, size:0..500000
        xml nullable:false, size:0..500000
    }

}

/**
 * Application event that notifies the plugin portal that the details of
 * a particular plugin need updating, perhaps due to a new release.
 */
class PluginPublishEvent extends ApplicationEvent {

    PluginPublishEvent(PendingRelease source) {
        super(source)
    }
}
