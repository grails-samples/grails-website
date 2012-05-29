package org.grails.search

import grails.events.Listener
import org.grails.content.Content
import org.grails.plugin.Plugin

class IndexingService {

    @Listener
    void pluginUpdated(Map message) {
        log.info "Reindexing plugin with ID ${message.id}"
        Plugin.reindex(message.id)
    }

    @Listener
    void wikiPageUpdated(Map message) {
        log.info "Reindexing wiki page with ID ${message.id}"
        Content.reindex(message.id)
    }
}
