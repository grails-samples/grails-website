import grails.plugin.like.*

class LikeGrailsPlugin {
    def version = "1.0-SNAPSHOT"
    def grailsVersion = "1.2 > *"
    def loadAfter = ["shiro", "spring-security-core"]
    def pluginExcludes = [ "grails-app/views/error.gsp" ]

    def author = "Peter Ledbrook"
    def authorEmail = "pledbrook@vmware.com"
    def title = "Like/Dislike Plugin"
    def description = '''\
Provides the domain model and tags for a like/dislike feature. This doesn't integrate \
with social networks - the liking and disliking only happens internally to your application.'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/like"

    def doWithApplicationContext = { applicationContext ->
        if (!applicationContext.containsBean("currentUser")) {
            def msg = "[Like Plugin] You must define a 'currentUser' bean that integrates with your authentication system."
            log.error msg
            throw new RuntimeException(msg)
        }
    }
}
