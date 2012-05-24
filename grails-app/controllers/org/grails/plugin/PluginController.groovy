package org.grails.plugin

import org.grails.tags.TagNotFoundException
import org.grails.common.ApprovalStatus
import org.compass.core.engine.SearchEngineQueryParseException

class PluginController {

    def pluginService
    def tagService

    def list() {
        def plugins = []
        def pluginCount = 0
        def maxResults = params.int('max') ?: 10
        def offset = params.int('offset') ?: 0

        def filter = params.filter ? params.filter.toString() : null
        if (filter) {
            (plugins, pluginCount) = pluginService."list${filter}PluginsWithTotal"(max: maxResults, offset: offset)
        }
        else {
            (plugins, pluginCount) = pluginService.listNewestPluginsWithTotal(max: maxResults, offset: offset)
        }

        def tags = tagService.getPluginTagArray()
        [ tags: tags, plugins: plugins, pluginCount: pluginCount ]
    }

    def listByTag() {
        try {
            def tags = tagService.getPluginTagArray()
            def maxResults = params.int('max') ?: 10
            def offset = params.int('offset') ?: 0
            def (plugins, pluginCount) = pluginService.listPluginsByTagWithTotal(params.tag, max: maxResults, offset: offset)
            render view: 'list', model: [
                    tags: tags,
                    plugins: plugins,
                    pluginCount: pluginCount,
                    max: maxResults,
                    offset: offset
            ]
        }
        catch (TagNotFoundException ex) {
            flash.message = "Tag not found"
            redirect action: 'list'
        }
    }

    def plugin() {
        def plugin = Plugin.findByName(params.id)

        // Redirect to the list page if the plugin doesn't exist
        if (!plugin) {
            flash.message = "Plugin not found"
            redirect action: 'list'
            return
        }

        def tags = tagService.getPluginTagArray()
        [ plugin: plugin, tags: tags ]
    }

    def submitPlugin() {
        def pluginPendingApproval = new PluginPendingApproval(
            submittedBy: request.user,
            status: ApprovalStatus.PENDING
        )
        if (request.method == "POST") {
            pluginPendingApproval.name = params.name
            pluginPendingApproval.scmUrl = params.scmUrl
            pluginPendingApproval.versionNumber = params.versionNumber
            pluginPendingApproval.notes = params.notes

            pluginPendingApproval.validate()
            println "PPA: ${pluginPendingApproval.inspect()}"

            if (!pluginPendingApproval.hasErrors() && pluginPendingApproval.save(flush:true)) {
                flash.message = "Your plugin has been submitted for approval"
                redirect url: "/plugins/pending/${pluginPendingApproval?.id}"
            } else {
                flash.message = "Please correct the fields below"
                flash.next()
            }
        }
        [pluginPendingApproval: pluginPendingApproval]
    }

    def submissionReceived() {
    }

    def pendingPlugins() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def tags = tagService.getPluginTagArray()
        [
                tags: tags,
                pluginPendingApprovalList: PluginPendingApproval.pending.list(params),
                pluginPendingApprovalTotal: PluginPendingApproval.pending.count()
        ]
    }

    def showPendingPlugin() {
        def pluginPendingApprovalInstance = PluginPendingApproval.get(params.id)
        if (!pluginPendingApprovalInstance) {
            redirect action: 'pendingPlugins'
        }

        [pluginPendingApprovalInstance: pluginPendingApprovalInstance]
    }

    def search() {
        if(params.q) {
            def tags = tagService.getPluginTagArray()
            try {
                println "Q: ${params.q}"
                def searchResult = Plugin.search(params.q, offset: params.offset)

                println searchResult.inspect()

                searchResult.results = searchResult.results.findAll{it}.unique { it.title }
                flash.message = "Found $searchResult.total results!"
                flash.next()
                render view: "searchResults", model: [tags: tags, searchResult: searchResult]
            }
            catch (SearchEngineQueryParseException ex) {
                render view: "searchResults", model: [tags: tags, parseException: true]
            }
            catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ex) {
                render view: "searchResults", model: [tags: tags, clauseException: true]
            }
        }
        else {
            redirect(action: 'list')
        }
    }

}