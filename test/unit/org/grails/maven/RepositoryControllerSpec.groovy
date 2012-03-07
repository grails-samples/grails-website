package org.grails.maven

import grails.test.mixin.*
import org.grails.plugin.*
import org.joda.time.DateTime

@TestFor(RepositoryController)
@Mock([Plugin, PluginRelease, PendingRelease])
class RepositoryControllerSpec extends spock.lang.Specification{


    void "Test publish plugin invalid method"() {

        when:"The publish plugin action is called with invalid data"
            controller.publish()
        then:"Return a 405"
            response.status == 405
    }
    void "Test publish plugin invalid data"() {
        when:"The publish plugin action is called with invalid data"
            request.method = "POST"
            controller.publish()
        then:"Return a 403"
            response.status == 400
    }

    void "Test that publishing a publish with an existing release works"() {
        when:"An existing plugin is created"
            def tomcat = tomcatPlugin
            params.plugin = "tomcat"
            params.version = "1.0.0"
            params.zip = "dummy"
            params.xml = "dummy"
            params.pom = "dummy"

        then:"The plugin release exists"
            PluginRelease.count() == 1
            PluginRelease.findByPluginAndReleaseVersion(tomcat, params.version) != null
        when:"publish is called"
            request.method = "POST"
            controller.publish()
        then:"The operation is forbidden because the plugin already exists"
            response.status == 403
            response.text == 'Plugin [tomcat] already published for version [1.0.0]'
    }

    void "Test publish plugin without necessary files"() {
        when:"publish is called without files to upload"
            params.plugin = "tomcat"
            params.version = "1.0.0"
            request.method = "POST"
            controller.publish()

        then:"A bad request response is issued"
            response.status == 400
            response.text == 'Missing plugin data. Include name, version, pom, zip and xml in your multipart request'
    }

    void "Test that publishing a plugin with correct data triggers a spring event"() {
        
        when:"publish is called with valid data"
            params.plugin = "tomcat"
            params.version = "1.0.0"
            params.zip = "dummy"
            params.xml = "dummy"
            params.pom = "dummy"
            def event
            controller.metaClass.publishEvent = {
                event = it
            }
            request.method = "POST"
            controller.publish()

        then:"The pending release is created and event published"
            PendingRelease.count() == 1
            event != null
            event.source instanceof PendingRelease
            response.status == 200
            response.text == "Published"
    }

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
	    def tomcat = tomcatPlugin            
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


    Plugin getTomcatPlugin() {
        def p = new Plugin( name: "tomcat",
                            title: "Tomcat",
                            currentRelease: "1.0.0",
                            author: "SpringSource",
                            authorEmail:'foo@bar.com',
                            downloadUrl:"http://foo.com/tomcat-1.0.zip",
                            documentationUrl:"http://grails.org/plugin/tomcat",
                            lastReleased: new DateTime(2010, 8, 11, 22, 30))
        p.releases = [ new PluginRelease(plugin:p, releaseVersion:"1.0.0", releaseDate:new DateTime(2010, 8, 11, 22, 30), downloadUrl:"http://foo.com/tomcat-1.0.zip")]
        p.save(flush:true)
        assert !p.hasErrors()
        return p
    }
}
