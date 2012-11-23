package org.grails.downloads

import grails.test.mixin.*
import grails.test.mixin.support.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(DownloadService)
@Mock(Download)
class DownloadServiceSpec extends spock.lang.Specification {

    void setup() {
        new Download(softwareName: "Grails", softwareVersion: "1.1.2").save()
        new Download(softwareName: "Grails", softwareVersion: "1.0-SNAPSHOT").save()
        new Download(softwareName: "Grails", softwareVersion: "1.0.1").save()
        new Download(softwareName: "Gradle", softwareVersion: "3.4.3").save()
        new Download(softwareName: "Grails", softwareVersion: "1.1.BUILD-SNAPSHOT").save()
        new Download(softwareName: "Grails", softwareVersion: "2.0").save()
        new Download(softwareName: "Grails", softwareVersion: "2.0.1").save()
        new Download(softwareName: "Grails", softwareVersion: "10.3.0.BUILD-SNAPSHOT").save()
        new Download(softwareName: "Grails", softwareVersion: "2.10.5").save()
    }

    void tearDown() {
        // Tear down logic here
    }

    void "Retrieve major versions of downloads"() {
        given: "An instance of the download service"
		def service = new DownloadService()

		when: "The list of major versions for the stored downloads is retrieved"
		def results = service.listMajorVersions()

		then: "The list only includes the major versions of Grails downlaods and it is sorted in order of highest first."
		results == [ "10.3", "2.10", "2.0", "1.1", "1.0" ]
    }
}
