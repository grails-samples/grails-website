package org.grails.plugin

import org.springframework.beans.factory.InitializingBean
import twitter4j.TwitterFactory
import twitter4j.http.AccessToken

/**
 * Service that uses Twitter4j to post messages to a single account. This
 * class requires both consumer and access keys/tokens to be defined in 
 * the application config. It's best to keep these in external config files:
 * <ul>
 * <li>twitter4j.oauth.consumerKey</li>
 * <li>twitter4j.oauth.consumerSecret</li>
 * <li>twitter4j.oauth.accessKey</li>
 * <li>twitter4j.oauth.accessSecret</li>
 * </ul>
 */
class TwitterService implements InitializingBean {
    def grailsApplication
    def twitter

    void afterPropertiesSet() {
        def factory = new TwitterFactory()
        def oauthConfig = grailsApplication.config.twitter4j.oauth

        // Validate configuration.
        assert oauthConfig.consumerKey : "twitter4j.oauth.consumerKey must be configured"
        assert oauthConfig.consumerSecret : "twitter4j.oauth.consumerSecret must be configured"
        assert oauthConfig.accessKey : "twitter4j.oauth.accessKey must be configured"
        assert oauthConfig.accessSecret : "twitter4j.oauth.accessSecret must be configured"

        twitter = factory.getOAuthAuthorizedInstance(
                oauthConfig.consumerKey,
                oauthConfig.consumerSecret,
                new AccessToken(oauthConfig.accessKey, oauthConfig.accessSecret))
    }

    /**
     * Post a Twitter status update for the configured user account.
     */
    def updateStatus(msg) {
        twitter.updateStatus(msg)
    }
}
