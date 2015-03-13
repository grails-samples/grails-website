package org.grails.taggable

import org.grails.plugin.Plugin

/*
 * author: Matthew Taylor
 */
class TagService {
    static transactional = false

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

    List<Tag> getPluginTagArray() {
        def tags = TagLink.executeQuery("select tl.tag.name, count(tl.tagRef) as c from org.grails.taggable.TagLink tl where tl.type = ? group by tl.tag order by c desc", ["Plugin"], [max: 16])

        return tags.collect { it[0] }
    }
}
