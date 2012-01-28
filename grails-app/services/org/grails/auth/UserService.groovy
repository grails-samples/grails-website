package org.grails.auth

import grails.plugin.springcache.annotations.Cacheable

class UserService {
    def createStandardUser(login, password, email) {
        def user = new User(
                login: login,
                email: email)
        user.updatePassword password
        user.addStandardRoles()
        return user
    }

    def saveUserInfo(userInfo, user = null) {
        if (userInfo.validate() && user) {
            userInfo.user = user

            if (!user.save()) return null

            user.email = userInfo.email
        }

        return userInfo.save(flush: true)
    }

    @Cacheable("permissions")
    def permissionsForUser(principal) {
        def user = User.findByLogin(principal)
        return user.roles*.permissions.inject([] as Set) { set, permList -> set.addAll permList; set }
    }
}
