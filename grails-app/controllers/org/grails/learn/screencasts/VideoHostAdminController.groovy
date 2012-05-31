package org.grails.learn.screencasts

class VideoHostAdminController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        [videoHostInstanceList: VideoHost.list()]
    }

    def create() {
        def videoHostInstance = new VideoHost()
        videoHostInstance.properties = params
        [videoHostInstance: videoHostInstance]
    }

    def save() {
        def videoHostInstance = new VideoHost(params)
        if (!videoHostInstance.hasErrors() && videoHostInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: ['Video Host', videoHostInstance.id])}"
            redirect action: "show", id: videoHostInstance.id
        }
        else {
            flash.message = "Please fix the errors below"
            flash.next()
            render view: "create", model: [videoHostInstance: videoHostInstance]
        }
    }

    def show() {
        def videoHostInstance = VideoHost.get(params.id)
        if (!videoHostInstance) {
            response.sendError 404
        }
        else {
            [videoHostInstance: videoHostInstance]
        }
    }

    def edit() {
        def videoHostInstance = VideoHost.get(params.id)
        if (!videoHostInstance) {
            response.sendError 404
        }
        else {
            [videoHostInstance: videoHostInstance]
        }
    }

    def update() {
        def videoHostInstance = VideoHost.get(params.id)
        if (videoHostInstance) {
            videoHostInstance.properties = params
            if (!videoHostInstance.hasErrors() && videoHostInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.created.message', args: ['Video Host', videoHostInstance.id])}"
                redirect(action: "show", id: videoHostInstance.id)
            }
            else {
                flash.message = "Please fix the errors below"
                flash.next()
                render view: "edit", model: [videoHostInstance: videoHostInstance]
            }
        }
        else {
            response.sendError 404
        }
    }

    def delete = {
        def videoHostInstance = VideoHost.get(params.id)
        if (videoHostInstance) {
            try {
                videoHostInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: ['Video Host', params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: ['Video Host', params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            response.sendError 404
        }
    }

}
