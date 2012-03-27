package org.grails.plugin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import org.grails.maven.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(PendingReleaseController)
@Mock(PendingRelease)
class PendingReleaseControllerSpec extends spock.lang.Specification{


    void "Test deploy action"() {
        given:"A pending release"
            def pr = new PendingRelease(pluginName:"tomcat", pluginVersion:"1.0.0", zip:"dummy".bytes, pom:"dummy".bytes, xml:"dummy".bytes)

            assert pr.save( flush:true)

            PluginDeployService pluginDeployService = Mock()
            controller.pluginDeployService = pluginDeployService
        when:"The deploy action is called"
            controller.deploy(pr.id)

        then:"The deploy service is invoked"
            1 * pluginDeployService.deployRelease(pr)
    }
}
