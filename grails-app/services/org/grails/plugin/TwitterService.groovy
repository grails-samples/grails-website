package org.grails.plugin

/**
 * Service that uses Twitter4j to post messages to a single account. This
 * class requires both consumer and access keys/tokens to be defined in 
 * the application config. It's best to keep these in external config files:
 * <ul>
 * <li>twitter.consumerKey</li>
 * <li>twitter.consumerSecret</li>
 * <li>twitter.accessKey</li>
 * <li>twitter.accessSecret</li>
 * </ul>
 */
class TwitterService {
    def twitterApi

    /**
     * Post a Twitter status update for the configured user account.
     */
    def updateStatus(msg) {
        twitterApi.timelineOperations().updateStatus(msg)
    }
}
