package org.grails.auth

import grails.plugin.springcache.annotations.Cacheable
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.grails.meta.UserInfo

class UserService {
    static transactional = true

    @Cacheable("permissions")
    def permissionsForUser(principal) {
        def user = getUserFromPrincipal(principal)
        return (user.permissions ?: []) + (user.roles*.permissions?.flatten() ?: []).unique()
    }

    /**
     * Creates a new Shiro user in the system if the given information satisfies
     * the constraints (unique username, etc.). Throws an exception if the
     * user can't be created.
     */
    def createUser(login, email, password = null) {
        def user = new User(
                login: login,
                password: password ? DigestUtils.shaHex(password) : "** none **",
                email: email)
                .addToRoles(Role.findByName(Role.EDITOR))
                .addToRoles(Role.findByName(Role.OBSERVER))
                .save(failOnError: true)

        new UserInfo(user: user).save()
        return user
    }

    /**
     * Logs the given user into the application. Returns the ID of the corresponding
     * <tt>User</tt> instance if the credentials are valid, otherwise it returns
     * <code>null</code>.
     */
    Long loginUser(username, password, rememberMe = false) {
        try {
            def subject = SecurityUtils.subject
            subject.login new UsernamePasswordToken(username, password, rememberMe)
            return subject.principals.oneByType(Long)
        }
        catch (AuthenticationException ex){
            log.info "Authentication failure for user '${username}'."
            throw ex
        }
    }

    /**
     * Determines whether the given username is unique within the system.
     * Returns <code>true</code> if it is.
     */
    Boolean isLoginUnique(login) {
        return User.countByLogin(login) == 0
    }

    /**
     * Determines whether the given email address is unique within the system.
     * Returns <code>true</code> if it is.
     */
    Boolean isEmailUnique(email) {
        return User.countByEmail(email) == 0
    }

    final getUserFromPrincipal(principal) {
        if (principal instanceof Number) return User.get(principal)
        else return User.findByLogin(principal)
    }
}
