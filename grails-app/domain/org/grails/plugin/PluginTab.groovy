package org.grails.plugin

import org.grails.content.Content

/**
 * Domain class representing a plugin tab wiki page.
 */
class PluginTab extends Content {
    static searchable = {
        root false
        only = ['title', 'body']
    }
}
