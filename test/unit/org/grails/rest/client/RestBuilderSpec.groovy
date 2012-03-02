package org.grails.rest.client

import org.codehaus.groovy.grails.web.json.*
import groovy.util.slurpersupport.*

import grails.test.mixin.*
import grails.test.mixin.web.*

@TestMixin(ControllerUnitTestMixin)
class RestBuilderSpec extends spock.lang.Specification {

    def "Test that a basic GET request returns a JSON result of the response type is JSON"(){
        given:"A rest client instance"
            def rest = new RestBuilder()

        when:"A get request is issued for a response that returns XML"
            def resp = rest.get("http://grails.org/api/v1.0/plugin/acegi/")

        then:"The response is a gpath result"
            resp != null
            resp.json instanceof JSONObject
            resp.json.name == 'acegi'
    }


    def "Test that a basic GET request returns a JSON result of the response type is JSON with custom settings"(){
        given:"A rest client instance"
            def rest = new RestBuilder(connectTimeout:1000, readTimeout:20000)

        when:"A get request is issued for a response that returns XML"
            def resp = rest.get("http://grails.org/api/v1.0/plugin/acegi/")

        then:"The response is a gpath result"
            resp != null
            resp.json instanceof JSONObject
            resp.json.name == 'acegi'
    }

    def "Test that a basic GET request returns a XML result of the response type is XML"(){
        given:"A rest client instance"
            def rest = new RestBuilder()

        when:"A get request is issued for a response that returns XML"
            def resp = rest.get("http://grails.org/api/v1.0/plugin/acegi/") {
                accept 'application/xml'
            }

        then:"The response is a gpath result"
            resp != null
            resp.xml instanceof GPathResult
            resp.xml.name == 'acegi'
    }

    def "Test basic authentication with GET request"() {
        given:"A rest client instance"
            def rest = new RestBuilder()

        when:"A get request is issued for a response that returns XML"
            def resp = rest.get("http://repo.grails.org/grails/api/security/users"){
                auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
            }

        then:"The response is a gpath result"
            resp != null
            resp.json instanceof JSONArray 
    }


    def "Test basic authentication with PUT request"() {
        given:"A rest client instance"
            def rest = new RestBuilder()

        when:"A get request is issued for a response that returns XML"
            def resp = rest.put("http://repo.grails.org/grails/api/security/groups/test-group"){
                auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
                contentType "application/vnd.org.jfrog.artifactory.security.Group+json"
                json {
                    name = "test-group"
                    description = "A temporary test group"
                }
            }
        then:"The response is a gpath result"
            resp != null
            resp.status == 201
            resp.text == "Created"

        when:"The resource contents are requested"
            resp = rest.get("http://repo.grails.org/grails/api/security/groups/test-group") {
                auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
            }

        then:"The contents are valid"
            resp != null
            resp.json.name == 'test-group'

        when:"The resource is deleted"
            resp = rest.delete("http://repo.grails.org/grails/api/security/groups/test-group") {
                auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
            }

        then:"The resource is gone"
            resp != null
            resp.status == 200
            resp.text == "Group 'test-group' has been removed successfully."
    }

    def "Test PUT request passing binary content in the body"() {
        setup:
            def rest = new RestBuilder(connectTimeout: 1000, readTimeout:10000)
            rest.delete("http://repo.grails.org/grails/libs-snapshots-local/org/mycompany/1.0/foo-1.0.jar") {
                auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
            }
   
        when:"A put request is issued that contains binary content"
            def resp = rest.put("http://repo.grails.org/grails/libs-snapshots-local/org/mycompany/1.0/foo-1.0.jar") {
                auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")
                body "foo".bytes
            }
            
        then:"The response JSON is correct"
            resp.json.uri == "http://repo.grails.org/grails/libs-snapshots-local/org/mycompany/1.0/foo-1.0.jar"
            new URL( "http://repo.grails.org/grails/libs-snapshots-local/org/mycompany/1.0/foo-1.0.jar").text == "foo"

    }
}
