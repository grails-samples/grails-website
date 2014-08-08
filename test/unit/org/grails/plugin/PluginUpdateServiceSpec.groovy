package org.grails.plugin

import grails.plugin.mail.*
import grails.plugin.searchable.*
import grails.test.mixin.*
import grails.test.mixin.web.ControllerUnitTestMixin

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.auth.*
import org.grails.content.*
import org.grails.meta.UserInfo
import org.grails.wiki.*

import spock.lang.Specification


@TestFor(PluginUpdateService)
@TestMixin(ControllerUnitTestMixin)
@Mock([Plugin, PluginTab, PluginRelease, UserInfo, User, Version, PluginService, PendingRelease])
class PluginUpdateServiceSpec extends Specification {

    void setup() {
        service.twitterLimit = 80
        config.grails.databinding.convertEmptyStringsToNull=false
        applicationContext.grailsWebDataBinder.convertEmptyStringsToNull=false
    }

    def "Test that updating authors from POM data works correctly"() {
        given:"A plugin updater and a POM"
            def updater = new PluginUpdater("1.0", "com.mycompany", "http://foobar.com", false)
            updater.@plugin = new Plugin()
            def xml = new XmlSlurper().parseText("""\
<?xml version='1.0' encoding='UTF-8'?>
<project xmlns='http://maven.apache.org/POM/4.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd'>
  <developers>
    <developer>
      <name>Graeme Rocher</name>
      <email>123@springsource.com</email>
    </developer>
    <developer>
      <name>Graeme Rocher</name>
      <email>123@vmware.com</email>
    </developer>
  </developers>
</project>
                """)

        when:"The authors are updated from the POM xml"
            mockDomain(org.grails.meta.UserInfo)
            updater.addAuthors(xml.developers)

        then:"The authors are populated correctly"
            updater.@plugin.authors.size() == 1
            updater.@plugin.authors[0].name == "Graeme Rocher"
            updater.@plugin.authors[0].email == "123@springsource.com"
    }

    def "Test plugin update event processing"() {
	given:"A plugin update event"
		def u = new User(email:"foo@bar.com", login:"admin", password:"bar").save(flush:true)
		TwitterService twitter = Mock()
		ShortenService shorten = Mock()
		shorten.shortenUrl(_) >> "http://grai.ls/abcd"
		SearchableService searchableService = Mock()
		MailService mailService = Mock()
		GrailsApplication app = grailsApplication
		service.twitterService = twitter
		service.shortenService = shorten 
		service.grailsApplication = app
		def wikiPageService = new WikiPageService(searchableService:searchableService)
		service.pluginService = new PluginService(grailsApplication:app, wikiPageService: wikiPageService, searchableService: searchableService )
		service.mailService = mailService
		def event = new PluginUpdateEvent(this,"tomcat","1.0.0", "org.grails.plugins", false, new URI("https://grails.artifactoryonline.com/grails/plugins/") )
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
		def releases = PluginRelease.list()
		
	then:"The plugin data is correctly populated"
		p.name == "tomcat"
		p.currentRelease == "1.0.0"
		releases.size() == 1
		releases.iterator().next().releaseVersion == "1.0.0"
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
