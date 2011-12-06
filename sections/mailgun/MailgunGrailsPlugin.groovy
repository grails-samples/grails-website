import grails.plugin.mailgun.MailgunSender

class MailgunGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "2.0 > *"
    def dependsOn = [:]
    def loadAfter = ["mail"]

    def title = "Mailgun Plugin"
    def author = "Peter Ledbrook"
    def authorEmail = "p.ledbrook@cacoethes.co.uk"
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/mailgun"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

    def doWithSpring = {
        def mailConfig = application.config.grails.mail

        mailgunSender(MailgunSender) {
            targetUrl = mailConfig.host
            username = mailConfig.username ?: "api"
            password = mailConfig.password
        }
    }
}
