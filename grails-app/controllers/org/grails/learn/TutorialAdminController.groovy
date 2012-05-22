package org.grails.learn

import org.grails.common.ApprovalStatus
import org.grails.content.GenericApprovalResponse

class TutorialAdminController {
    def searchableService
    def genericApprovalResponseService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tutorialInstanceList: Tutorial.list(params), tutorialInstanceTotal: Tutorial.count()]
    }

    def create = {
        def tutorialInstance = new Tutorial()
        tutorialInstance.properties = params
        if (!tutorialInstance?.submittedBy) {
            tutorialInstance.submittedBy = request.user
        }
        return [tutorialInstance: tutorialInstance]
    }

    def save = {
        def tutorialInstance = new Tutorial(params)
        if (!tutorialInstance?.submittedBy) {
            tutorialInstance.submittedBy = request.user
        }

        // Mark the submission as approved if 'autoApprove' is checked
        if (params.autoApprove) {
            tutorialInstance.status = ApprovalStatus.APPROVED
        }

        try {
            searchableService.stopMirroring()
            if (!tutorialInstance.hasErrors() && tutorialInstance.save()) {
                processTags tutorialInstance, params.tags
                tutorialInstance.save flush: true
                flash.message = "${message(code: 'default.created.message', args: ['Tutorial', tutorialInstance.id])}"
                redirect(action: "show", id: tutorialInstance.id)
                return
            }
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
        finally {
            searchableService.startMirroring()
        }

        render(view: "create", model: [tutorialInstance: tutorialInstance])
    }

    def show = {
        def tutorialInstance = Tutorial.get(params.id)
        if (!tutorialInstance) {
            response.sendError 404
        }
        else {
            [tutorialInstance: tutorialInstance]
        }
    }

    def edit = {
        def tutorialInstance = Tutorial.get(params.id)
        if (!tutorialInstance) {
            response.sendError 404
        }
        else {
            return [tutorialInstance: tutorialInstance]
        }
    }

    def update = {
        def tutorialInstance = Tutorial.get(params.id)
        if (tutorialInstance) {
            tutorialInstance.properties = params

            try {
                searchableService.stopMirroring()
                if (!tutorialInstance.hasErrors() && tutorialInstance.save()) {
                    processTags tutorialInstance, params.tags
                    tutorialInstance.save flush: true
                    flash.message = "${message(code: 'default.updated.message', args: ['Tutorial', tutorialInstance.id])}"
                    redirect(action: "show", id: tutorialInstance.id)
                    return
                }
            }
            finally {
                searchableService.startMirroring()
            }
            render(view: "edit", model: [tutorialInstance: tutorialInstance])
        }
        else {
            response.sendError 404
        }
    }

    def delete = {
        def tutorialInstance = Tutorial.get(params.id)
        if (tutorialInstance) {
            try {
                tutorialInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: ['Tutorial', params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: ['Tutorial', params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            response.sendError 404
        }
    }

    def disposition = {
        def tutorialInstance = Tutorial.get(params.id)

        def genericApprovalResponse = new GenericApprovalResponse(
                submittedBy: tutorialInstance.submittedBy,
                moderatedBy: request.user,
                whatType: tutorialInstance.class.name,
                whatId: tutorialInstance.id,
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
        redirect action: 'show', id: tutorialInstance.id
    }

    protected processTags(domainInstance, tagString) {
        def tags = tagString.split(/[,;]/)
        domainInstance.tags = tags
    }

}
