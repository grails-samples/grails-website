package org.grails.plugin

import grails.plugin.spock.IntegrationSpec

import spock.lang.Ignore
import spock.lang.Specification
import org.joda.time.DateTime
/**
 * Integration tests for {@link PluginService}.
 */
class PluginServiceSpec extends IntegrationSpec {
    PluginService testService = new PluginService()
    
    def setup() {
        def plugins = []
        def now = new Date()
        plugins << Plugin.build(
                name: "gwt",
                currentRelease: "0.6",
                lastReleased: new DateTime( now.updated(year: 2007, month: 5, date: 12).time ) )
        plugins << Plugin.build(
                name: "shiro",
                currentRelease: "1.1-SNAPSHOT",
                lastReleased: new DateTime( now.updated(year: 2007, month: 3, date: 5).time ),
                featured: true)
        plugins << Plugin.build(
                name: "spring-security-core",
                currentRelease: "1.0.1", 
                lastReleased: new DateTime( now.updated(year: 2010, month: 4, date: 22).time ),
                official: true)
        plugins << Plugin.build(
                name: "fixtures",
                currentRelease: "1.0",
                lastReleased: new DateTime( now.updated(year: 2009, month: 11, date: 13).time ))
        plugins << Plugin.build(
                name: "build-test-data",
                currentRelease: "1.0.1",
                lastReleased: new DateTime( now.updated(year: 2009, month: 9, date: 12).time ))
        plugins << Plugin.build(
                name: "spock",
                currentRelease: "0.5-SNAPSHOT",
                lastReleased: new DateTime( now.updated(year: 2010, month: 4, date: 5).time ),
                featured: true)
        plugins << Plugin.build(
                name: "grails-ui",
                currentRelease: "0.1.1",
                lastReleased: new DateTime( now.updated(year: 2009, month: 1, date: 30).time ),
                official: true)
        plugins << Plugin.build(
                name: "commentable",
                currentRelease: "0.6.2",
                lastReleased: new DateTime( now.updated(year: 2008, month: 9, date: 12).time ))
        plugins << Plugin.build(
                name: "rateable",
                currentRelease: "0.7.5",
                lastReleased: new DateTime( now.updated(year: 2008, month: 9, date: 13).time ))
        plugins << Plugin.build(
                name: "taggable",
                currentRelease: "0.3.5-SNAPSHOT",
                lastReleased: new DateTime( now.updated(year: 2008, month: 8, date: 13).time ))
        plugins << Plugin.build(
                name: "rendering",
                currentRelease: "1.0",
                lastReleased: new DateTime( now.updated(year: 2010, month: 7, date: 27).time ),
                featured: true)
        plugins << Plugin.build(
                name: "hibernate",
                currentRelease: "1.3.4",
                lastReleased: new DateTime( now.updated(year: 2007, month: 12, date: 3).time ),
                featured: true,
                official: true)
        plugins << Plugin.build(
                name: "tomcat",
                currentRelease: "1.3.4",
                lastReleased: new DateTime( now.updated(year: 2009, month: 10, date: 1).time ),
                official: true)
        plugins << Plugin.build(
                name: "webflow",
                currentRelease: "1.0-SNAPSHOT",
                lastReleased: new DateTime( now.updated(year: 2009, month: 2, date: 15).time ),
                official: true)

        // The 'dateCreated' field is automatically initialised when a
        // Plugin instance is created, but this does not lead to reliable
        // tests. So, we modify the 'dateCreated' values now, ensuring
        // that the 'newest' plugin is the last one declared above.
        int i = 20
        for (p in plugins) {
            p.dateCreated = now - i
            i--
        }
    }
    
    def "Test listing all plugins with total"() {
        when:
        def (plugins, count) = testService.listAllPluginsWithTotal()
        
        then:
        plugins.size() == 14
        count == 14
    }
    
    def "Test listing all plugins in alphabetical order"() {
        when:
        def (plugins, count) = testService.listAllPluginsWithTotal(sort: "name")
        
        then:
        plugins.size() == 14
        plugins[0].name == "build-test-data"
        plugins[1].name == "commentable"
        plugins[-1].name == "webflow"
        count == 14
    }
    
    def "Test all plugins listed with offset and maximum results"() {
        when:
        def (plugins, count) = testService.listAllPluginsWithTotal(sort: "name", offset: 2, max: 6)
        
        then:
        plugins.size() == 6
        plugins[0].name == "fixtures"
        plugins[1].name == "grails-ui"
        plugins[5].name == "rendering"
        count == 14
    }
    
    def "Test listing featured plugins with total"() {
        when:
        def (plugins, count) = testService.listFeaturedPluginsWithTotal()
        
        then:
        plugins*.name.sort() == [ "hibernate", "rendering", "shiro", "spock" ]
        count == 4
    }
    
    def "Test listing featured plugins sorted by name with total"() {
        when:
        def (plugins, count) = testService.listFeaturedPluginsWithTotal(sort: "name")
        
        then:
        plugins*.name == [ "hibernate", "rendering", "shiro", "spock" ]
        count == 4
    }
    
