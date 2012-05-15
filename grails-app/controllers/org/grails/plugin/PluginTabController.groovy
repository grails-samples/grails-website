package org.grails.plugin

import javax.persistence.OptimisticLockException
import org.grails.content.Version

class PluginTabController {
    def cacheService
    def wikiPageService

    def index() {
        def wikiPage = wikiPageService.getCachedOrReal(params.id)
        if (wikiPage?.instanceOf(PluginTab)) {
            // This property involves a query, so we fetch it here rather
            // than in the view.
            def latestVersion = wikiPage.latestVersion
            render template: "/content/wikiShow",
                   model: [content: wikiPage,
                           update: params._ul,
                           latest: latestVersion]
        }
        else {
            response.sendError 404
        }
    }

    def infoWikiPage() {
        def page = PluginTab.findByTitle(params.id, [cache: true])

        if (page) {
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

            render template: '/content/wikiInfo',
                   model: [first: first, last: last, wikiPage: page, 
                            versions: pageVersions.collect { it[0]}, 
                            authors: pageVersions.collect { it[1]}, 
                            update: params._ul,
                            urlMapping: "pluginTab"]
        }
        else {
            response.sendError 404
        }
    }

    def showWikiVersion() {
        def page = PluginTab.findByTitle(params.id)
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
            render view: "/content/showVersion", model: [content: version, update: params._ul]
        }
        else {
            response.sendError 404
        }

    }

    def editWikiPage() {
        if (!params.id) {
            render template: "/shared/remoteError",
                   model: [code:"page.id.missing"]
        }
        else {
            def page = PluginTab.findByTitle(params.id, [sort: "version", order: "desc"])

            render template: "/content/wikiEdit", model: [
                    wikiPage: page,
                    update: params._ul ]
        }
    }

    def saveWikiPage() {
        try {
            def wikiPage = wikiPageService.createOrUpdatePluginTab(
                    params.id,
                    params.body,
                    request.user,
                    params.long('version'))

            if (wikiPage.hasErrors()) {
                render(template: "/content/wikiEdit", model: [
                        wikiPage: new PluginTab(title: params.id, body: params.body),
                        update: params._ul])
            }
            else {
                if (wikiPage.latestVersion.number == 0) {
                    // It's a new page.
                    redirect uri: "/${wikiPage.title.encodeAsURL()}"
                }
                else {
                    render template: "/content/wikiShow", model: [
                            content: wikiPage,
                            message: "wiki.page.updated",
                            update: params._ul,
                            latest: wikiPage.latestVersion]
                }
            }
        }
        catch (OptimisticLockException ex) {
            render(template: "/content/wikiEdit", model: [
                    wikiPage: new PluginTab(title: params.id, body: params.body),
                    update: params._ul,
                    error: "page.optimistic.locking.failure"])
        }
    }

    def diffWikiVersion() {
        def page = PluginTab.findByTitle(params.id)
        def model = [:]
        if (page) {
            def leftVersion = params.number.toLong()
            def left = Version.findByCurrentAndNumber(page, leftVersion)
            def rightVersion = params.diff.toLong()
            def right = Version.findByCurrentAndNumber(page, rightVersion)
            if (left && right) {
                model = [
                        message: "Showing difference between version ${leftVersion} and ${rightVersion}",
                        text1: right.body.encodeAsHTML(),
                        text2: left.body.encodeAsHTML()]
            }
            else {
                model = [message: "Version not found in diff"]
            }

        }
        else {
            model = [message: "Page not found to diff" ]
        }

        render view: "/content/diffWikiVersion", model: model
    }

    def markupWikiPage() {
        def page = PluginTab.findByTitle(params.id)

        if (page) {
            render template: "/content/wikiFields", model: [wikiPage: page]
        }
        else {
            response.sendError 404
        }
    }

    def rollbackWikiVersion() {
        def page = PluginTab.findByTitle(params.id)
        if (page) {
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

            if (!version) {
                render template: "/content/versionList", model: [
                        wikiPage: page,
                        versions: allVersions.collect { it[0] },
                        authors: allVersions.collect { it[1] },
                        message: "wiki.version.not.found",
                        update: params._ul,
                        urlMapping: "pluginTab"]
            }
            else {
                if (page.body == version.body) {
                    render template: "/content/versionList", model: [
                            wikiPage: page,
                            versions: allVersions.collect { it[0] },
                            authors: allVersions.collect { it[1] },
                            message: "Contents are identical, no need for rollback.",
                            update: params._ul,
                            urlMapping: "pluginTab"]
                }
                else {
                    page.lock()
                    page.version = page.version + 1
                    page.body = version.body
                    page.save(flush: true, failOnError: true)
                    Version v = page.createVersion()
                    v.author = request.user                        
                    v.save(failOnError: true)
                    evictFromCache page.id, page.title
                    
                    // Add the new version to the version list, otherwise it won't appear!
                    allVersions << [v.number, v.author]

                    render template: "/content/versionList", model: [
                            wikiPage: page,
                            versions: allVersions.collect { it[0] },
                            authors: allVersions.collect { it[1] },
                            message: "Page rolled back, a new version ${v.number} was created",
                            update: params._ul,
                            urlMapping: "pluginTab"]
                }
            }
        }
        else {
            response.sendError 404
        }
    }

    private evictFromCache(id, title) {
        cacheService.removeWikiText(title)
        cacheService.removeContent(title)
        
        if (id) cacheService.removeCachedText('versionList' + id)
    }
}
