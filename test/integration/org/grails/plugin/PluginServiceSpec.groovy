package org.grails.plugin

import grails.plugin.spock.IntegrationSpec
import spock.lang.Ignore
import spock.lang.Specification

class PluginServiceSpec extends IntegrationSpec {
    PluginService testService = new PluginService()
    
    def setup() {
        Plugin.build(name: "gwt", currentRelease: "0.6")
        Plugin.build(name: "shiro", currentRelease: "1.1-SNAPSHOT")
        Plugin.build(name: "spring-security-core", currentRelease: "1.0.1")
        Plugin.build(name: "fixtures", currentRelease: "1.0")
        Plugin.build(name: "build-test-data", currentRelease: "1.0.1")
        Plugin.build(name: "spock", currentRelease: "0.5-SNAPSHOT")
        Plugin.build(name: "grails-ui", currentRelease: "0.1.1")
        Plugin.build(name: "commentable", currentRelease: "0.6.2")
        Plugin.build(name: "rateable", currentRelease: "0.7.5")
        Plugin.build(name: "taggable", currentRelease: "0.3.5-SNAPSHOT")
        Plugin.build(name: "rendering", currentRelease: "1.0")
        Plugin.build(name: "hibernate", currentRelease: "1.3.4")
        Plugin.build(name: "tomcat", currentRelease: "1.3.4")
        Plugin.build(name: "webflow", currentRelease: "1.0-SNAPSHOT")
    }
    
    def "Test all plugins listed"() {
        when:
        def plugins = testService.listAllPlugins()
        
        then:
        plugins.size() == 14
    }
    
    def "Test all plugins listed in alphabetical order"() {
        when:
        def plugins = testService.listAllPlugins(sort: "name")
        
        then:
        plugins.size() == 14
        plugins[0].name == "build-test-data"
        plugins[1].name == "commentable"
        plugins[13].name == "webflow"
    }
    
    def "Test all plugins listed with offset and maximum results"() {
        when:
        def plugins = testService.listAllPlugins(sort: "name", offset: 2, max: 6)
        
        then:
        plugins.size() == 6
        plugins[0].name == "fixtures"
        plugins[1].name == "grails-ui"
        plugins[5].name == "rendering"
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
