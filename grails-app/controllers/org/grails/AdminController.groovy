package org.grails

import org.grails.content.*
import org.grails.wiki.*

class AdminController {
    def downloadService
    def index(){

        def recentWikiUpdates = WikiPage.list(sort:'lastUpdated', order:'desc', max:10)
        def majorVersions =  downloadService.getDownloadsByMajorVersion()
        def latestDownload = downloadService.getLatestDownload()
        def latestBeta = org.grails.downloads.Download.where {
            betaRelease == true
        }.order("releaseDate", "desc")
         .max(1)
         .find()
        return [majorVersions:majorVersions,latestBeta:latestBeta, latestDownload: latestDownload, recentWikiPages: recentWikiUpdates] + new ContentPendingApprovalController().list()
    }
}
