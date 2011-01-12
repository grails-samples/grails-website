package org.grails.screencasts

class ScreencastService {

    boolean transactional = true

    def getLatestScreencastId() {
        def ids = Screencast.withCriteria {
            order 'dateCreated', 'desc'
            maxResults 1
            projections {
                property 'id'
            }
        }
        return ids[0]
    }
}
