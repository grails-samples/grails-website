package org.grails.plugin

import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.plugin.spock.UnitSpec

class PluginUpdateServiceSpec extends UnitSpec {
    def testService

    void setup() {
        mockLogging(PluginUpdateService)
        mockDomain(Plugin)
        testService = new PluginUpdateService()
        testService.twitterLimit = 80
    }

    def "Tweeting releases"() {
        given:
        TwitterService twitter = Mock()
        ShortenService shorten = Mock()
        GrailsApplication app = Mock()
        shorten.shortenUrl(_) >> "http://grai.ls/abcd"
        app.config >> [grails: [serverURL: "http://localhost:8080"]]

        testService.twitterService = twitter
        testService.shortenService = shorten
        testService.grailsApplication = app

        when:
        testService.tweetRelease(plugin)

        then:
        1 * twitter.updateStatus(expected)

        where:
        plugin                           |  expected
        [name: 'shiro',
         title: "Shiro Plugin",
         currentRelease: "1.1.2"]        | "Shiro Plugin 1.1.2 released: http://localhost:8080/plugin/shiro"
        [name: 'apache-shiro-integration',
         title: "Apache Shiro Integration",
         currentRelease: "1.1.2"]        | "Apache Shiro Integration 1.1.2 released: http://grai.ls/abcd"
        [name: 'apache-shiro-integration',
         title: "Apache Shiro Integration for Grails plugin",
         currentRelease: "1.1.2-alpha1"] | "Apache Shiro Integration for...ugin 1.1.2-alpha1 released: http://grai.ls/abcd"
    }
}
