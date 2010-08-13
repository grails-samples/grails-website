package org.grails.downloads

import grails.test.ControllerUnitTestCase
import org.grails.downloads.Download

class DownloadControllerUnitTests extends ControllerUnitTestCase {
    void testLatest() {
        mockDomain(Download)

        def domainControl = mockFor(Download)
        domainControl.demand.static.withCriteria() { Closure c ->
            return [ new Download(softwareName: "Grails", softwareVersion: "2.0.2") ]
        }
        domainControl.demand.static.withCriteria() { Closure c ->
            return [ new Download(softwareName: "Grails", softwareVersion: "1.3.4") ]
        }

        controller.grailsApplication = [config: [download: [versions: ["2.0", "1.3"]]]]
        controller.downloadCache = new Expando()
        controller.downloadCache.get = { String name -> delegate."${name}" }
        controller.downloadCache.put = { element -> delegate."${element.key}" = element.value }

        def model = this.controller.latest()
        
        def stableDownloads = model["stableDownloads"]
        assertEquals 2, stableDownloads.size()
        assertEquals([ "2.0", "1.3" ], stableDownloads.keySet() as List)
        assertEquals "2.0.2", stableDownloads["2.0"].softwareVersion
        assertEquals "1.3.4", stableDownloads["1.3"].softwareVersion
    }
}
