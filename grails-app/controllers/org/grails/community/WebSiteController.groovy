package org.grails.community

import org.grails.common.ApprovalStatus

class WebSiteController {
    def imageUploadService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def featuredWebSites = WebSite.featuredQuery.list()
        def webSites = WebSite.notFeaturedQuery.list()
        [
                featuredWebSiteInstanceList: featuredWebSites,
                webSiteInstanceList: webSites
        ]
    }

    def create = {
        def webSiteInstance = new WebSite()
        webSiteInstance.properties = params
        return [webSiteInstance: webSiteInstance]
    }

    def save = {
        def webSiteInstance = new WebSite(params)
        webSiteInstance.submittedBy = request.user
        if (!webSiteInstance.hasErrors() && validateAndSave(webSiteInstance)) {
            webSiteInstance.save flush: true
            flash.message = "Your submission was successful. We will let you know when it is approved."
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [webSiteInstance: webSiteInstance])
        }
    }

    def show = {
        def webSiteInstance = WebSite.get(params.id)
        if (!webSiteInstance) {
            redirect(action: "list")
        }
        else {
            [webSiteInstance: webSiteInstance]
        }
    }

    def edit = {
        def webSiteInstance = WebSite.get(params.id)
        if (!webSiteInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webSite.label', default: 'WebSite'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [webSiteInstance: webSiteInstance]
        }
    }

    def update = {
        def webSiteInstance = WebSite.get(params.id)
        if (webSiteInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (webSiteInstance.version > version) {

                    webSiteInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'webSite.label', default: 'WebSite')] as Object[], "Another user has updated this WebSite while you were editing")
                    render(view: "edit", model: [webSiteInstance: webSiteInstance])
                    return
                }
            }
            webSiteInstance.properties = params
            if (!webSiteInstance.hasErrors() && webSiteInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'webSite.label', default: 'WebSite'), webSiteInstance.id])}"
                redirect(action: "show", id: webSiteInstance.id)
            }
            else {
                render(view: "edit", model: [webSiteInstance: webSiteInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webSite.label', default: 'WebSite'), params.id])}"
            redirect(action: "list")
        }
    }

    def submissionReceived = {
        def webSiteInstance = WebSite.get(params.id)
        [webSiteInstance: webSiteInstance]
    }

    // Helper Methods

    protected validateAndSave(webSite) {
        def validates = !webSite.hasErrors()
        validates = webSite.validate() && validates
        validates = validatePreviewImage(webSite) && validates
        if (validates && webSite.save()) {
            imageUploadService.save(webSite)
        }
        return validates
    }

    protected validatePreviewImage(domainInstance) {
        if (!hasPreviewImage()) {
            domainInstance.errors.rejectValue "preview", "webSite.preview.required"
            return false
        }
        else return true
    }

    protected hasPreviewImage() {
        return !request.getFile("preview").isEmpty()
    }
}
