package org.grails.community

import org.grails.common.ApprovalStatus

class WebSiteController {
    def imageUploadService
    def cacheService
    def contentSearchService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        def maxResults = params.int("max",12)
        def offset = params.int("offset", 0) 
        def featuredWebSites = offset > 0 ? [] : WebSite.featuredQuery.list()
        def webSites = WebSite.notFeaturedQuery.list(offset:offset, max:maxResults)
        [
                featuredWebSiteInstanceList: featuredWebSites,
                webSiteInstanceList: webSites,
                websiteCount: WebSite.notFeaturedQuery.count()
        ]
    }

    def search(String q, int offset) {
        render view:"list", model:[webSiteInstanceList: contentSearchService.search(WebSite, q, offset, [reload: true])]
    }

    def create() {
        def webSiteInstance = new WebSite()
        bindData(webSiteInstance, params)
        return [webSiteInstance: webSiteInstance]
    }

    def save() {
        def website= params.id? WebSite.get(params.id) : new WebSite()
        if(website == null) website = new WebSite()
        bindData(website, params, [exclude: ['status', 'featured', 'popularity', 'submittedBy']])

        boolean isNew = !website.isAttached()
        if(isNew) {
            website.submittedBy = request.user
            website.status = ApprovalStatus.PENDING
        }
        
        if (!website.hasErrors() && validateAndSave(website)) {

            website.save flush: true

            if(isNew) {
                request.user.addToPermissions("website:edit,update:$website.id")
                request.user.save()
            }
  
            def key = "webSite_${website.id}".toString()
            cacheService?.removeWikiText(key)
            cacheService?.removeShortenedWikiText(key)
  
            flash.message = isNew ? "Your submission was successful. We will let you know when it is approved." : "Website Updated"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [webSiteInstance: website])
        }
    }

    def show() {
        def webSiteInstance = WebSite.get(params.id)
        if (!webSiteInstance) {
            render status:404
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
