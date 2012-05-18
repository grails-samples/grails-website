package org.grails.search

import org.grails.content.Content
import org.grails.plugin.platform.events.Listener
import org.grails.plugin.Plugin

class IndexingService {

    @Listener
    def pluginUpdated(Map message) {
        log.info "Reindexing plugin with ID ${message.id}"
        Plugin.reindex(message.id)
    }

    @Listener
    def wikiPageUpdated(Map message) {
        log.info "Reindexing wiki page with ID ${message.id}"
        Content.reindex(message.id)
    }
}
