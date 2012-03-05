package org.grails.downloads

import grails.test.ControllerUnitTestCase
import org.grails.downloads.Download
import grails.test.mixin.*

@TestFor(DownloadController)
@Mock([Download, VersionOrder])
class DownloadControllerUnitTests {
    void testLatest() {

       assert new VersionOrder(baseVersion:"2.0",orderIndex:1).save()
       assert new VersionOrder(baseVersion:"1.3", orderIndex:2).save()
       new Download(softwareName: "Grails", softwareVersion: "2.0.2").save() 
       new Download(softwareName: "Grails", softwareVersion: "1.3.4").save(flush:true) 
        
        controller.metaClass.fetchMode = { String name, type ->
            // ignore
        }
        controller.grailsApplication = [config: [download: [versions: ["2.0", "1.3"]]]]
        controller.downloadCache = new Expando()
        controller.downloadCache.get = { String name -> delegate."${name}" }
        controller.downloadCache.put = { element -> delegate."${element.key}" = element.value }

        this.controller.latest()
        
        def stableDownloads = model["stableDownloads"]
        assertEquals 2, stableDownloads.size()
        assertEquals([ "2.0", "1.3" ], stableDownloads.keySet() as List)
        assert "2.0.2" == stableDownloads["2.0"][0].softwareVersion
        assert "1.3.4" == stableDownloads["1.3"][0].softwareVersion
    }
}
