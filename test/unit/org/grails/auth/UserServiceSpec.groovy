package org.grails.auth

import grails.plugin.spock.*
import org.apache.commons.codec.digest.DigestUtils
import org.grails.meta.UserInfo
import spock.lang.*

@TestFor(UserService)
@Mock([User, Role, UserInfo])
class UserServiceSpec extends spock.lang.Specification {

    def setup() {
        new Role(name: Role.EDITOR).save()
        new Role(name: Role.OBSERVER).save()
        new Role(name: Role.ADMINISTRATOR).save(flush: true)
    }

    def "Creating a user with standard roles"() {
        given: "A user service and a set of standard roles"
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

    def "Saving new user info"() {
        given: "A user service and some new user info"
        def userService = new UserService()
        def expectedName = "Peter"
        def expectedEmail = "joe.bloggs@somewhere.net"
        def userInfo = new UserInfo(name: expectedName, email: expectedEmail)

        when: "New user info is saved"
        def retInfo = userService.saveUserInfo(userInfo)

        then: "The user info can be queried"
        def dbInfo = UserInfo.findByNameAndEmail(expectedName, expectedEmail)
        dbInfo?.name == expectedName
        dbInfo?.email == expectedEmail

        and: "the returned info is the same as the one queried for"
        retInfo == dbInfo
    }

    def "Saving new user info for existing user"() {
        given: "A user service, an existing user, and some new user info"
        def userService = new UserService()
        def expectedName = "Joe Bloggs"
        def expectedEmail = "joe.bloggs@somewhere.net"
        def user = new User(login: "joe123", password: "pass", email: "old.joe@nowhere.net").save(failOnError: true)
        def userInfo = new UserInfo(name: expectedName, email: expectedEmail)

        when: "New user info is saved with the given user"
        def retInfo = userService.saveUserInfo(userInfo, user)

        then: "The user info can be queried"
        def dbInfo = UserInfo.findByNameAndEmail(expectedName, expectedEmail)
        dbInfo?.name == expectedName
        dbInfo?.email == expectedEmail

        and: "the user has been attached and its email address updated"
        dbInfo?.user == user
        user.email == expectedEmail
    }
}
