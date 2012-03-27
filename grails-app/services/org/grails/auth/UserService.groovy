package org.grails.auth

import grails.plugin.springcache.annotations.Cacheable

class UserService {
    @Cacheable("permissions")
    def permissionsForUser(principal) {
        def user = User.findByLogin(principal)
        return (user.permissions ?: []) + (user.roles*.permissions?.flatten() ?: []).unique()
    }
}
