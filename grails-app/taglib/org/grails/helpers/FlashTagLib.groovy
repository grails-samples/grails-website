package org.grails.helpers

class FlashTagLib {

    static namespace = "flash"

    def message = { attrs, body ->
        def bean = attrs.bean
        def flash = attrs.flash
        def className = 'info'
        if (bean?.errors?.errorCount > 0) {
            className = 'error'
        }
        if (flash?.message) {
            out << "<div class=\"alert alert-${className}\">${flash?.message?.encodeAsHTML()}</div>"
        }
    }

}
