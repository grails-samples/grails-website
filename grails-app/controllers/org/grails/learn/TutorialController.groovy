package org.grails.learn

import org.grails.common.ApprovalStatus

class TutorialController {
    def searchableService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def tutorialInstanceList = Tutorial.allQuery.list()
        [tutorialInstanceList: tutorialInstanceList]
    }

    def show = {
        def tutorialInstance = Tutorial.get(params.id)
        if (!tutorialInstance) {
            redirect(action: "list")
        }
        else {
            [tutorialInstance: tutorialInstance]
        }
    }

    def create = {
        def tutorialInstance = new Tutorial()
        tutorialInstance.properties = params
        return [tutorialInstance: tutorialInstance]
    }

    def save = {
        def tutorialInstance = new Tutorial(params)
        println(params)
        println(tutorialInstance)
        tutorialInstance.status = ApprovalStatus.PENDING
        tutorialInstance.submittedBy = request.user

        try {
            searchableService.stopMirroring()
            if (!tutorialInstance.hasErrors() && tutorialInstance.save()) {
                processTags tutorialInstance, params.tags
                tutorialInstance.save flush: true
                flash.message = "Your submission was successful. We will let you know when it is approved."
                redirect(action: "list")
            }
            else {
                render(view: "create", model: [tutorialInstance: tutorialInstance])
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
        def tags = tagString.split(/[,;]/)
        domainInstance.tags = tags
    }
}
