package org.grails.maven

import org.apache.commons.codec.digest.*
import org.grails.plugin.*
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.transaction.annotation.Transactional
import grails.plugins.rest.client.*
/**
 * Responsible for taking a PendingRelease and publishing it to Artifactory
 *
 * @author Graeme Rocher
 */
class PluginDeployService implements ApplicationListener<PluginPublishEvent>{

    String releaseUrl = "http://repo.grails.org/grails/plugins-releases-local/org/grails/plugins"
    String snapshotUrl = "http://repo.grails.org/grails/plugins-snapshots-local/org/grails/plugins"
    String deployUsername = System.getProperty("artifactory.user")
    String deployPassword = System.getProperty("artifactory.pass")
    RestBuilder rest = new RestBuilder(connectTimeout:1000, readTimeout:10000)

    @Transactional
    void onApplicationEvent(PluginPublishEvent event) {
        PendingRelease pendingRelease = event.source
        log.debug "Received plugin publish event for pending release [$event.source]"        
        deployRelease(pendingRelease)
    }
    
    /**
     * Takes a PendingRelease and deploys it to Artifactory. The release is deleted is successful.
     *
     * @param pendingRelease The PendingRelease instance
     *
     */
    def deployRelease(PendingRelease pendingRelease) {
        def url = pendingRelease.pluginVersion.endsWith("-SNAPSHOT") ? snapshotUrl : releaseUrl
        log.info "Deploying plugin [$pendingRelease.pluginName] with version [$pendingRelease.pluginVersion] to URL $url"

        deployArtifact(rest, url, pendingRelease, "zip")
        deployArtifact(rest, url, pendingRelease, "pom")
        deployArtifact(rest, url, pendingRelease, "xml")

        pendingRelease.delete flush:true
    }

    protected deployArtifact(rest,baseUrl, pendingRelease, type) {
        
            def p = pendingRelease.pluginName
            def v = pendingRelease.pluginVersion
            def ext = type == 'xml' ? '-plugin.xml' : ".$type"
            def uri = "$baseUrl/$p/$v/$p-${v}$ext"
            log.info "Uploading $type to Artifactory URL $uri"
            
            def resp = rest.put(uri) {
                auth deployUsername, deployPassword
                body pendingRelease[type]
            }

            log.debug "Received artifactory response: ${resp?.text}"

            uri = "$baseUrl/$p/$v/$p-${v}${ext}.md5"

            log.debug "Uploading MD5 checksum $uri"
            resp = rest.put(uri) {
                auth deployUsername, deployPassword
                body DigestUtils.md5Hex(pendingRelease[type])
            }

            log.debug "Received artifactory response: ${resp?.text}"
            uri = "$baseUrl/$p/$v/$p-${v}${ext}.sha1"

            log.debug "Uploading SHA1 checksum $uri"
            resp = rest.put(uri) {
                auth deployUsername, deployPassword
                body DigestUtils.shaHex(pendingRelease[type])
            }

            log.debug "Received artifactory response: ${resp?.text}"
    }
}
