package org.grails.downloads

import grails.plugin.springcache.annotations.*
import net.sf.ehcache.Element
import net.sf.ehcache.Ehcache
import org.hibernate.FetchMode

class DownloadController {
    def grailsApplication
    def downloadCache
    
    def index = { redirect(action:list,params:params) }


    def latest = {
        // Find out which versions we should display. This should be a
        // list like ['2.0', '1.3', '1.2'].
        def versions = grailsApplication.config.download.versions ?: ['1.3', '1.2']

        def stableDownloads = versions.inject([:]) { map, version ->
            def m = version =~ /(\d+\.\d+)\s?(beta)?/
            def verNumber = m[0][1]
            def isBeta = m[0][2] as boolean

            def binaryDownload = getCachedOr("Grails ${version}") {
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
                downloads ? downloads[0] : null
            }

            def docDownload = !binaryDownload ? null : getCachedOr("Grails Documentation ${version}") {
                return Download.findBySoftwareNameAndSoftwareVersion(
                        'Grails Documentation',
                        binaryDownload.softwareVersion,
                        [fetch: [files: 'select']])
            }

            map[version] = [ binaryDownload, docDownload ]
            return map
        }

        def betaDownload = getCachedOr("GrailsBeta") {
            def downloads = Download.findAllBySoftwareNameAndBetaRelease(
                    'Grails',
                    true,
                    [max:1, order:'desc', sort:'releaseDate', cache:true, fetch: [files: 'select']])
            downloads ? downloads[0] : null
        }

        def betaDoc = !betaDownload ? null : getCachedOr("GrailsBeta Documentation") {
            return Download.findBySoftwareNameAndSoftwareVersion(
                    'Grails Documentation',
                    betaDownload.softwareVersion,
                    [fetch: [files: 'select']])
        } 

        render(view:'index', model:[stableDownloads:stableDownloads, betaDownload:[(betaDownload.softwareVersion): [betaDownload, betaDoc]]])
    }

    def getCachedOr(String name, callable) {
        def obj = downloadCache?.get(name)?.value 
        if(!obj) {
            obj = callable.call()
            downloadCache?.put new Element(name, obj)
        }
        return obj
    }

    def archive = {
        def downloads = Download.findAllBySoftwareName(params.id, [order:'desc', sort:'releaseDate', cache:true])

        return [downloads:downloads]
    }


    def downloadFile = {

        def mirror = params.mirror? Mirror.get(params.mirror) : null
        if(mirror) {
            // This used to do a download count, but ran into problems with
            // optimistic locking exceptions. Also, we'll probably move
            // downloads to SpringSource's S3 account for which we will get
            // download statistics
            redirect(url:mirror.url)
        }
        else {
            response.sendError 404
        }
    }

    def fileDetails = {
        def downloadFile = DownloadFile.get(params.id)
        [downloadFile:downloadFile]
    }

    def addFile = { AddFileCommand cmd ->
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
                downloadFile.addToMirrors(url:cmd.url, name:cmd.name)
                download.addToFiles(downloadFile)
                download.save()
                redirect(action:'show', id:download.id)
            }
        }
        return [download:download]

    }

    def deleteMirror = {
        def mirror = Mirror.get(params.id)
        if(mirror) {
            mirror.delete(flush:true)
            render(template:'mirrorList', model:[downloadFile:mirror.file])
        }
        else {
            render "Mirror not found for id ${params.id}"
        }
    }

    def addMirror = {
        def downloadFile = DownloadFile.get(params.id)

        if(downloadFile) {
            def mirror = new Mirror(params)
            if(mirror.url) {
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
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
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
}
class AddFileCommand {
    URL url
    String name
}
