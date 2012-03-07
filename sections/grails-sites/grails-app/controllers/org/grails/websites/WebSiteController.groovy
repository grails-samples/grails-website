package org.grails.websites

import grails.util.GrailsNameUtils
import org.apache.shiro.SecurityUtils
import org.grails.sections.AbstractSectionController

class WebSiteController extends AbstractSectionController {
    static final int IMAGE_PREVIEW_MAX_SIZE = 2 * 1024 * 1024

    def imageUploadService
    def searchableService

    WebSiteController() {
        super(WebSite)
    }

    def create() {
        [ webSite: new WebSite(params) ]
    }

    def save() {
        def webSite = new WebSite()
        bindParams webSite

        try {
            searchableService.stopMirroring()
            if (!webSite.hasErrors() && validateAndSave(webSite)) {
                processTags webSite, params.tags
                webSite.save flush: true
                redirect action: "list"
                return
            }
        }
        finally {
            searchableService.startMirroring()
        }

        render view: "create", model: [ webSite: webSite ]
    }

    def edit() {
        def webSite = WebSite.get(params.id)

        if (!webSite) {
            response.sendError 404
        }
        else { 
            [ webSite : webSite ] 
        }
    }

    def update() {
        def webSite = WebSite.get(params.id)

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
