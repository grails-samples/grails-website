package org.grails.downloads

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springcache.annotations.*
import grails.validation.Validateable
import net.sf.ehcache.Element
import net.sf.ehcache.Ehcache
import org.hibernate.FetchMode
import org.grails.content.Version

class DownloadController {
    def grailsApplication
    def downloadCache
    def downloadService
    def pluginService

    def legacyHome() {
        redirect action: "index", permanent: true
    }

    def ubuntu() {}

    def index() {
        // Returns a map of major version string ("1.1", "2.0", etc.) to
        // download info for that major version. This download info takes
        // the form
        def groupedDownloads = downloadService.getDownloadsByMajorVersion()
 
        // Extract the major versions of Grails that are in the DB at the moment.
        // These are sorted by highest version number first.
        def majorVersions = downloadService.sortVersions(groupedDownloads.keySet())
        
        def latestDownload
        for (entry in groupedDownloads) {
            latestDownload = entry.value.find { map -> map.download.latestRelease }
            if (latestDownload) break
        }

        return [majorVersions: majorVersions.reverse(),
                groupedDownloads: groupedDownloads,
                latestDownload: latestDownload ]
    }

    def archive() {
        redirect action: "index", permanent: true
    }

    def downloadFile() {

        def mirror = params.mirror?.isInteger() ? Mirror.get(params.mirror) : null
        if (mirror) {
            // This used to do a download count, but ran into problems with
            // optimistic locking exceptions. Also, we'll probably move
            // downloads to SpringSource's S3 account for which we will get
            // download statistics
            redirect url: mirror.urlString
        }
        else {
            response.sendError 404
        }
    }

    def showUrl() {
        def mirror = params.mirror ? Mirror.get(params.mirror) : null
        if (mirror) {
            render mirror.urlString
        }
        else {
            response.sendError 404
        }
    }

    /**
     * TODO: Not sure this should be here. It either needs deleting or moving
     * to the admin controller.
     */
    def addFile(AddFileCommand cmd) {
        def download = Download.get(params.id)
        if (request.method == 'POST') {
            if (!cmd.url) {
                return [download: download, message: "Invalid URL"]
            }
            else if (!cmd.name) {
                return [download: download, message: "You must specify the name of the mirror"]
            }
            else {
                def downloadFile = new DownloadFile(params)
                downloadFile.addToMirrors(urlString: cmd.url, name: cmd.name)
                download.addToFiles(downloadFile)
                download.save()
                redirect(action: 'show', id: download.id)
                return
            }
        }
        return [download: download]

    }

    def deleteMirror() {
        def mirror = Mirror.get(params.id)
        if (mirror) {
            mirror.delete(flush: true)
            render(template: 'mirrorList', model: [downloadFile: mirror.file])
        }
        else {
            render "Mirror not found for id ${params.id}"
        }
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [
            delete: 'POST',
            save: 'POST',
            update: 'POST',
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
