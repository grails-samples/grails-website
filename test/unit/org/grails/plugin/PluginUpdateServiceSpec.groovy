package org.grails.plugin

import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.plugin.spock.UnitSpec
import grails.test.mixin.*
import org.grails.plugin.*
import org.grails.auth.*
import org.grails.wiki.*
import spock.lang.*
import grails.plugin.searchable.*
import org.grails.content.*
import grails.plugin.mail.*

@TestFor(PluginUpdateService)
@Mock([Plugin, PluginTab, PluginRelease, User, Version, PluginService])
class PluginUpdateServiceSpec extends Specification {

    void setup() {
        service.twitterLimit = 80
    }


    def "Test plugin update event processing"() {
	given:"A plugin update event"
		def u = new User(email:"foo@bar.com", login:"admin", password:"bar").save(flush:true)
		TwitterService twitter = Mock()
		ShortenService shorten = Mock()
		shorten.shortenUrl(_) >> "http://grai.ls/abcd"
		SearchableService searchableService = Mock()
		MailService mailService = Mock()
		GrailsApplication app = Mock()
		app.getConfig() >> new ConfigObject()
		service.twitterService = twitter
		service.shortenService = shorten 
		service.grailsApplication = app
		def wikiPageService = new WikiPageService(searchableService:searchableService)
		service.pluginService = new PluginService(grailsApplication:app, wikiPageService: wikiPageService, searchableService: searchableService )
		service.wikiPageService = wikiPageService
		service.mailService = mailService
		def event = new PluginUpdateEvent(this,"tomcat","1.0.0", "org.grails.plugins", false, new URI("http://repo.grails.org/grails/plugins/") )
		URL.metaClass.withReader = { String encoding, Closure callable ->
			def f = delegate.toString().endsWith("pom") ? "tomcat.pom" : "tomcat-plugin.xml"

			PluginUpdateServiceSpec.getResource(f).openConnection().getInputStream().withReader(encoding, callable)
		}
		Plugin.metaClass.index = {->} // mocked
	when:"The event is processed"
		service.onApplicationEvent(event)

	then:"The plugin is correctly updated"
		Plugin.count() == 1
		PluginRelease.count() == 1		

	when:"The model is queried"
		def p = Plugin.findByName("tomcat")
		
	then:"The plugin data is correctly populated"
		p.name == "tomcat"
		p.currentRelease == "1.0.0"
		p.releases.size() == 1
		p.releases.iterator().next().releaseVersion == "1.0.0"
    } 

    def "Tweeting releases"() {
        given:
        TwitterService twitter = Mock()
        ShortenService shorten = Mock()
        GrailsApplication app = Mock()
        shorten.shortenUrl(_) >> "http://grai.ls/abcd"
        app.config >> [grails: [serverURL: "http://localhost:8080"]]

        service.twitterService = twitter
        service.shortenService = shorten
        service.grailsApplication = app

        when:
        service.tweetRelease(plugin, plugin.currentRelease, plugin.url)
        then:
        1 * twitter.updateStatus(expected)

        where:
        plugin                                        |  expected
        [name: 'shiro',
         title: "Shiro Plugin",
         currentRelease: "1.1.2",
         url:"http://localhost:8080/plugin/shiro"]        | "Shiro Plugin 1.1.2 released: http://localhost:8080/plugin/shiro"
        [name: 'apache-shiro-integration',
         title: "Apache Shiro Integration",
         currentRelease: "1.1.2",
         url:"http://grai.ls/abcd"]        | "Apache Shiro Integration 1.1.2 released: http://grai.ls/abcd"
        [name: 'apache-shiro-integration',
         title: "Apache Shiro Integration for Grails plugin",
         currentRelease: "1.1.2-alpha1",
         url:"http://grails.org/plugin/shiro"]        | "Apache Shiro Integration for...ugin 1.1.2-alpha1 released: http://grai.ls/abcd"
    }
}
