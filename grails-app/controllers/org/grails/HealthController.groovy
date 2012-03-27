package org.grails

import org.grails.plugin.Plugin

/**
 * Simple controller with one page that renders a health string of the
 * form "DB:(UP|DOWN)".
 */
class HealthController {
    def index() {
        def sb = new StringBuilder()
        sb << "DB:"

        try {
            Plugin.count()
            sb << "UP"
        }
        catch (Throwable t) {
            log.error "Health check on DB failed: ${t.message}"
            sb << "DOWN"
        }

        render text: sb.toString()
    }
}
