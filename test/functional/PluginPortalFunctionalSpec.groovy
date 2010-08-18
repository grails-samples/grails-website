import org.grails.test.pages.PluginHomePage;

import grails.plugin.geb.GebSpec

class PluginPortalFunctionalSpec extends GebSpec {
    def "Test plugin portal home page"() {
        when: "we go to the plugin portal home page"
        to PluginHomePage
        
        then:
        at PluginHomePage
    }
}
