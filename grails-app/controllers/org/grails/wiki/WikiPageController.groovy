package org.grails.wiki

import org.grails.content.Version

class WikiPageController {

    def scaffold = WikiPage
    def cacheService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect action: "list", params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [wikiPageInstanceList: WikiPage.list(params), wikiPageInstanceTotal: WikiPage.count()]
    }

    def create() {
        def wikiPageInstance = new WikiPage()
        bindData(wikiPageInstance, params)
        [wikiPageInstance: wikiPageInstance]
    }

    def save() {
        def wikiPageInstance = new WikiPage(params)
        if (wikiPageInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), wikiPageInstance.id])}"
            redirect action: "show", id: wikiPageInstance.id
        }
        else {
            render view: "create", model: [wikiPageInstance: wikiPageInstance]
        }
    }

    def show() {
        def wikiPageInstance = WikiPage.get(params.id)
        if (!wikiPageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "list"
        }
        else {
            [wikiPageInstance: wikiPageInstance]
        }
    }

    def edit() {
        def wikiPageInstance = WikiPage.get(params.id)
        if (!wikiPageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "list"
        }
        else {
            return [wikiPageInstance: wikiPageInstance]
        }
    }

    def rollback(String id) {
        def page = WikiPage.findByTitle(id)
        if (page) {
            def latestVersion = page.latestVersion
            def number = latestVersion.number-1

            def previousVersion

            while(number > -1)  {
                previousVersion = Version.findByCurrentAndNumber(page, latestVersion.number-1)   
                if(previousVersion) break;
                number = number - 1
            }
             
            if(previousVersion) {
                    page.lock()
                    page.version = page.version + 1
                    page.body = previousVersion.body
                    page.save(flush: true, failOnError: true)
                    Version v = page.createVersion()
                    v.author = request.user
                    v.save(failOnError: true)
                    evictFromCache page.id, page.title    
                    flash.message = "Rolled back to previous version"
                    redirect action:"show", id:page.id
            }
            else {
                flash.message = "No previous version to rollback to"
                redirect action:"show", id:page.id
            }
        }
        else {
            render status:404
        }
    }

    private evictFromCache(id, title) {
        cacheService.removeWikiText(title)
        cacheService.removeContent(title)
        if (id) cacheService.removeCachedText('versionList' + id)
    }    

    def update() {
        def wikiPageInstance = WikiPage.get(params.id)
        if (wikiPageInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (wikiPageInstance.version > version) {
                    
                    wikiPageInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'wikiPage.label', default: 'WikiPage')] as Object[], "Another user has updated this WikiPage while you were editing")
                    render(view: "edit", model: [wikiPageInstance: wikiPageInstance])
                    return
                }
            }
            bindData(wikiPageInstance, params)
            if (!wikiPageInstance.hasErrors() && wikiPageInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), wikiPageInstance.id])}"
                redirect action: "show", id: wikiPageInstance.id
            }
            else {
                render view: "edit", model: [wikiPageInstance: wikiPageInstance]
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "list"
        }
    }

    def delete() {
        def wikiPageInstance = WikiPage.get(params.id)
        if (wikiPageInstance) {
            try {
                // Delete all the related versions as well as the source wiki page.
                def versions = Version.findAllByCurrent(wikiPageInstance)
                versions*.delete()
                wikiPageInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
                redirect action: "list"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
                redirect action: "show", id: params.id
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "list"
        }
    }

    def all() {
        params.sort = "title"
        params.order = "asc"

        return [wikiPages: WikiPage.list(params).groupBy { it.title ? it.title[0] : '' },
                totalWikiPages: WikiPage.count()]
    }
}
