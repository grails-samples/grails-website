package org.grails.downloads

class DownloadAdminController {

    static scaffold = Download

    def downloadService

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = params.sort ?: 'latestRelease'
        params.order = params.order ?: 'desc'
        [downloadInstanceList: Download.list(params), downloadInstanceTotal: Download.count()]
    }

    def show() {
        def downloadInstance = Download.findById(params.id)
        [downloadInstance: downloadInstance]
    }

    def create() {
        def downloadInstance = new Download( releaseDate: new org.joda.time.DateTime() )
        [downloadInstance: downloadInstance]
    }

    def edit() {
        def downloadInstance = Download.findById(params.id)
        [downloadInstance: downloadInstance]
    }

    def markAsLatestRelease() {
        def downloadInstance = Download.findById(params.id)
        downloadService.markAsLatest(downloadInstance)
        flash.message = "Marked ${downloadInstance.softwareVersion} as the latest release"
        redirect(action: "list")
    }

}
