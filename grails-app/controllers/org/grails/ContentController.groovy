package org.grails

import grails.plugin.springcache.annotations.*

import javax.persistence.OptimisticLockException
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import org.codehaus.groovy.grails.web.metaclass.RedirectDynamicMethod
import org.codehaus.groovy.grails.web.servlet.HttpHeaders;
//import org.compass.core.engine.SearchEngineQueryParseException
import org.grails.blog.BlogEntry
import org.grails.content.Content
import org.grails.content.Version
import org.grails.content.WikiImage
import org.grails.content.notifications.ContentAlertStack
import org.grails.wiki.BaseWikiController
import org.grails.wiki.WikiPage
import org.grails.plugin.Plugin
import org.grails.plugin.PluginController
import org.grails.plugin.PluginTab
import org.grails.screencasts.Screencast
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.multipart.MultipartFile

class ContentController extends BaseWikiController {
    static allowedMethods = [saveWikiPage: "POST", rollbackWikiVersion: "POST"]

    def searchableService
    def screencastService
    def pluginService
    def dateService
    def wikiPageService
    def grailsUrlMappingsHolder
    def imageUploadService

    def index() {
        def wikiPage = wikiPageService.getCachedOrReal(params.id)
        if (wikiPage?.instanceOf(PluginTab)) {
            def plugin = wikiPage.plugin
            if (!plugin) {
                // Prevents web crawlers with stale data from causing an exception and
                // flooding the logs.
                response.sendError 404
            }
            else {
                // A bit of hackery: plugin tabs should not be accessed via this handler,
                // but sometimes they are. So, permanently redirect to the tab's plugin
                // portal page.
                redirect controller: "plugin", action: "show", params: [name: wikiPage.plugin.name], permanent: true
            }
        }
        else if (wikiPage) {
            // Permanent redirect for deprecated pages that have an alternative URL.
            if (wikiPage.deprecated && wikiPage.deprecatedUri) {
                redirect uri: wikiPage.deprecatedUri, permanent: true
                return
            }

            // This property involves a query, so we fetch it here rather
            // than in the view.
            def latestVersion = wikiPage.latestVersion
            if (request.xhr) {
                render template:"wikiShow", model:[content:wikiPage, update:params._ul, latest:latestVersion]
            } else {
                // disable comments
                render view:"contentPage", model:[content:wikiPage, latest:latestVersion]
            }
        }
        else {
            response.sendError 404
        }
    }

    def gettingStarted() {}

    def search() {
        if(params.q) {
            def q = "+(${params.q}) -deprecated:true".toString()
//            try {
                def searchResult = searchableService.search(q, classes: [WikiPage, Plugin], offset: params.offset, escape:false)
                flash.message = "Found $searchResult.total results!"
                flash.next()
                render view:"/searchable/index", model: [searchResult: searchResult]
//            }
//            catch (SearchEngineQueryParseException ex) {
//                render view: "/searchable/index", model: [parseException: true]
//            }
//            catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ex) {
//                render view: "/searchable/index", model: [clauseException: true]
//            }
        }
        else {
            render(view:"homePage")
        }
    }

    def latest() {

         def engine = createWikiEngine()

         def feedOutput = {

            def top5 = WikiPage.listOrderByLastUpdated(order:'desc', max:5)
            title = "Grails.org Wiki Updates"
            link = "http://grails.org/wiki/latest?format=${request.format}"
            description = "Latest wiki updates Grails framework community"

            for(item in top5) {
                entry(item.title) {
                    link = "http://grails.org/${item.title.encodeAsURL()}"
                    publishedDate = item.dateCreated?.toDate()
                    engine.render(item.body, context)
                }
            }
         }

        withFormat {
            html {
                redirect(uri:"")
            }
            rss {
                render(feedType:"rss",feedOutput)
            }
            atom {
                render(feedType:"atom", feedOutput)
            }
        }
    }

