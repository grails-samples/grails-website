import grails.util.Environment

class DevSessionCookieGrailsPlugin {
    def version = "1.0"
    def doWithWebDescriptor = { xml ->
        if(Environment.current.isDevelopmentMode()) {
            // remove cookie-config element from session-config
            xml.'session-config'.'cookie-config'[0]?.replaceNode { }
        }
    }
}
