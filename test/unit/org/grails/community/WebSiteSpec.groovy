package org.grails.community

import static org.junit.Assert.*
import org.grails.UnitSpecHelper

/**
 * author: Eric Berry
 */

class WebSiteSpec extends UnitSpecHelper {

    void "title constraints"() {
        setup:
        mockDomain(WebSite)

        when:
        def website = new WebSite(title: title, description: "Description goes here", url: "http://google.com")
        website.validate()

        then:
        website.hasErrors() == !valid

        where:
        title     | valid
        "Testing" | true
        ""        | false
        word(51)  | false
        null      | false
    }

    void "description constraints"() {
        setup:
        mockDomain(WebSite)

        when:
        def website = new WebSite(title: "This is a test", description: description, url: "http://google.com")
        website.validate()

        then:
        website.hasErrors() == !valid

        where:
        description | valid
        "Testing"   | true
        ""          | false
        word(5001)  | false
        null        | false
    }

    void "url constraints"() {
        setup:
        mockDomain(WebSite)

        when:
        def website = new WebSite(title: "This is a test", description: "Description goes here", url: url)
        website.validate()

        then:
        website.hasErrors() == !valid

        where:
        url               | valid
        "foo.bar"         | true
        "http://foo.bar"  | true
        "https://foo.bar" | true
        ""                | false
        null              | false
    }

}
