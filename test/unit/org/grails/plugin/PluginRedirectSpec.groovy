package org.grails.plugin

import spock.lang.Specification

class PluginRedirectSpec extends Specification {

    def "test findGrailsThreePluginUrlByPluginId"() {
        when:
        def result = PluginRedirect.findGrailsThreePluginUrlByPluginId(pluginId)

        then:
        result == expected

        where:
        pluginId               | expected
        'bogus'                | null
        'spring-security-core' | 'http://plugins.grails.org/plugin/grails/spring-security-core'
    }
}
