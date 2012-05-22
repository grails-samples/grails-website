package org.grails.community

import org.grails.common.ApprovalStatus
import org.grails.content.GenericApprovalResponse

class WebSiteAdminController {

    static final int IMAGE_PREVIEW_MAX_SIZE = 2 * 1024 * 1024

    def imageUploadService
    def searchableService
    def genericApprovalResponseService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [webSiteInstanceList: WebSite.list(params), webSiteInstanceTotal: WebSite.count()]
    }

    def create = {
        def webSiteInstance = new WebSite()
        webSiteInstance.properties = params
        if (!webSiteInstance?.submittedBy) {
            webSiteInstance.submittedBy = request.user
        }
        return [webSiteInstance: webSiteInstance]
    }

    def save = {
        def webSiteInstance = new WebSite(params)
        if (!webSiteInstance?.submittedBy) {
            webSiteInstance.submittedBy = request.user
        }

        println "AUTO APPROVED? ${params.autoApprove}"
        if (params.autoApprove) {
            webSiteInstance.status = ApprovalStatus.APPROVED
        }

        try {
            searchableService.stopMirroring()
            if (!webSiteInstance.hasErrors() && validateAndSave(webSiteInstance)) {
                processTags webSiteInstance, params.tags
                webSiteInstance.save flush: true
                flash.message = "${message(code: 'default.created.message', args: ['Web Site', webSiteInstance.id])}"
                redirect(action: "show", id: webSiteInstance.id)
                return
            }
        }
        finally {
            searchableService.startMirroring()
        }

        render(view: "create", model: [webSiteInstance: webSiteInstance])
    }

    def show = {
        def webSiteInstance = WebSite.get(params.id)
        if (!webSiteInstance) {
            response.sendError 404
        }
        else {
            [webSiteInstance: webSiteInstance]
        }
    }

    def edit = {
        def webSiteInstance = WebSite.get(params.id)
        if (!webSiteInstance) {
            response.sendError 404
        }
        else {
            return [webSiteInstance: webSiteInstance]
        }
    }

    def update = {
        def webSiteInstance = WebSite.get(params.id)
        if (webSiteInstance) {
            webSiteInstance.properties = params

            try {
                searchableService.stopMirroring()
                if (!webSiteInstance.hasErrors() && validateAndSave(webSiteInstance)) {
                    processTags webSiteInstance, params.tags
                    webSiteInstance.save flush: true
                    flash.message = "Web Site was created successfully"
                    redirect(action: "show", id: webSiteInstance.id)
                    return
                }
            }
            finally {
                searchableService.startMirroring()
            }
            render(view: "edit", model: [webSiteInstance: webSiteInstance])
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'webSite.label', default: 'WebSite'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def webSiteInstance = WebSite.get(params.id)
        if (webSiteInstance) {
            try {
                webSiteInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'webSite.label', default: 'WebSite'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'webSite.label', default: 'WebSite'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            response.sendError 404
        }
    }

    def disposition = {
        def webSiteInstance = WebSite.get(params.id)
        if (params.featured) {
            webSiteInstance.featured = true
            webSiteInstance.save(flush: true)
        }

        def genericApprovalResponse = new GenericApprovalResponse(
                submittedBy: webSiteInstance.submittedBy,
                moderatedBy: request.user,
                whatType: webSiteInstance.class.name,
                whatId: webSiteInstance.id,
                responseText: params.responseText,
                status: ApprovalStatus.valueOf(params.status)
        )

        if (!genericApprovalResponse.hasErrors() && genericApprovalResponse.save(flush: true)) {
            if (genericApprovalResponseService.setDispositionOfPendingApproval(genericApprovalResponse)) {
                flash.message = "Response was submitted to ${genericApprovalResponse.submittedBy?.login} (${genericApprovalResponse.submittedBy?.email})"
            }
            else {
                flash.message = "Unable to process the request including sending the email."
            }
        }
        else {
            println genericApprovalResponse.errors?.inspect()
            flash.message = "Unable to save response."
        }
        redirect action: 'show', id: webSiteInstance.id
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

    protected processTags(domainInstance, tagString) {
        def tags = tagString.split(/[,;]/)
        domainInstance.tags = tags
    }

}
