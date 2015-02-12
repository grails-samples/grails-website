package org.grails

import org.grails.content.*
import org.grails.wiki.*

class AdminController {
    def index(){

        def recentWikiUpdates = WikiPage.list(sort:'lastUpdated', order:'desc', max:10)
        return [recentWikiPages: recentWikiUpdates] + new ContentPendingApprovalController().list()
    }
}
