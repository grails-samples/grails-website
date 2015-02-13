package org.grails.learn.screencasts

import org.grails.common.ApprovalStatus
import org.grails.content.GenericApprovalResponse

class ScreencastAdminController {
    def searchableService
    def genericApprovalResponseService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [screencastInstanceList: Screencast.list(params), screencastInstanceTotal: Screencast.count()]
    }

    def create = {
        def screencastInstance = new Screencast()
        bindData(screencastInstance, params)
        if (!screencastInstance?.submittedBy) {
            screencastInstance.submittedBy = request.user
        }
        return [screencastInstance: screencastInstance]
    }

    def save = {
        def screencastInstance = new Screencast(params)
        if (!screencastInstance?.submittedBy) {
            screencastInstance.submittedBy = request.user
        }

        // Mark the submission as approved if 'autoApprove' is checked
        if (params.autoApprove) {
            screencastInstance.status = ApprovalStatus.APPROVED
        }

        try {
            searchableService.stopMirroring()
            if (!screencastInstance.hasErrors() && screencastInstance.save()) {
                processTags screencastInstance, params.tags
                screencastInstance.save flush: true
                flash.message = "${message(code: 'default.created.message', args: ['Screencast', screencastInstance.id])}"
                redirect(action: "show", id: screencastInstance.id)
                return
            }
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
        finally {
            searchableService.startMirroring()
        }

        render(view: "create", model: [screencastInstance: screencastInstance])
    }

    def show = {
        def screencastInstance = Screencast.get(params.id)
        if (!screencastInstance) {
            response.sendError 404
        }
        else {
            [screencastInstance: screencastInstance]
        }
    }

    def edit = {
        def screencastInstance = Screencast.get(params.id)
        if (!screencastInstance) {
            response.sendError 404
        }
        else {
            return [screencastInstance: screencastInstance]
        }
    }

    def update = {
        def screencastInstance = Screencast.get(params.id)
        if (screencastInstance) {
            bindData(screencastInstance, params)

            try {
                searchableService.stopMirroring()
                if (!screencastInstance.hasErrors() && screencastInstance.save()) {
                    processTags screencastInstance, params.tags
                    screencastInstance.save flush: true
                    flash.message = "${message(code: 'default.updated.message', args: ['Screencast', screencastInstance.id])}"
                    redirect(action: "show", id: screencastInstance.id)
                    return
                }
            }
            finally {
                searchableService.startMirroring()
            }
            render(view: "edit", model: [screencastInstance: screencastInstance])
        }
        else {
            response.sendError 404
        }
    }

    def disposition = {
        def screencastInstance = Screencast.get(params.id)

        def genericApprovalResponse = new GenericApprovalResponse(
                submittedBy: screencastInstance.submittedBy,
                moderatedBy: request.user,
                whatType: screencastInstance.class.name,
                whatId: screencastInstance.id,
                responseText: params.responseText,
                status: ApprovalStatus.valueOf(params.status)
        )

        if (!genericApprovalResponse.hasErrors() && genericApprovalResponse.save(flush: true)) {
            if (genericApprovalResponseService.linkAndfirePendingApproval(genericApprovalResponse)) {
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
        redirect action: 'show', id: screencastInstance.id
    }

    protected processTags(domainInstance, tagString) {
        def tags = tagString.split(/[,;]/)*.trim()
        domainInstance.tags = tags
    }

}
