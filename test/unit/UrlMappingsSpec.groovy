import spock.lang.*

@TestFor(UrlMappings)
class UrlMappingsSpec extends Specification {
    def "Grails plugin repository URLs"() {
        expect:
        assertForwardUrlMapping "/plugins/grails-$plugin/tags/RELEASE_${version.replaceAll('\\.', '_')}/grails-$plugin-$version.${type}",
                controller: 'repository', action: 'list'
                [plugin: plugin, version: version, fullName: "$plugin-$version", type: type]

        where:
         plugin   |   version   |  type
        'shiro'   |   '1.0.2'   |  'zip'
        'spring-security-core'   |   '1.2.BUILD-SNAPSHOT'   |  'zip'
    }
}
