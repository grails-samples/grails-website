package org.grails.maven

import org.apache.commons.codec.digest.*
import org.grails.plugin.*
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.ResourceAccessException
import grails.plugins.rest.client.*
/**
 * Responsible for taking a PendingRelease and publishing it to Artifactory
 *
 * @author Graeme Rocher
 */
class PluginDeployService implements ApplicationListener<PluginPublishEvent>{

    int retryCount = 3
    String releaseUrl = "https://repo.grails.org/grails/plugins-releases-local/org/grails/plugins"
    String snapshotUrl = "https://repo.grails.org/grails/plugins-snapshots-local/org/grails/plugins"
    String deployUsername = System.getProperty("artifactory.user")
    String deployPassword = System.getProperty("artifactory.pass")
    RestBuilder rest = new RestBuilder(connectTimeout:1000, readTimeout:10000)

    @Transactional
    void onApplicationEvent(PluginPublishEvent event) {
        PendingRelease pendingRelease = event.source
        log.debug "Received plugin publish event for pending release [$event.source]"
        deployRelease pendingRelease
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

        try {
            deployArtifact(rest, url, pendingRelease, "zip")
            deployArtifact(rest, url, pendingRelease, "pom")
            deployArtifact(rest, url, pendingRelease, "xml")

            completeRelease pendingRelease
        }
        catch (Exception ex) {
            log.error "Failed to deploy plugin to artifact repository: ${ex.message}", ex
            failRelease pendingRelease
        }
    }

    protected deployArtifact(rest,baseUrl, pendingRelease, type) {

        def p = pendingRelease.pluginName
        def v = pendingRelease.pluginVersion
        def ext = type == 'xml' ? '-plugin.xml' : ".$type"
        def content = pendingRelease[type]

        putFileWithRetry "$baseUrl/$p/$v/$p-${v}$ext", content
        putFileWithRetry "$baseUrl/$p/$v/$p-${v}${ext}.md5", DigestUtils.md5Hex(content)
        putFileWithRetry "$baseUrl/$p/$v/$p-${v}${ext}.sha1", DigestUtils.shaHex(content)
    }

    /**
     * Performs a PUT of the given content to the specified URI, retrying the PUT if
     * it fails due to network timeouts or similar. If the PUT succeeds, this method
     * returns {@code true}, otherwise {@code false}.
     */
    protected putFileWithRetry(uri, content) {
        for (int i in 0..<retryCount) {
            try {
                putFile(uri, content)
                return
            }
            catch (ResourceAccessException ex) {
                log.debug "PUT failed on try ${i + 1}"
            }
        }

        log.warn "All PUT attempts failed for ${uri}"
        throw new RuntimeException("All PUT attempts failed for ${uri}")
    }

    /**
     * Performs a PUT of the given content to the specified URI. If the PUT succeeds,
     * this method returns {@code true}, otherwise {@code false}. Success is defined
     * as an HTTP response less than 400.
     */
    protected putFile(uri, content) {
        log.debug "Uploading file to $uri"

        def resp = rest.put(uri) {
            auth deployUsername, deployPassword
            body content
        }

        log.debug "Received artifactory response: ${resp?.text}"
        if (resp.status >= 400) {
            throw new RuntimeException("Artifact repository returned ${resp.status} status code")
        }
    }

    protected completeRelease(pendingRelease) {
        saveRelease pendingRelease, ReleaseStatus.COMPLETED
    }

    protected failRelease(pendingRelease) {
        saveRelease pendingRelease, ReleaseStatus.FAILED
    }

    protected saveRelease(pendingRelease, status) {
        if (pendingRelease.id) {
            pendingRelease = PendingRelease.get(pendingRelease.id)
            pendingRelease.status = status
        }
        else {
            pendingRelease = new PendingRelease(
                    pluginName: pendingRelease.pluginName,
                    pluginVersion: pendingRelease.pluginVersion,
                    status: status)
        }

        pendingRelease.save(failOnError: true)
    }
}