    def previewWikiPage() {
        def page = Content.findAllByTitle(params.id).find { !it.instanceOf(Version) }
        if(page) {
            // This is required for the 'page.properties = ...' call to work. 
            page = GrailsHibernateUtil.unwrapIfProxy(page)
            
            def engine = createWikiEngine()
            page.discard()
            page.properties = params

            render( engine.render(page.body, context) )
        }
    }

    def postComment() {
        def content = Content.get(params.id)
        content.addComment(request.user, params.comment)
        render(template:'/comments/comment', var:'comment', bean:content.comments[-1])
    }

    def showWikiVersion() {
        def page = Content.findAllByTitle(params.id).find { !it.instanceOf(Version) }
        def version
        if (page) {
            try {
                version = Version.findByCurrentAndNumber(page, params.number.toLong())
            }
            catch (NumberFormatException ex) {
                log.error "Not a valid version number: ${params.number}"
                log.error "Requested URL: ${request.forwardURI}, referred by: ${request.getHeader('Referer')}"
                response.sendError 404
                return
            }
        }
        else {
            response.sendError 404
            return
        }

        if (version) {
            render(view:"showVersion", model:[content:version, update:params._ul])                    
        }
        else {
            render(view:"contentPage", model:[content:page])
        }

    }

    def markupWikiPage() {
        def page = Content.findAllByTitle(params.id).find { !it.instanceOf(Version) }

        if(page) {
            render(template:"wikiFields", model:[wikiPage:page])
        }
    }

    def infoWikiPage() {
        def page = Content.findAllByTitle(params.id, [cache:true]).find { !it.instanceOf(Version) }

        if(page) {

            def pageVersions = Version.withCriteria {
                projections {
                    distinct 'number', 'version'
                    property 'author'
                }
                eq 'current', page
                order 'number', 'asc'
                cache true
            }
            def first = pageVersions ? Version.findByNumberAndCurrent(pageVersions[0][0], page, [cache:true]) : null
            def last  = pageVersions ? Version.findByNumberAndCurrent(pageVersions[-1][0], page, [cache:true]) : null

            render(template:'wikiInfo',model:[first:first, last:last,wikiPage:page, 
                                              versions:pageVersions.collect { it[0]}, 
                                              authors:pageVersions.collect { it[1]}, 
                                              update:params._ul])
        }

    }

    def editWikiPage() {
        if(!params.id) {
            render(template:"/shared/remoteError", model: [code:"page.id.missing"])
        }
        else {
            // WikiPage.findAllByTitle should only return one record, but at this time
            // (2010-06-24) it seems to be returning more on the grails.org server.
            // This is to help determine whether that's what is in fact happening.
            def pages = Content.findAllByTitle(params.id, [sort: "version", order: "desc"])
            if (pages?.size() > 1) log.warn "[editWikiPage] Content.findAllByTitle() returned more than one record!"
            def page = pages.find { !it.instanceOf(Version) }

            render(template:"wikiEdit",model:[
                    wikiPage:page,
                    update: params._ul,
//                    editFormName: params.editFormName,
                    saveUri: page.instanceOf(PluginTab) ?
                            g.createLink(controller: "plugin", action: "saveTab", id: page.title, pluginId: page.plugin.id) :
                            g.createLink(action: "saveWikiPage", id: page.title)])
        }
    }

    def createWikiPage() {
        if (params.xhr) {
            return render(template:'wikiCreate', var:'pageName', bean:params.id)
        }
        [pageName:params.id]
    }

    def saveWikiPage() {
        if (!params.id) {
            render(template:"/shared/remoteError", model:[code:"page.id.missing"])
        }
        else {
            try {
                def wikiPage = wikiPageService.createOrUpdateWikiPage(
                        params.id,
                        params.body,
                        request.user,
                        params.long('version'))

                if (wikiPage.hasErrors()) {
                    render(template: "wikiEdit", model: [
                            wikiPage: new WikiPage(title: params.id, body: params.body)])
                }
                else {
                    if (wikiPage.latestVersion.number == 0) {
                        // It's a new page.
                        redirect(uri:"/${wikiPage.title.encodeAsURL()}")
                    }
                    else {
                        render(template: "wikiShow", model: [
                                content: wikiPage,
                                message: "wiki.page.updated",
                                update: params._ul,
                                latest: wikiPage.latestVersion])
                    }
                }
            }
            catch (OptimisticLockException ex) {
                render(template: "wikiEdit", model: [
                        wikiPage: new WikiPage(title: params.id, body: params.body),
                        error: "page.optimistic.locking.failure"])
            }
        }
    }

