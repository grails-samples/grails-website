package org.grails.auth

import spock.lang.*
import grails.test.mixin.*
@TestFor(UserService)
@Mock([User, Role])
class UserServiceSpec extends Specification {
    def "Fetching the permissions for a user"() {
        given:
        new User(
                login: "geoff",
                permissions: ["plugin:publish:tomcat", "plugin:view"],
                roles: [
                        new Role(name: "editor", permissions: ["wiki:view,edit"]),
                        new Role(name: "guest", permissions: ["wiki:view"])
                ],
                ).save(validate: false)
        new User(
                login: "andy",
                permission: ["plugin:view", "plugin:update"],
                roles: [new Role(name: "observer", permissions: ["wiki:view", "news:view"])],
                ).save(validate: false)
        def userService = new UserService()

        when:
        def permissions = userService.permissionsForUser(User.findByLogin("geoff"))

        then:
        permissions.size() == 4
        permissions.contains "plugin:publish:tomcat"
        permissions.contains "plugin:view"
        permissions.contains "wiki:view,edit"
        permissions.contains "wiki:view"
    }

    def "Fetching the permissions for a user who has none"() {
        given:
        new User(login: "geoff").save(validate: false)
        new User(
                login: "andy",
                permission: ["plugin:view", "plugin:update"],
                roles: [new Role(name: "observer", permissions: ["wiki:view", "news:view"])],
                ).save(validate: false)
        def userService = new UserService()

        when:
        def permissions = userService.permissionsForUser(User.findByLogin("geoff"))

        then:
        permissions.size() == 0
    }
}
