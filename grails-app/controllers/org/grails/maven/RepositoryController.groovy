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

    def cacheService
    
    /**
     * Publishes a plugin. The expected request format is a XML payload that is the plugin descriptor with multipart files for the zip and the POM named "file" and "pom" 
     */
    def publish(PublishPluginCommand cmd) {
        log.debug "Got publish request for method ${request.method}"
        boolean isBrowserRequest = params.format == 'html'
        if(request.method == 'GET') {
            log.debug "Got GET request, rendering publish view"
            render view:"publish"
        }
        else if(request.method == 'POST' || isBrowserRequest) {
            def p = cmd.plugin
            def v = cmd.version
            if(cmd.hasErrors()) {
                if(isBrowserRequest){
                    return [publishCommand:cmd]
                }
                else {

                    log.debug "Publish plugin failed. Invalid data:\n  ${cmd.errors}"
                    render status:400, text:"Missing plugin data. Include name, version, pom, zip and xml in your multipart request"
                }
            }
            else {

                log.info "Publishing plugin [$p] with version [$v]"
                def existing = PluginRelease.where {
                    plugin.name == p && releaseVersion == v
                }

                if(!existing.exists() || v.endsWith("-SNAPSHOT")) {
                    log.debug "Plugin [$p:$v] does not existing. Creating pending release..."
                    def pendingRelease = new PendingRelease(pluginName:p, pluginVersion:v, zip:cmd.zip, pom:cmd.pom, xml:cmd.xml)
                    assert pendingRelease.save(flush:true) // assertion should never fail due to prior validation in command object                        


                    log.debug "Triggering plugin publish event for plugin [$p:$v]"
                    publishEvent(new PluginPublishEvent(pendingRelease))
                    if(isBrowserRequest) {
                        return [message:"Plugin published."]
                    }
                    else {
                        render status:200, text:"Published"
                    }
                }
                else {
                    log.debug "Plugin [$p:$v] already published. Operation forbidden"
                    if(isBrowserRequest) {
                        cmd.errors['plugin'] = 'plugin.publish.already.exists'
                        return [publishCommand:cmd]
                    }else {
                        render status:403, text:"Plugin [$p] already published for version [$v]"
                    }
                }
            }
        }
        else {
            log.debug "Unsupported HTTP method $request.method used in publish action"
            render status:405
        }
    }


    def pluginMeta() {
        render g.link(controller:"repository", action:"list", absolute:true, "plugins-list.xml")
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
    def artifact(String fullName, String plugin, String pluginVersion, String type) {       
        if(plugin && pluginVersion && type) {
            String key = "artifact:$plugin:$pluginVersion:$type".toString()
            def url = cacheService?.getContent(key)
            if(url == null) {
                def ext = type
                type = ".$type".toString()
                if(pluginVersion.endsWith("-plugin")) {
                    pluginVersion = pluginVersion[0..-8]
                    type = "-plugin$type"
                } else if(type == ".xml") {
                    type = "-plugin$type"
                }

                if(pluginVersion == '[revision]' || pluginVersion == 'latest.release' || pluginVersion == 'latest.integration') {
                    // calculate latest
                    def pr = findPluginRelease(plugin)
                    if(pr) {
                        pluginVersion = pr.releaseVersion
                    }
                }

                def snapshotVersion = pluginVersion
                if(snapshotVersion.endsWith("-SNAPSHOT")) {
                    // need to calculate actual version from maven metadata
                    def parent = new URL("http://repo.grails.org/grails/plugins/org/grails/plugins/$plugin/$pluginVersion/maven-metadata.xml")
                    try {
                        def metadata = parent.newReader(connectTimeout: 10000, useCaches: false).withReader { new XmlSlurper().parse(it) }
                        def timestamp = metadata.versioning.snapshot.timestamp.text()
                        def buildNo = metadata.versioning.snapshot.buildNumber.text()
                        if(timestamp && buildNo) {
                            snapshotVersion = pluginVersion - "-SNAPSHOT"
                            snapshotVersion = "$snapshotVersion-$timestamp-$buildNo"
                        }
                    }
                    catch(e) {                        
                        log.warn "Failed to parse maven metadata for $plugin: $e.message", e
                    }
                    
                }
                url = "http://repo.grails.org/grails/plugins/org/grails/plugins/$plugin/$pluginVersion/$plugin-${snapshotVersion}$type"                
                cacheService?.putContent(key, url)
            }
            
            redirect url:url
        
        } else {
            render status:404
        }
    }
    
    def grailsLinkGenerator
    def listLatest(String plugin) {
        String key = "artifact:list:latest:$plugin"
        def content = cacheService?.getContent(key)
        if(content) {
            render content
        }
        else {
            def pr = findPluginRelease(plugin)
            if(pr) {

                content = g.render( template:"listLatest", model: [plugin:plugin,release:pr, fullName:"$plugin-$pr.releaseVersion"] )
                cacheService?.putContent(key, content)
                render content
            }
            else {
                render status: 404
            }
            
        }
        
    }
    
    private findPluginRelease(String n) {
         return PluginRelease.where {
            plugin.name == n
         }.max(1)
          .order("releaseDate", "desc")
          .find()
    }
    
    def pluginService
    
    /**
     * Renders the plugin list as XML in a format compatible with all versions of Grails
     */
    @Cacheable("pluginMetaList")
    def list() {
        def total = Plugin.count()
        int offset = 0
        
        // get the most recent plugin release and use it as the last modified date
        def pr = PluginRelease.list(max:1, sort:'releaseDate', order:'desc')
        if(pr) {
            lastModified pr.releaseDate[0].toDate()
        }
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
        importFrom PendingRelease

        plugin blank:false
        version blank:false
    }

}

/**
 * Application event that notifies the plugin portal that the details of
 * a particular plugin need updating, perhaps due to a new release.
 */
class PluginPublishEvent extends ApplicationEvent {

    PluginPublishEvent(PendingRelease source) {
        super(source)
        if(source == null) {
            throw new IllegalArgumentException("Event source cannot be null")
        }

    }
}
