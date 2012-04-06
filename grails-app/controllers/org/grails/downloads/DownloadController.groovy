package org.grails.downloads

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springcache.annotations.*
import grails.validation.Validateable
import net.sf.ehcache.Element
import net.sf.ehcache.Ehcache
import org.hibernate.FetchMode

class DownloadController {
    def grailsApplication
    def downloadCache
    
    def index() { }

    def latest() {
        // Find out which versions we should display. This should be a
        // list like ['2.0', '1.3', '1.2'].
        def versions = versionOrder*.baseVersion ?: ['1.3', '1.2']

        def downloads = versions.inject([:]) { map, version ->
            def m = version =~ /(\d+\.\d+)\s?(beta|milestone)?/
            def verNumber = m[0][1]
            def isBeta = m[0][2] as boolean

            def downloads = Download.withCriteria {
                eq 'softwareName', 'Grails'
                like 'softwareVersion', "${verNumber}%"
                if (isBeta) {
                    eq 'betaRelease', true
                }
                else {
                    or {
                        eq 'betaRelease', false
                        isNull 'betaRelease'
                    }
                }
                fetchMode 'files', FetchMode.SELECT
                order 'releaseDate', 'desc'
                maxResults 1
            }
            def binaryDownload = downloads ? downloads[0] : null

            def docDownload = !binaryDownload ? null : Download.findBySoftwareNameAndSoftwareVersion(
                    'Grails Documentation',
                    binaryDownload.softwareVersion,
                    [fetch: [files: 'select']])

            if (downloads) {
                map[version] = [ binaryDownload, docDownload ]
            }
            else {
                log.error "No downloads found for: ${version}"
            }

            return map
        }

        // Split the downloads into stable and non-stable.
        downloads = downloads.groupBy { key, value -> value[0].betaRelease ? 'beta' : 'stable' }

        render(view: 'index', model: [stableDownloads: downloads['stable'], betaDownloads: downloads['beta']])
    }

    def archive() {
        def downloads = Download.findAllBySoftwareName(params.id, [order:'desc', sort:'releaseDate', cache:true])

        return [downloads:downloads]
    }


    def downloadFile() {

        def mirror = params.mirror? Mirror.get(params.mirror) : null
        if (mirror) {
            // This used to do a download count, but ran into problems with
            // optimistic locking exceptions. Also, we'll probably move
            // downloads to SpringSource's S3 account for which we will get
            // download statistics
            redirect(url: mirror.urlString)
        }
        else {
            response.sendError 404
        }
    }

    def showUrl() {
        def mirror = params.mirror? Mirror.get(params.mirror) : null
        if (mirror) {
            render mirror.urlString
        }
        else {
            response.sendError 404
        }
    }

    def fileDetails() {
        def downloadFile = DownloadFile.get(params.id)
        [downloadFile:downloadFile]
    }

    def addFile(AddFileCommand cmd) {
        def download = Download.get(params.id)
        if(request.method == 'POST') {
            if(!cmd.url) {                 
                return [download:download, message:"Invalid URL"]
            }
            else if(!cmd.name) {
                return [download:download, message:"You must specify the name of the mirror"]
            }
            else {
                def downloadFile = new DownloadFile(params)
                downloadFile.addToMirrors(urlString: cmd.url, name:cmd.name)
                download.addToFiles(downloadFile)
                download.save()
                redirect(action:'show', id:download.id)
                return
            }
        }
        return [download:download]

    }

    def deleteMirror() {
        def mirror = Mirror.get(params.id)
        if(mirror) {
            mirror.delete(flush:true)
            render(template:'mirrorList', model:[downloadFile:mirror.file])
        }
        else {
            render "Mirror not found for id ${params.id}"
        }
    }

