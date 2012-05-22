package org.grails.taggable

import org.grails.plugin.Plugin

/*
 * author: Matthew Taylor
 */
class TagService {

    def removeEmptyTags() {
        def emptyTags = []
        Tag.list().each { tag ->
            if (!TagLink.findByTag(tag)) {
                emptyTags << tag
            }
        }
        emptyTags.each {
            log.info "Removing empty tag '${it}'."
            it.delete()
        }
    }

    def ArrayList<Tag> getPluginTagArray() {
        def tags = Plugin.getAllTags()

        // Break up tags to fit in UI
        def tagArray = [[], []]
        tags.eachWithIndex { tag, i ->
            tagArray[i % 2].push(tag)
        }
        return tagArray
    }
}