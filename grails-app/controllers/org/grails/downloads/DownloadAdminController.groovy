package org.grails.downloads

class DownloadAdminController {

    def downloadService

    def scaffold = Download

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = params.sort ?: 'latestRelease'
        params.order = params.order ?: 'desc'
        def downloadInstanceList = Download.list(params)
        [downloadInstanceList: Download.list(params), downloadInstanceTotal: Download.count()]
    }

    def markAsLatestRelease() {
        def downloadInstance = Download.findById(params.id)
        downloadService.markAsLatest(downloadInstance)
        flash.message = "Marked ${downloadInstance.softwareVersion} as the latest release"
        redirect(action: "list")
    }

}
