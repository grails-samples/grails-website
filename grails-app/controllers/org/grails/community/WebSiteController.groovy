package org.grails.community

import org.grails.common.ApprovalStatus

class WebSiteController {
    def imageUploadService
    def cacheService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        def featuredWebSites = WebSite.featuredQuery.list()
        def webSites = WebSite.notFeaturedQuery.list()
        [
                featuredWebSiteInstanceList: featuredWebSites,
                webSiteInstanceList: webSites
        ]
    }

    def create() {
        def webSiteInstance = new WebSite()
        webSiteInstance.properties = params
        return [webSiteInstance: webSiteInstance]
    }

    def save() {
        def website= params.id? WebSite.get(params.id) : new WebSite()
        if(website == null) website = new WebSite()
        website.properties = params
        website.submittedBy = request.user
        if (!website.hasErrors() && validateAndSave(website)) {
            website.status = ApprovalStatus.PENDING
            website.save flush: true
            def key = "webSite_${website.id}".toString()
            cacheService?.removeWikiText(key)
            cacheService?.removeShortenedWikiText(key)
  
            flash.message = "Your submission was successful. We will let you know when it is approved."
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [webSiteInstance: website])
        }
    }

    def show() {
        def webSiteInstance = WebSite.get(params.id)
        if (!webSiteInstance) {
            redirect(action: "list")
        }
        else {
            [webSiteInstance: webSiteInstance]
        }
    }

    def edit(Long id) {
        def website = WebSite.get(id)
        if (!website) {
            render status:404
        }
        else {
            render view:'create', model:[webSiteInstance: website]
        }
    }


    def submissionReceived() {
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
