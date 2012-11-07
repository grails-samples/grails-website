package org.grails.downloads

import grails.test.ControllerUnitTestCase
import org.grails.downloads.Download
import grails.test.mixin.*

import static org.junit.Assert.*
@TestFor(DownloadController)
@Mock([Download, VersionOrder])
class DownloadControllerUnitTests {
    void testLatest() {

       assert new VersionOrder(baseVersion:"2.0",orderIndex:1).save()
       assert new VersionOrder(baseVersion:"1.3", orderIndex:2).save()
       new Download(softwareName: "Grails", softwareVersion: "2.0.2")
            .addToFiles(fileType:DownloadFile.FileType.BINARY)
            .save() 
       new Download(softwareName: "Grails", softwareVersion: "1.3.4")
            .addToFiles(fileType:DownloadFile.FileType.BINARY)
            .save(flush:true) 
        
       controller.downloadService = new DownloadService()
        def model = this.controller.index()
        
        def stableDownloads = model["majorVersions"]
        assertEquals 2, stableDownloads.size()
        assertEquals([ "2.0", "1.3" ], stableDownloads)
        assert "2.0.2" == model.groupedDownloads["2.0"][0].download.softwareVersion
        assert "1.3.4" == model.groupedDownloads["1.3"][0].download.softwareVersion
    }
}