    private evictFromCache(id, title) {
        cacheService.removeWikiText(title)
        cacheService.removeContent(title)
        if (id) cacheService.removeCachedText('versionList' + id)
    }

    def rollbackWikiVersion() {
        def page = Content.findAllByTitle(params.id).find { !it.instanceOf(Version) }
        if(page) {
            def version = Version.findByCurrentAndNumber(page, params.number.toLong())
            def allVersions = Version.withCriteria {
                projections {
                    distinct 'number', 'version'
                    property 'author'
                }
                eq 'current', page
                order 'number', 'asc'
                cache true
            }

            if(!version) {
                render(template:"versionList", model:[
                        wikiPage: page,
                        versions: allVersions.collect { it[0] },
                        authors: allVersions.collect { it[1] },
                        message:"wiki.version.not.found",
                        update: params._ul])
            }
            else {
                if(page.body == version.body) {
                    render(template:"versionList", model:[
                            wikiPage: page,
                            versions: allVersions.collect { it[0] },
                            authors: allVersions.collect { it[1] },
                            message:"Contents are identical, no need for rollback.",
                            update: params._ul])
                }
                else {

                    page.lock()
                    page.version = page.version+1
                    page.body = version.body
                    page.save(flush: true, failOnError: true)
                    eventAsync "wikiPageUpdated", [id: page.id]
                    Version v = page.createVersion()
                    v.author = request.user                        
                    v.save(failOnError: true)
                    evictFromCache page.id, page.title
                    
                    // Add the new version to the version list, otherwise it won't appear!
                    allVersions << [v.number, v.author]

                    render(template:"versionList", model:[
                            wikiPage: page,
                            versions: allVersions.collect { it[0] },
                            authors: allVersions.collect { it[1] },
                            message:"Page rolled back, a new version ${v.number} was created",
                            update: params._ul])
                }
            }
        }
        else {
            response.sendError(404)
        }
    }

    def diffWikiVersion() {

        def page = Content.findAllByTitle(params.id).find { !it.instanceOf(Version) }
        if(page) {
            def leftVersion = params.number.toLong()
            def left = Version.findByCurrentAndNumber(page, leftVersion)
            def rightVersion = params.diff.toLong()
            def right = Version.findByCurrentAndNumber(page, rightVersion)
            if(left && right) {
                return [message: "Showing difference between version ${leftVersion} and ${rightVersion}", text1:right.body.encodeAsHTML(), text2: left.body.encodeAsHTML()]
            }
            else {
                return [message: "Version not found in diff"]
            }

        }
        else {
            return [message: "Page not found to diff" ]
        }
    }

    def previousWikiVersion() {
        def page = Content.findAllByTitle(params.id).find { !it.instanceOf(Version) }
        if(page) {
            def leftVersion = params.number.toLong()
            def left = Version.findByCurrentAndNumber(page, leftVersion)

            List allVersions = Version.findAllByCurrent(page).sort { it.number }
            def right = allVersions[allVersions.indexOf(left)-1]
            def rightVersion = right.number

            if(left && right) {
                render(view:"diffView",model:[content:page,message: "Showing difference between version ${leftVersion} and ${rightVersion}", text1:right.body.encodeAsHTML(), text2: left.body.encodeAsHTML()])
            }
            else {
                render(view:"diffView",model:[message: "Version not found in diff"])
            }

        }
        else {
            render(view:"diffView",model: [message: "Page not found to diff" ] )
        }

    }

