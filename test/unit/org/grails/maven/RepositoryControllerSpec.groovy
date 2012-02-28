package org.grails.maven

import grails.test.mixin.*
import org.grails.plugin.*
import org.joda.time.DateTime

@TestFor(RepositoryController)
@Mock([Plugin, PluginRelease])
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
    
    void "Test that the plugin list generates XML in the right format"() {
        given:"A plugin with a release"
            def p = new Plugin( name: "tomcat",
                                title: "Tomcat",
                                currentRelease: "1.3.4",
                                author: "SpringSource",
                                authorEmail:'foo@bar.com',
                                downloadUrl:"http://foo.com/tomcat-1.0.zip",
                                documentationUrl:"http://grails.org/plugin/tomcat",
                                lastReleased: new DateTime(2010, 8, 11, 22, 30))
            p.releases = [ new PluginRelease(plugin:p, releaseVersion:"1.0.0", releaseDate:new DateTime(2010, 8, 11, 22, 30), downloadUrl:"http://foo.com/tomcat-1.0.zip")]
            p.save(flush:true)
            assert !p.hasErrors()
            
        when:"The domain model is queried"
            def allPlugins = Plugin.list()
            
        then:"It is in the correct state"
            allPlugins.size() == 1
            allPlugins[0].releases.size() == 1
            
        when:"The plugin list is rendered"
            controller.list()
            println response.text
        then:"The generated xml is correct"
            response.xml.plugin.size() == 1
            response.xml.plugin[0].@name == 'tomcat'
            response.xml.plugin[0].'@latest-release' == '1.0.0'            
            response.xml.plugin[0].release.size() == 1
            response.xml.plugin[0].release[0].@version == '1.0.0'
            response.xml.plugin[0].release[0].author.text() == 'SpringSource'
            response.xml.plugin[0].release[0].authorEmail.text() == 'foo@bar.com'
            response.xml.plugin[0].release[0].file.text() == "http://foo.com/tomcat-1.0.zip"

    }
}
