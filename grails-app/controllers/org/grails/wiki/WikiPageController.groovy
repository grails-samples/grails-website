package org.grails.wiki

import org.grails.content.Version

class WikiPageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [wikiPageInstanceList: WikiPage.list(params), wikiPageInstanceTotal: WikiPage.count()]
    }

    def create = {
        def wikiPageInstance = new WikiPage()
        wikiPageInstance.properties = params
        return [wikiPageInstance: wikiPageInstance]
    }

    def save = {
        def wikiPageInstance = new WikiPage(params)
        if (wikiPageInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), wikiPageInstance.id])}"
            redirect action: "show", id: wikiPageInstance.id
        }
        else {
            render view: "create", model: [wikiPageInstance: wikiPageInstance]
        }
    }

    def show = {
        def wikiPageInstance = WikiPage.get(params.id)
        if (!wikiPageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "list"
        }
        else {
            [wikiPageInstance: wikiPageInstance]
        }
    }

    def edit = {
        def wikiPageInstance = WikiPage.get(params.id)
        if (!wikiPageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "list"
        }
        else {
            return [wikiPageInstance: wikiPageInstance]
        }
    }

    def update = {
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
            wikiPageInstance.properties = params
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

    def delete = {
        def wikiPageInstance = WikiPage.get(params.id)
        if (wikiPageInstance) {
            try {
                // Delete all the related versions as well as the source wiki page.
                def versions = Version.findAllByCurrent(wikiPageInstance)
                versions*.delete()
                wikiPageInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
                redirect action: "all"
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
                redirect action: "show", id: params.id
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'wikiPage.label', default: 'WikiPage'), params.id])}"
            redirect action: "all"
        }
    }

    def all = {
        params.sort = "title"
        params.order = "asc"

        return [wikiPages: WikiPage.list(params).groupBy { it.title ? it.title[0] : '' },
                totalWikiPage: WikiPage.count()]
    }
}
