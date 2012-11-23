package org.grails.plugin

import grails.converters.*

class PendingReleaseController {

    static scaffold = PendingRelease

    def pluginDeployService

    def deploy(Long id) {
        def pr = PendingRelease.get(id)

        if(pr) {
            pluginDeployService.deployRelease(pr)
            flash.message = "Release deployed"
            redirect action:"list"
        }
        else {
            flash.message = "Release not found"
            redirect action:"list"
        }
    }

    /**
     * Returns the status of the given pending release, or a 404 if it doesn't
     * exist.
     */
    def status(String name, String version) {
        def pr = PendingRelease.findByPluginNameAndPluginVersion(name, version)
        if (!pr) {
            def pluginRelease = PluginRelease.where {
                plugin.name == name && releaseVersion == version
            }.get()
            if (pluginRelease) pr = [status: ReleaseStatus.COMPLETED]
        }

        if (pr) {
            withFormat {
                text {
                    render text: pr.status.toString()
                }
                json {
                    render([status: pr.status.toString()] as JSON)
                }
                xml {
                    render contentType: "application/xml", {
                        status pr.status.toString()
                    }
                }
            }
        }
        else {
            response.sendError 404
        }
    }
}
