package org.grails.plugin

import org.grails.content.Content

/**
 * Domain class representing a plugin tab wiki page.
 */
class PluginTab extends Content {
    
    static transients = ['plugin']
    
    static searchable = {
        root false
        only = ['title', 'body']
    }
    
    Plugin getPlugin() {
        return Plugin."findBy${capitalize(PluginService.extractTabName(title))}"(this)
    }
    
    private capitalize(str) {
        if (!str) return str
        return str[0].toUpperCase() + str[1..-1]
    }
}
