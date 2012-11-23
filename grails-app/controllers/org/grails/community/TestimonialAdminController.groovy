package org.grails.community

import org.grails.content.GenericApprovalResponse
import org.grails.common.ApprovalStatus

class TestimonialAdminController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def genericApprovalResponseService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [testimonialInstanceList: Testimonial.list(params), testimonialInstanceTotal: Testimonial.count()]
    }

    def create = {
        def testimonialInstance = new Testimonial()
        testimonialInstance.properties = params
        return [testimonialInstance: testimonialInstance]
    }

    def save = {
        def testimonialInstance = new Testimonial(params)
        if (testimonialInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), testimonialInstance.id])}"
            redirect(action: "show", id: testimonialInstance.id)
        }
        else {
            render(view: "create", model: [testimonialInstance: testimonialInstance])
        }
    }

    def show = {
        def testimonialInstance = Testimonial.get(params.id)
        if (!testimonialInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), params.id])}"
            redirect(action: "list")
        }
        else {
            [testimonialInstance: testimonialInstance]
        }
    }

    def edit = {
        def testimonialInstance = Testimonial.get(params.id)
        if (!testimonialInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [testimonialInstance: testimonialInstance]
        }
    }

    def disposition = {
        def testimonialInstance = Testimonial.get(params.id)
        if (params.featured) {
            testimonialInstance.featured = true
            testimonialInstance.save(flush: true)
        }

        def genericApprovalResponse = new GenericApprovalResponse(
                submittedBy: testimonialInstance.submittedBy,
                moderatedBy: request.user,
                whatType: testimonialInstance.class.name,
                whatId: testimonialInstance.id,
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
        redirect action: 'show', id: testimonialInstance.id
    }

    def update = {
        def testimonialInstance = Testimonial.get(params.id)
        if (testimonialInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (testimonialInstance.version > version) {
                    
                    testimonialInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'testimonial.label', default: 'Testimonial')] as Object[], "Another user has updated this Testimonial while you were editing")
                    render(view: "edit", model: [testimonialInstance: testimonialInstance])
                    return
                }
            }
            testimonialInstance.properties = params
            if (!testimonialInstance.hasErrors() && testimonialInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), testimonialInstance.id])}"
                redirect(action: "show", id: testimonialInstance.id)
            }
            else {
                render(view: "edit", model: [testimonialInstance: testimonialInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def testimonialInstance = Testimonial.get(params.id)
        if (testimonialInstance) {
            try {
                testimonialInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), params.id])}"
            redirect(action: "list")
        }
    }
}
