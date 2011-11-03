package org.grails.auth

import grails.plugin.springcache.annotations.Cacheable

class UserService {
    @Cacheable("permissions")
    def permissionsForUser(principal) {
        def user = User.findByLogin(principal)
        return user.roles*.permissions.inject([] as Set) { set, permList -> set.addAll permList; set }
    }
}
