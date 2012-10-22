package org.grails.community

class TestimonialController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def testimonialInstanceList =  Testimonial.approved().list(max: params.max)
        def testimonialInstanceTotal = Testimonial.approved().count()

        [testimonialInstanceList: testimonialInstanceList, testimonialInstanceTotal: testimonialInstanceTotal]
    }

    def create = {
        def testimonialInstance = new Testimonial()
        testimonialInstance.properties = params
        return [testimonialInstance: testimonialInstance]
    }

    def save = {
        def testimonialInstance = new Testimonial()
        bindData(testimonialInstance, params, [include: ['title', 'companyName', 'body']])
        testimonialInstance.submittedBy = request.user

        if (testimonialInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'testimonial.label', default: 'Testimonial'), testimonialInstance.id])}"
            redirect(action: "show", id: testimonialInstance.id)
        }
        else {
            render(view: "create", model: [testimonialInstance: testimonialInstance])
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

            bindData(testimonialInstance, params, [include: ['title', 'companyName', 'body']])

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

}
