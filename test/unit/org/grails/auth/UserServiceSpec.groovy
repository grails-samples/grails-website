package org.grails.auth

import grails.plugin.spock.*
import org.apache.commons.codec.digest.DigestUtils
import spock.lang.*

@TestFor(UserService)
@Mock([User, Role])
class UserServiceSpec extends spock.lang.Specification {

    def setup() {
        new Role(name: Role.EDITOR).save()
        new Role(name: Role.OBSERVER).save()
        new Role(name: Role.ADMINISTRATOR).save(flush: true)
    }

    def "Creating a user with standard roles"() {
        given: "A user service and as set of standard roles"
        def userService = new UserService()
        def expectedRoles = [Role.EDITOR, Role.OBSERVER]

        when: "I create a standard user"
        def user = userService.createStandardUser("adam", "pass", "a.bloggs@somewhere.net")

        then: "That user has the given username and password"
        user.login == "adam"
        user.email == "a.bloggs@somewhere.net"

        and: "the password is hashed"
        user.password != "pass"
        user.password == DigestUtils.shaHex("pass")

        and: "the user has the standard roles"
        user.roles*.name.intersect(expectedRoles) == expectedRoles
    }
}
