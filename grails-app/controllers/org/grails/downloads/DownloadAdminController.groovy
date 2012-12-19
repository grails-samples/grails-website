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

    def saveRelease(ReleaseInfoCommand cmd) {
        if(cmd.hasErrors()) {
            render view:"create", model:[releaseInfo:cmd]
        }
        else {
            Download.withTransaction { status ->
                def d = new Download(softwareName:"Grails", softwareVersion: cmd.softwareVersion, betaRelease:cmd.betaRelease) 

                def zip = new DownloadFile(title:"Binary Zip", fileType: DownloadFile.FileType.BINARY)
                zip.addToMirrors(new Mirror(file:zip, name:"Amazon", urlString:cmd.binaryZip))
                d.addToFiles(zip)

                def doc = new DownloadFile(title:"Documentation", fileType: DownloadFile.FileType.DOCUMENTATION)
                doc.addToMirrors(new Mirror(file:doc, name:"Amazon", urlString:cmd.documentationZip))
                d.addToFiles(doc)
                                
                                
                d.save(flush:true)
                if(cmd.latestRelease) {
                    downloadService.markAsLatest(d)                
                }
                flash.message = "Release $d created"
                redirect action:'list'
                
            }
            
        }
    }
    def show() {
        def downloadInstance = Download.findById(params.id)
        [downloadInstance: downloadInstance]
    }

    def create() {
        [releaseInfo: new ReleaseInfoCommand()]
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
class ReleaseInfoCommand {
    String binaryZip
    String documentationZip
    String softwareVersion
    Boolean latestRelease
    Boolean betaRelease = false

    static constraints = {
        binaryZip nullable:false, blank:false
        documentationZip nullable:false, blank:false
        softwareVersion nullable:false, blank:false
        latestRelease nullable:false


    }
}
