class SharedGrailsPlugin {
    def version = "1.0"
    def grailsVersion = "1.2 > *"
    def pluginExcludes = [ "grails-app/views/error.gsp" ]

    def author = "Peter Ledbrook"
    def authorEmail = "pledbrook@vmware.com"
    def title = "grails.org Sections Plugin"
    def description = "Shared resources for the sections of the website that share similar behaviour."

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/shared"
}
