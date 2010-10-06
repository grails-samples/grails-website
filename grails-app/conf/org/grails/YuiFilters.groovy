package org.grails

import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptTagLib

/**
 * This filter ensures that the YUI plugin is used for the adaptive
 * AJAX tags.
 */
class YuiFilters {
    def filters = {
        all(uri: "/**") {
            before = {
                request[JavascriptTagLib.INCLUDED_LIBRARIES] = ['yui']
            }
        }
    }
}
