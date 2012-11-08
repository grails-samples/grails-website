package org.grails.learn.tutorials

import org.grails.common.ApprovalStatus
import org.grails.learn.tutorials.Tutorial

class TutorialController {
    def searchableService
    def cacheService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def legacyHome() {
        redirect action: "list", params: params, permanent: true
    }

    def list() {
        def tutorialInstanceList = Tutorial.allQuery.list()
        [tutorialInstanceList: tutorialInstanceList]
    }

    def show() {
        def tutorialInstance = Tutorial.get(params.id)
        if (!tutorialInstance) {
            redirect(action: "list")
        }
        else {
            [tutorialInstance: tutorialInstance]
        }
    }

    def edit(Long id) {
        def t = Tutorial.get(id)
        if(t) {
            render view:"create", model:[tutorialInstance: t]
        }
        else {
            render status:404
        }
    }

    def create() {
        def tutorialInstance = new Tutorial()
        tutorialInstance.properties = params
        return [tutorialInstance: tutorialInstance]
    }

    def save() {
        def tutorialInstance = params.id ? Tutorial.get(params.id) : new Tutorial()
        tutorialInstance.properties['title', 'description', 'url'] = params
        tutorialInstance.status = ApprovalStatus.PENDING
        tutorialInstance.submittedBy = request.user

        try {
            searchableService.stopMirroring()
            if (!tutorialInstance.hasErrors() && tutorialInstance.save()) {
                processTags tutorialInstance, params.tags
                tutorialInstance.save flush: true
                def key = "tutorial_${tutorialInstance.id}".toString()
                cacheService?.removeWikiText(key)
                cacheService?.removeShortenedWikiText(key)
                flash.message = "Your submission was successful. We will let you know when it is approved."
                redirect(action: "list")
            }
            else {
                render(view: "create", model: [tutorialInstance: tutorialInstance])
            }
        }
        catch (Exception ex) {
            log.error ex.message, ex
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