    def uploadImage() {
        def message = null
        def uploadTypes = grailsApplication.config.wiki.supported.upload.types ?: []

        if (request.method == 'POST') {
            MultipartFile file = request.getFile('image')
            log.info "Uploading image, file: ${file.originalFilename} (${file.contentType})"
            if (uploadTypes?.contains(file.contentType)) {

                try {
                    def newFilename = file.originalFilename.replaceAll(/\s+/, '_')
                    def wikiImage = new WikiImage(params)
                    wikiImage.name = getImageName(params.id, newFilename)
                    if (wikiImage.save()) {
                        imageUploadService.save(wikiImage)

                        render view: "/common/iframeMessage", model: [
                                pageId: "upload",
                                frameSrc: g.createLink(controller: 'content', action: 'uploadImage', id: params.id),
                                message: "Upload complete. Use the syntax !${wikiImage.name}! to refer to your file"]

                        // Break out on successful upload.
                        return
                    }
                    else {
                        message = "Error uploading file! " +
                                g.message(error: wikiImage.errors.fieldError, encodeAs: 'HTML')
                    }
                }
                catch (Exception e) {
                    log.error e.message, e
                    message = "Error uploading file! Info: ${e.message}"
                }
            }
            else {
                log.info "Bad file type, rendering error message to view"
                message = "File type not in list of supported types: ${uploadTypes?.join(',')}"
            }
        }

        render view: "/common/uploadDialog", model: [category: params.id, message: message]
    }

    /**
     * Renders an image that was uploaded to a wiki page. It delegates to the
     * Burning Image plugin's controller for serving up images, but it first
     * has to work out the correct parameters to pass to that controller.
     */
    def showImage(String path) {
        def wikiImage = WikiImage.findByName(path)
        if (wikiImage) {
            // Copied and modified from BurningImageTagLib. The WikiImage
            // domain class has a generated property 'biImage' that contains
            // the attached images - one for each configured size. We use
            // the BI image to pass the required parameters to the BI
            // controller.
            def size = "large"
            def image = wikiImage.biImage[size]
            cache neverExpires: true
            forward controller: "dbContainerImage", action: "index", params: [
                    imageId: image.ident(),
                    size: size,
                    type: image.type ]
        }
        else {
            response.sendError 404
        }
    }

    def deprecate() {
        def page = WikiPage.findByTitle(params.id)
        if (!page) {
            response.sendError 404
            return
        }

        WikiPage.withTransaction {
            page.deprecated = true
            page.deprecatedUri = params.uri
            if (page.save()) {
                wikiPageService.pageChanged(params.id)
            }
        }

        // This is a bit hacky, but hopefully a short-term solution. I'm
        // not convinced by the use of iframes for the upload and deprecate
        // dialogs.
        redirect action: "index", id: params.id
    }
    
    def homePage() {
        // Homepage needs latest plugins
        def newestPlugins = pluginService.newestPlugins(4)
        def newsItems = BlogEntry.list(max:3, cache:true, order:"desc", sort:"dateCreated")

        // make it easy to get the month and day
        newsItems.each {
            it.metaClass.getMonth = { ->
                dateService.getMonthString(it.dateCreated)
            }
            it.metaClass.getDay = { ->
                dateService.getDayOfMonth(it.dateCreated)
            }
        }
        def latestScreencastId = screencastService.latestScreencastId
        return [ newestPlugins: newestPlugins, 
                 newsItems: newsItems,
                 latestScreencastId: latestScreencastId ]
    }

    def screencastLegacy() {
        redirect controller: "screencast", action: "list", permanent: true
    }

    /**
     * Constructs an name for a wiki page image based on the name of a wiki
     * page (preferably the one the image is on!) and the original filename
     * of that image.
     */
    protected String getImageName(String wikiPageId, String filename) {
        def b = new StringBuilder()
        if (wikiPageId) b << wikiPageId << '/'
        b << filename
        return b.toString()
    }
}
