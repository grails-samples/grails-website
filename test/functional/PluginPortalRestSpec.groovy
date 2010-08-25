import static groovyx.net.http.ContentType.*

import groovyx.net.http.HTTPBuilder
import spock.lang.Shared
import spock.lang.Specification

class PluginPortalRestSpec extends Specification {
    @Shared http = new HTTPBuilder("http://localhost:8080")
    
    def setupSpec() {
        http.post path: "/test/fixtures/load", query: [file: "plugins"], body: "", { resp ->
            println "Status: ${resp.statusLine.statusCode}"
        }
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
