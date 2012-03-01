package org.grails.rest.client

import org.codehaus.groovy.grails.web.json.JSONObject
import groovy.util.slurpersupport.*

class RestBuilderSpec extends spock.lang.Specification {

    def "Test that a basic GET request returns a JSON result of the response type is JSON"(){
        given:"A rest client instance"
            def rest = new RestBuilder()

        when:"A get request is issued for a response that returns XML"
            def resp = rest.get("http://grails.org/api/v1.0/plugin/acegi/")

        then:"The response is a gpath result"
            resp != null
            resp instanceof JSONObject
            resp.name == 'acegi'
    }


    def "Test that a basic GET request returns a JSON result of the response type is JSON with custom settings"(){
        given:"A rest client instance"
            def rest = new RestBuilder(connectTimeout:1000, readTimeout:20000)

        when:"A get request is issued for a response that returns XML"
            def resp = rest.get("http://grails.org/api/v1.0/plugin/acegi/")

        then:"The response is a gpath result"
            resp != null
            resp instanceof JSONObject
            resp.name == 'acegi'
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
            resp instanceof GPathResult
            resp.name == 'acegi'
    }
}
