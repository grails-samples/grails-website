package org.grails.websites

import org.apache.shiro.SecurityUtils
import org.grails.sections.AbstractSectionController

class WebSiteControllerOld extends AbstractSectionController {
    static final int IMAGE_PREVIEW_MAX_SIZE = 2 * 1024 * 1024

    def imageUploadService
    def searchableService

    WebSiteControllerOld() {
        super(WebSiteOld)
    }

    def create() {
        [ webSite: new WebSiteOld(params) ]
    }

    def save() {
        def webSite = new WebSiteOld()
        bindParams webSite

        try {
            searchableService.stopMirroring()
            if (!webSite.hasErrors() && validateAndSave(webSite)) {
                processTags webSite, params.tags
                webSite.save flush: true
                redirect action: "list", params: [category: "newest"]
                return
            }
        }
        finally {
            searchableService.startMirroring()
        }

        render view: "create", model: [ webSite: webSite ]
    }

    def edit() {
        def webSite = WebSiteOld.get(params.id)

        if (!webSite) {
            response.sendError 404
        }
        else { 
            [ webSite : webSite ] 
        }
    }

    def update() {
        def webSite = WebSiteOld.get(params.id)

        if (!webSite) {
            response.sendError 404
        }
        else { 
            bindParams webSite

            try {
                searchableService.stopMirroring()
                if (!webSite.hasErrors() && webSite.save()) {
                    if (hasPreviewImage()) imageUploadService.save(webSite)
                    processTags webSite, params.tags
                    webSite.save(flush: true)
    
                    redirect action:"list"
                    return
                }
            }
            finally {
                searchableService.startMirroring()
            }

            render view:"edit", model:[webSite:webSite]
        }
    }

    protected bindParams(webSite) {
        // Update the tutorial's properties, but exclude 'featured'
        // because only an administrator can set that.
        bindData webSite, params, [include: ["title", "description", "url", "preview"]]

        // Update 'featured' property if the current user has permission to.
        if (SecurityUtils.subject.isPermitted("webSite:feature")) {
            webSite.featured = params.featured ?: false
        }
    }

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
