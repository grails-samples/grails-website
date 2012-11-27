package org.grails.community

import static org.junit.Assert.*
import org.grails.UnitSpecHelper
import org.grails.auth.*
import grails.test.mixin.*
/**
 * author: Eric Berry
 */
@TestFor(WebSite)
@Mock(User)
class WebSiteSpec extends UnitSpecHelper {

    void "title constraints"() {

        when:
        def user = new User(email:"foo@bar.com", login:"foo", password:"bar").save(flush:true, validate:false)
        def website = new WebSite(title: title, description: "Description goes here", url: "http://google.com", submittedBy:user)
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

        when:
        def user = new User(email:"foo@bar.com", login:"foo", password:"bar").save(flush:true, validate:false)
        def website = new WebSite(title: "This is a test", description: description, url: "http://google.com", submittedBy:user)
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
        def user = new User(email:"foo@bar.com", login:"foo", password:"bar").save(flush:true, validate:false)       
        def website = new WebSite(title: "This is a test", description: "Description goes here", url: url, submittedBy: user)
        website.validate()

        then:
        website.hasErrors() == !valid

        where:
        url                  | valid
        "grails.com"         | false
        "http://grails.com"  | true
        "https://grails.com" | true
        ""                   | false
        null                 | false
    }

}
