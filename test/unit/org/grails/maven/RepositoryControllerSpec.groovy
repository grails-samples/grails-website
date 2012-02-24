package org.grails.maven

import grails.test.mixin.*

@TestFor(RepositoryController)
class RepositoryControllerSpec extends spock.lang.Specification{

    // http://localhost:8080/maven/grails-acegi/tags/RELEASE_0_1/grails-acegi-0.1.zip
    void "Test that a request with a non valid host returns a 404"() {
        when:"A the controller is called with invalid arguments"
            controller.artifact(null,null,null,null)
        then:"a 404 status is returned"
            response.status == 404
    }
    
    void "Test maven redirect URL is correct"() {
        when:"An artifact is requested for a given plugin"
            controller.artifact("grails-cloud-foundry-0.1","cloud-foundry", "1.0", "zip")
            
        then:"The produced URL is correct"    
            response.redirectUrl == 'http://repo.grails.org/grails/plugins/org/grails/plugins/cloud-foundry/1.0/cloud-foundry-1.0.zip'
    }
}
