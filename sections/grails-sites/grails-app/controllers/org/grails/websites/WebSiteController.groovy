package org.grails.websites

import grails.util.GrailsNameUtils
import org.grails.sections.AbstractSectionController

class WebSiteController extends AbstractSectionController {
    static final int IMAGE_PREVIEW_MAX_SIZE = 2 * 1024 * 1024

    def imageUploadService
    def grailsApplication
    def taggableService

    WebSiteController() {
        super(WebSite)
    }

    def create() {
        [ website: new WebSite(params) ]
    }

    def save() {
        def website = new WebSite()
        bindData website, params['website']

        if (!website.hasErrors() && website.save()) {
            imageUploadService.save(website)
            website.parseTags(params.tags, /[,;]/)
            redirect action: "list"
            return
        }

        render view: "create", model: [ website: website ]
    }

    def edit() {
        def website = WebSite.get(params.id)

        if (!website) {
            response.sendError 404
        }
        else { 
            [ website : website ] 
        }
    }

    def update() {
        def website = WebSite.get(params.id)

        if (!website) {
            response.sendError 404
        }
        else { 
            bindData website, params['website']

            if (!website.hasErrors() && website.save()) {
                imageUploadService.save(website)
                website.parseTags(params.tags, /[,;]/)
                website.save(flush: true)

                redirect action:"list"
                return
            }

            render view:"edit", model:[website:website]
        }
    }
}
