package org.grails.learn.screencasts

import org.grails.common.ApprovalStatus

class ScreencastController {
    def searchableService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        def screencastInstanceList = Screencast.allQuery.list()
        [screencastInstanceList: screencastInstanceList]
    }

    def show() {
        def screencastInstance = Screencast.get(params.id)
        if (!screencastInstance) {
            redirect(action: "list")
        }
        else {
            [screencastInstance: screencastInstance]
        }
    }

    def create() {
        def screencastInstance = new Screencast()
        screencastInstance.properties = params
        return [screencastInstance: screencastInstance]
    }

    def save() {
        def screencastInstance = new Screencast(params)
        screencastInstance.status = ApprovalStatus.PENDING
        screencastInstance.submittedBy = request.user

        try {
            searchableService.stopMirroring()
            if (!screencastInstance.hasErrors() && screencastInstance.save()) {
                processTags screencastInstance, params.tags
                screencastInstance.save flush: true
                flash.message = "Your submission was successful. We will let you know when it is approved."
                redirect(action: "list")
            }
            else {
                render(view: "create", model: [screencastInstance: screencastInstance])
            }
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
        finally {
            searchableService.startMirroring()
        }
    }

    protected processTags(domainInstance, tagString) {
        def tags = tagString.split(/[,;]/)*.trim()
        domainInstance.tags = tags
    }
}