    def addMirror() {
        def downloadFile = DownloadFile.get(params.id)

        if(downloadFile) {
            def mirror = new Mirror(params)
            if(mirror.urlString) {
                downloadFile.addToMirrors(mirror)
                downloadFile.save(flush:true)
                render(template:'mirrorList', model:[downloadFile:downloadFile])   
            }
            else {
                render "Invalid URL specified"
            }
        }
        else {
            render "File not found for id ${params.id}"
        }
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [
            delete:'POST',
            save:'POST',
            update:'POST',
            apiList: 'GET',
            apiShow: 'GET']

    def apiList() {
        // Default sorting.
        if (!params.sort && !params.order) {
            params.sort = "softwareVersion"
            params.order = "desc"
        }

        def downloads = Download.list(params)
        withFormat {
            json {
                renderDownloadsAsJson downloads
            }
            xml {
                renderDownloadsAsXml downloads
            }
        }
    }

    def apiShow() {
        def download = Download.findBySoftwareVersion(params.version)
        if (!download) {
            response.sendError 404
        }
        else {
            withFormat {
                json {
                    render([download: downloadDataAsMap(download)] as JSON)
                }
                xml {
                    render contentType: "application/xml", {
                        buildDownloadData delegate, download
                    }
                }
            }
        }
    }

    def list = {
        if (!params.max) params.max = 10
        [ downloadList: Download.list( params ) ]
    }

    def show = {
        def download = Download.get( params.id )

        if(!download) {
            flash.message = "Download not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ download : download ] }
    }

    def delete = {
        def download = Download.get( params.id )
        if(download) {
            download.delete()
            flash.message = "Download ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Download not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def download = Download.get( params.id )

        if(!download) {
            flash.message = "Download not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ download : download ]
        }
    }

    def update = {
        def download = Download.get( params.id )
        if(download) {
            download.properties = params
            if(!download.hasErrors() && download.save()) {
                flash.message = "Download ${params.id} updated"
                redirect(action:show,id:download.id)
            }
            else {
                render(view:'edit',model:[download:download])
            }
        }
        else {
            flash.message = "Download not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def download = new Download(params)
        return ['download':download]
    }

    def save = {
        def download = new Download(params)
        if(!download.hasErrors() && download.save()) {
            flash.message = "Download ${download.id} created"
            redirect(action:show,id:download.id)
        }
        else {
            render(view:'create',model:[download:download])
        }
    }

    def adminShowVersionOrder = {
        render template: "showDisplayedVersions", model: [versions: versionOrder]
    }

    def adminEditVersionOrder = {
        render template: "editDisplayedVersions", model: [versions: versionOrder]
    }

    def adminUpdateVersionOrder = {
        def hasError = false
        def versions = params.versions?.split(/\s*,\s*/)

        VersionOrder.withTransaction { status ->
            VersionOrder.executeUpdate("delete from VersionOrder")
            versions.eachWithIndex { v, i ->
                hasError |= !new VersionOrder(baseVersion: v, orderIndex: i + 1).save()
            }

            if (hasError) {
                status.setRollbackOnly()
            }
        }

        render template: "showDisplayedVersions", model: [versions: versionOrder, hasError: hasError]
    }

    private List getVersionOrder() {
        return VersionOrder.list(sort: "orderIndex", order: "asc")
    }

    protected void renderDownloadsAsXml(downloads) {
        render contentType: "application/xml", {
            delegate.downloads {
                for (d in downloads) {
                    buildDownloadData delegate, d
                }
            }
        }
    }

    protected void buildDownloadData(delegate, d) {
        delegate.download name: d.softwareName,
                          version: d.softwareVersion,
                          beta: d.betaRelease,
                          releaseNotes: g.createLink(uri: d.releaseNotes, absolute: true), {
            files {
                for (f in d.files) {
                    file(title: f.title) {
                        mirrors {
                            for (m in f.mirrors) {
                                mirror name: m.name, url: m.urlString
                            }
                        }
                    }
                }
            }
        }
    }

    protected void renderDownloadsAsJson(downloads) {
        def data = [:]
        data.downloads = downloads.collect { d ->
            downloadDataAsMap(d)
        }
        render data as JSON
    }

    protected downloadDataAsMap(d) {
        return [
                name: d.softwareName,
                version: d.softwareVersion,
                beta: d.betaRelease,
                releaseNotes: g.createLink(uri: d.releaseNotes, absolute: true),
                files: d.files.collect { f ->
                    [title: f.title, mirrors: f.mirrors.collect { m -> [name: m.name, url: m.urlString] }]
                }]
    }
}

@Validateable
class AddFileCommand {
    String url
    String name
}
