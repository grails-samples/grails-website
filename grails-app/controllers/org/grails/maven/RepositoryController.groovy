package org.grails.maven

import groovy.xml.MarkupBuilder
import org.grails.plugin.*

import org.springframework.context.ApplicationEvent
/**
 * 
 * Responsible for adapting Grails repository conventions onto a Maven compatible repository.
 * Defaults to http://repo.grails.org/grails/plugins. Also handles deployment of plugins using
 * the publish action.
 * 
 * @author Graeme Rocher
 */
class RepositoryController {

    def cacheService
    def grailsApplication
    
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

                // We disallow re-publication of non-snapshot releases, so find
                // out whether this version has been published before.
                def existing = PluginRelease.where {
                    plugin.name == p && releaseVersion == v && isSnapshot == false
                }

                if (!existing.exists()) {
                    log.debug "Plugin [$p:$v] does not exist or is snapshot. Creating pending release..."
                    createPendingRelease p, v, cmd

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
        render '<a href="http://plugins.grails.org/.plugin-meta/plugins-list.xml">plugins-list.xml</a><a href="http://grails.org/plugins/.plugin-meta/plugins-list.xml">plugins-list.xml</a>'
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
            type = getCorrectType(fullName, type)

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

                // WARNING These aliases work on the basis of release date, not
                // version, so if a developer publishes an older version after a
                // newer one, the older one takes precedence as the 'latest' release.
                // This pretty much matches the old svn.codehaus.org behaviour, but
                // it's counter-intuitive for users.
                if(pluginVersion == '[revision]' || pluginVersion == 'latest.release') {
                    // Use the most recent non-snapshot release.
                    pluginVersion = findLatestNonSnapshotPluginRelease(plugin) ?: pluginVersion
                } else if(pluginVersion == 'latest.integration') {
                    // Use the most recent release, including snapshots.
                    pluginVersion = findLatestPluginRelease(plugin) ?: pluginVersion
                }

                def snapshotVersion = pluginVersion
                if(snapshotVersion.endsWith("-SNAPSHOT")) {
                    // need to calculate actual version from maven metadata
                    def parent = new URL("${repoUrl}/org/grails/plugins/$plugin/$pluginVersion/maven-metadata.xml")
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
                        log.debug "Failed to parse maven metadata for $plugin: $e.message"
                    }
                    
                }
                url = "${repoUrl}/org/grails/plugins/$plugin/$pluginVersion/$plugin-${snapshotVersion}$type"                
                cacheService?.putContent(key, url)
            }
            
            redirect url:url
        
        } else {
            render status:404
        }
    }
    
    def listLatest(String plugin) {
        String key = "artifact:list:latest:$plugin"
        def content = cacheService?.getContent(key)
        if(content) {
            render content
        }
        else {
            def pr = findLatestNonSnapshotPluginRelease(plugin)
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
    
    protected getRepoUrl() {
        def repoUrl = grailsApplication.config.artifactRepository.url ?: "http://repo.grails.org/grails/plugins"
        return repoUrl.endsWith("/") ? repoUrl[0..-2] : repoUrl
    }
    
    private findLatestNonSnapshotPluginRelease(String n) {
        def query = PluginRelease.where {
            plugin.name == n && isSnapshot == false
        }
        def latestPlugin = query.get(sort:'releaseDate', order:'desc', max: 1)
        if (latestPlugin) {
            return latestPlugin
        } else {
            throw new Exception("PluginRelease not found with Plugin name of $n")
        }
    }
    
    private findLatestPluginRelease(String n) {
        def query = PluginRelease.where {
            plugin.name == n && isSnapshot == false
        }
        def latestPlugin = query.get(sort:'releaseDate', order:'desc', max: 1)
        if (latestPlugin) {
            return latestPlugin
        } else {
            throw new Exception("PluginRelease not found with Plugin name of $n")
        }
    }

    protected createPendingRelease(name, version, files) {
        // First remove any existing pending entries for this plugin.
        def pendingRelease = PendingRelease.where { pluginName == name && pluginVersion == version }.get()
        if (pendingRelease) pendingRelease.delete()

        pendingRelease = new PendingRelease(
                pluginName: name,
                pluginVersion: version,
                zip: files.zip,
                pom: files.pom,
                xml: files.xml).save(flush: true)
        assert pendingRelease // assertion should never fail due to prior validation in command object

        log.debug "Triggering plugin publish event for plugin [$name:$version]"
        publishEvent(new PluginPublishEvent(pendingRelease))
    }
    
    def pluginService
    
    /**
     * Renders the plugin list as XML in a format compatible with all versions of Grails
     */
    def list() {
        def content = cacheService.getPluginList()
        if (!content) {
            content = generatePluginListXml()
            cacheService.putPluginList(content)
        }

        // get the most recent plugin release and use it as the last modified date
        def pr = PluginRelease.get(max:1, sort:'releaseDate', order:'desc')
        if(pr) {
            lastModified pr.releaseDate.toDate()
        }

        render text: content, contentType: "text/xml"
    }

    private String generatePluginListXml() {
        final total = Plugin.count()
        int offset = 0
        def writer = new StringWriter(1600000)
        new MarkupBuilder(writer).plugins {

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

        return writer.toString()
    }

    /**
     * When the requested file is a checksum (.sha1 or .md5), the <em>actual</em>
     * type is encoded in the "full name" part of the file. This method extracts
     * the real type and appends the checksum part. For example,
     * <tt>grails-shiro-1.0.0.zip.sha1</tt> has a type of 'sha1' and the '.zip'
     * is included in the <tt>fullName</tt> parameter. In this case, the method
     * will return 'zip.sha1'. For non-checksum files, the type is returned as
     * is, e.g. if <tt>type</tt> is 'zip' the method returns 'zip'.
     */
    protected getCorrectType(String fullName, String type) {
        if (type != 'md5' && type != 'sha1') return type

        def pos = fullName.lastIndexOf('.')
        if (pos != -1) return fullName.substring(pos + 1) + '.' + type
        else return type
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
