import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import groovyx.net.http.HTTPBuilder
import spock.lang.Specification

/**
 * Functional tests for the plugin portal's REST-like interface. It basically
 * tests all the URLs that don't return or redirect to HTML pages. We use
 * HttpBuilder for this because Geb is page/HTML oriented.
 */
class PluginPortalRestSpec extends Specification {
    def http = new HTTPBuilder("http://localhost:8080")
    
    def "Test updating plugin info via a 'ping'"() {
        given:
        http.handler.success = { it.statusLine.statusCode }
        http.handler.failure = { it.statusLine.statusCode }
        
        when: "a 'ping' is sent telling the portal to update a plugin's info"
        def status = http.request(PUT, JSON) { req ->
           uri.path = url
           uri.query = [format: "json"]
           body = requestBody
        }

        then: "the appropriate status code is returned"
        status == expectedStatus

        where:
        url                 | expectedStatus | requestBody
        "/plugin/shiro"     | 200            | [url: "http://localhost:8080/test/shiro" ]
        "/plugin/notKnown"  | 404            | [url: "http://localhost:8080/test/shiro" ]
        "/plugin/shiro"     | 400            | [test: "http://localhost:8080/test/shiro" ]
        "/plugin/shiro"     | 400            | [url: "this is not a URL" ]
        "/plugin/shiro"     | 400            | [url: "/relative/path/uri" ]
    }

    def "Test plugin list in JSON"() {
        when: "the plugin list is retrieved with format 'json'"
        def json = http.get(path: "/plugins", query: [format:'json'], contentType: JSON)

        then: "all six plugins are returned in alphabetical order by name"
        json.plugins.size() == 6
        json.plugins*.name == [
                "build-test-data",
                "fixtures",
                "gwt",
                "hibernate",
                "shiro",
                "tomcat" ]

        and: "with their titles"
        json.plugins*.title == [
                "Build Test Data Plugin",
                "Fixtures Plugin",
                "GWT Integration",
                "Hibernate ORM Plugin",
                "Integration of Apache Shiro security library",
                "Tomcat" ]
    }

    def "Test plugin list in XML"() {
        when: "the plugin list is retrieved with format 'xml'"
        def xml = http.get(path: "/plugins", query: [format:'xml'], contentType: XML)

        then: "all six plugins are returned in alphabetical order by name"
        xml.plugin.size() == 6
        xml.plugin*.@name == [
                "build-test-data",
                "fixtures",
                "gwt",
                "hibernate",
                "shiro",
                "tomcat" ]

        and: "with their titles"
        xml.plugin.title*.text() == [
                "Build Test Data Plugin",
                "Fixtures Plugin",
                "GWT Integration",
                "Hibernate ORM Plugin",
                "Integration of Apache Shiro security library",
                "Tomcat" ]
    }
}