    def "Test listing featured plugins with offset, limit and total"() {
        when:
        def (plugins, count) = testService.listFeaturedPluginsWithTotal(sort: "name", offset: offset, max: max)
        
        then:
        plugins*.name == expected
        count == 4

        where:
        offset | max | expected
        1      |  2  | [ "rendering", "shiro" ]
        5      |  3  | []
        0      |  10 | [ "hibernate", "rendering", "shiro", "spock" ]
    }
    
    def "Test listing newest plugins with total"() {
        when:
        def (plugins, count) = testService.listNewestPluginsWithTotal()
        
        then:
        plugins.size() == 14
        plugins[0].name == "webflow"
        plugins[1].name == "tomcat"
        plugins[-1].name == "gwt"
        count == 14
    }
    
    def "Test listing newest plugins sorted by name with total"() {
        when: "a sort argument is provided"
        def (plugins, count) = testService.listNewestPluginsWithTotal(sort: "name")
        println plugins*.dateCreated
        
        then: "the argument should be ignored"
        plugins.size() == 14
        plugins[0].name == "webflow"
        plugins[1].name == "tomcat"
        plugins[-1].name == "gwt"
        count == 14
    }
    
    def "Test listing newest plugins with offset, limit and total"() {
        when:
        def (plugins, count) = testService.listNewestPluginsWithTotal(offset: 4, max: 6)
        
        then:
        plugins.size() == 6
        plugins[0].name == "taggable"
        plugins[1].name == "rateable"
        plugins[-1].name == "build-test-data"
        count == 14
    }
    
    /**
     * Need to work out whether to add data for ratings or mock the
     * corresponding query method.
     */
    @Ignore
    def "Test listing popular plugins with total"() {
        when:
        def (plugins, count) = testService.listPopularPluginsWithTotal()
        
        then:
        plugins*.name.sort() == [ "hibernate", "rendering", "shiro", "spock" ]
        count == 4
    }
    
    /**
     * Need to work out whether to add data for ratings or mock the
     * corresponding query method.
     */
    @Ignore
    def "Test listing popular plugins sorted by name with total"() {
        when:
        def (plugins, count) = testService.listPopularPluginsWithTotal(sort: "name")
        
        then:
        plugins*.name == [ "hibernate", "rendering", "shiro", "spock" ]
        count == 4
    }
    
    /**
     * Need to work out whether to add data for ratings or mock the
     * corresponding query method.
     */
    @Ignore
    def "Test listing popular plugins with offset, limit and total"() {
        when:
        def (plugins, count) = testService.listPopularPluginsWithTotal(sort: "name", offset: 1, max: 2)
        
        then:
        plugins*.name == [ "rendering", "shiro" ]
        count == 4
    }
    
    def "Test listing recently updated plugins with total"() {
        when:
        def (plugins, count) = testService.listRecentlyUpdatedPluginsWithTotal()
        
        then:
        plugins.size() == 14
        plugins[0].name == "rendering"
        plugins[1].name == "spring-security-core"
        plugins[-1].name == "shiro"
        count == 14
    }
    
    def "Test listing recently updated plugins sorted by name with total"() {
        when: "a sort argument is provided"
        def (plugins, count) = testService.listRecentlyUpdatedPluginsWithTotal(sort: "name")
        
        then: "the argument should be ignored"
        plugins.size() == 14
        plugins[0].name == "rendering"
        plugins[1].name == "spring-security-core"
        plugins[-1].name == "shiro"
        count == 14
    }
    
    def "Test listing recently updated plugins with offset, limit and total"() {
        when:
        def (plugins, count) = testService.listRecentlyUpdatedPluginsWithTotal(offset: 4, max: 6)
        
        then:
        plugins.size() == 6
        plugins[0].name == "tomcat"
        plugins[1].name == "build-test-data"
        plugins[-1].name == "commentable"
        count == 14
    }
    
    def "Test listing supported plugins with total"() {
        when:
        def (plugins, count) = testService.listSupportedPluginsWithTotal()
        
        then:
        plugins*.name.sort() == [ "grails-ui", "hibernate", "spring-security-core", "tomcat", "webflow" ]
        count == 5
    }
    
    def "Test listing supported plugins sorted by name with total"() {
        when:
        def (plugins, count) = testService.listSupportedPluginsWithTotal(sort: "name")
        
        then:
        plugins*.name == [ "grails-ui", "hibernate", "spring-security-core", "tomcat", "webflow" ]
        count == 5
    }
    
    def "Test listing supported plugins with offset, limit and total"() {
        when:
        def (plugins, count) = testService.listSupportedPluginsWithTotal(sort: "name", offset: offset, max: max)
        
        then:
        plugins*.name == expected 
        count == 5

        where:
        offset | max | expected
        1      |  2  | [ "hibernate", "spring-security-core" ]
        5      |  3  | []
        0      |  10 | [ "grails-ui", "hibernate", "spring-security-core", "tomcat", "webflow" ]
    }

    @Ignore
    def "Test master plugin list is generated correctly"() {
        when:
        def plugins = testService.generateMasterPlugins()

        then:
        plugins != null
        plugins.size() > 100
    }
}
