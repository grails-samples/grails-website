import org.grails.test.pages.*

import geb.spock.GebReportingSpec
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseException

class PluginPortalFunctionalSpec extends GebReportingSpec {
    def setupSpec() {
        def http = new HTTPBuilder("http://localhost:8080")
        http.post path: "/test/fixtures/load", query: [file: "plugins"], body: ""
    }

    def cleanupSpec() {
        def http = new HTTPBuilder("http://localhost:8080")
        http.post path: "/test/fixtures/unload", body: ""
    }

    def "Test plugin portal home page"() {
        when: "we go to the plugin portal home page"
        to PluginHomePage
        
        then:
        at PluginHomePage
    }

    def "Test the page for a single plugin"() {
        when: "we go to the plugin portal home page"
        to PluginShowPage, "shiro"
        
        then:
        at PluginShowPage
        name.text() == "Integration of Apache Shiro security library"
        version.text() == "1.1-SNAPSHOT"
        authors.text() == "Peter Ledbrook"
    }

    def "Test showing an unknown plugin"() {
        when: "I go to the plugin page for an unknown plugin"
        to PluginShowPage, "ghdfgds"
        page LoginPage

        then: "I get the login page"
        at LoginPage
    }
}
