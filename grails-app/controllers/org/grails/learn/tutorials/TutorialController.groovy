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
        def tutorial= params.id ? Tutorial.get(params.id) : new Tutorial()
        if(tutorial == null) tutorial = new Tutorial()
        tutorial.properties['title', 'description', 'url'] = params
        boolean isNew = !tutorial.isAttached()
        if(isNew) {
            tutorial.status = ApprovalStatus.PENDING
            tutorial.submittedBy = request.user
        }
       try {
            searchableService.stopMirroring()
            if (!tutorial.hasErrors() && tutorial.save()) {
                processTags tutorial, params.tags
                
                tutorial.save flush: true

                // Make sure user can edit tutorial they create
                if(isNew) {
                    request.user.addToPermissions("tutorial:edit,update:$tutorial.id")
                    request.user.save()
                }
                
                def key = "tutorial_${tutorial.id}".toString()
                cacheService?.removeWikiText(key)
                cacheService?.removeShortenedWikiText(key)
                flash.message = isNew ? "Your submission was successful. We will let you know when it is approved." : "Tutorial Updated."
                redirect(action: "list")
            }
            else {
                render(view: "create", model: [tutorialInstance: tutorial])
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
