package org.grails.websites

class WebSiteAdminControllerOld {

    static final int IMAGE_PREVIEW_MAX_SIZE = 2 * 1024 * 1024

    def imageUploadService
    def searchableService

    static scaffold = WebSiteOld

    def list() {
        [ webSites: WebSiteOld.list() ]
    }

    def show() {
        def webSite = WebSiteOld.findById(params.id)
        [ webSite: webSite ]
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
//                processTags webSite, params.tags
                webSite.save flush: true
                redirect action: "list", params: [category: "newest"]
                return
            }
        }
        finally {
//            searchableService.startMirroring()
        }

        render view: "create", model: [ webSite: webSite ]
    }

    def edit() {
        [ webSite: WebSiteOld.findById(params.id) ]
    }

    def update() {
        def webSite = WebSiteOld.get(params.id)
        bindParams webSite

        if (!webSite.hasErrors() && webSite.save()) {
            if (hasPreviewImage()) imageUploadService.save(webSite)
            webSite.save(flush: true)

            redirect action: "show", id: webSite.id
            return
        }

        render view:"edit", model:[webSite:webSite]
    }

    protected bindParams(webSite) {
        // Update the tutorial's properties, but exclude 'featured'
        // because only an administrator can set that.
        bindData webSite, params, [include: ["title", "description", "url", "preview"]]
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
