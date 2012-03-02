package org.grails.plugin

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
}
