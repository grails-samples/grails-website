package org.grails.maven

import grails.test.mixin.*
import grails.plugins.rest.client.*
import org.grails.plugin.*
@TestFor(PluginDeployService)
@Mock(PendingRelease)
class PluginDeployServiceSpec extends spock.lang.Specification {
    
    def "Test that deploying an artifact invokes the correct artifactory URLs"() {
        given:"A pending release instance"
            def pr = new PendingRelease(pluginName:"tomcat", pluginVersion:"1.0.0", zip:"dummy-zip".bytes, pom:"dummy-pom".bytes, xml:"dummy-xml".bytes)
            pr.save flush:true

            RestBuilder mockRest = Mock()
            service.rest = mockRest
            assert PendingRelease.count() == 1

        when:"Deploy is invoked on the service"
            service.deployRelease(pr)

        then:"The release was deployed"
            PendingRelease.findByPluginNameAndPluginVersion("tomcat", "1.0.0").status == ReleaseStatus.COMPLETED
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0.zip",_) >> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0.zip.md5",_) >> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0.zip.sha1",_)>> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0.pom",_)>> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0.pom.md5",_)>> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0.pom.sha1",_)>> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0-plugin.xml",_)>> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0-plugin.xml.md5",_)>> [status:200]
            1 * mockRest.put("$service.releaseUrl/tomcat/1.0.0/tomcat-1.0.0-plugin.xml.sha1",_)>> [status:200]

    }
}
