package org.grails.learn.tutorials

import org.grails.common.ApprovalStatus
import org.grails.learn.tutorials.Tutorial

class TutorialController {
    def searchableService
    def cacheService
    def contentSearchService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def tagged(String tag) {
        def maxResults = params.int("max",10)
        def offset = params.int("offset", 0)
        render view:"list", model:[tutorialInstanceList: Tutorial.findAllByTag(tag, [max: maxResults, offset: offset]), tutorialCount: Tutorial.countByTag(tag)]
    }
    def search(String q, int offset) {
        render view:"list", model:[tutorialInstanceList: contentSearchService.search(Tutorial, q, offset)]
    }

    def legacyHome() {
        redirect action: "list", params: params, permanent: true
    }

    def list() {
        def maxResults = params.int("max",10)
        def offset = params.int("offset", 0)
        def tutorialInstanceList = Tutorial.allQuery.list(max: maxResults, offset: offset)
        [tutorialInstanceList: tutorialInstanceList, tutorialCount: Tutorial.allQuery.count() ]
    }

    def show() {
        def tutorialInstance = Tutorial.approved.get(params.id)
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
        bindData(tutorialInstance, params)
        return [tutorialInstance: tutorialInstance]
    }

    def save() {
        def tutorial= params.id ? Tutorial.get(params.id) : new Tutorial()
        if(tutorial == null) tutorial = new Tutorial()
        bindData(tutorial, params, [include: ['title', 'description', 'url']])
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
                searchableService.index(tutorial)
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
