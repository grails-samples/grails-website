package org.grails.downloads

class DownloadFileController {

    def scaffold = DownloadFile

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [downloadFileInstanceList: DownloadFile.list(params), downloadInstanceTotal: DownloadFile.count()]
    }

    def show() {
        def downloadFileInstance = DownloadFile.findById(params.id)
        [downloadFileInstance: downloadFileInstance]
    }

    def create() {
        def downloadFileInstance = new DownloadFile()
        [downloadFileInstance: downloadFileInstance]
    }

    def edit() {
        def downloadFileInstance = DownloadFile.findById(params.id)
        [downloadFileInstance: downloadFileInstance]
    }

}
