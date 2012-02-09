package org.grails.tutorials

import grails.util.GrailsNameUtils
import org.apache.shiro.SecurityUtils
import org.grails.sections.AbstractSectionController

class TutorialController extends AbstractSectionController {
    static final int IMAGE_PREVIEW_MAX_SIZE = 2 * 1024 * 1024

    def searchableService

    TutorialController() {
        super(Tutorial)
    }

    def index() {}

    def create() {
        [ tutorial: new Tutorial(params) ]
    }

    def save() {
        def tutorial = new Tutorial()
        bindParams tutorial

        try {
            searchableService.stopMirroring()
            if (!tutorial.hasErrors() && tutorial.save()) {
                processTags tutorial, params.tags
                tutorial.save flush: true
                redirect action: "list"
                return
            }
        }
        finally {
            searchableService.startMirroring()
        }

        render view: "create", model: [ tutorial: tutorial ]
    }

    def edit() {
        def tutorial = Tutorial.get(params.id)

        if (!tutorial) {
            response.sendError 404
        }
        else { 
            [ tutorial : tutorial ] 
        }
    }

    def update() {
        def tutorial = Tutorial.get(params.id)

        if (!tutorial) {
            response.sendError 404
        }
        else { 
            bindParams tutorial

            try {
                searchableService.stopMirroring()
                if (!tutorial.hasErrors() && tutorial.save()) {
                    processTags tutorial, params.tags
                    tutorial.save flush: true
                    redirect action:"list"
                    return
                }
            }
            finally {
                searchableService.startMirroring()
            }

            render view:"edit", model:[tutorial:tutorial]
        }
    }

    protected bindParams(tutorial) {
        // Update the tutorial's properties, but exclude 'featured'
        // because only an administrator can set that.
        bindData tutorial, params['tutorial'], ["featured"]

        // Update 'featured' property if the current user has permission to.
        if (SecurityUtils.subject.isPermitted("tutorial:feature")) {
            tutorial.featured = params.featured ?: false
        }
    }
}
