package org.grails.auth

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authc.DisabledAccountException

class WikiRealm {
    static authTokenClass = org.apache.shiro.authc.UsernamePasswordToken

    def credentialMatcher
    def shiroPermissionResolver
    def userService

    def authenticate(authToken) {
        log.info "Attempting to authenticate ${authToken.username} in DB realm..."
        def username = authToken.username

        // Null username is invalid
        if (username == null) {
            throw new AccountException('Null usernames are not allowed by this realm.')
        }

        // Get the user with the given username. If the user is not
        // found, then they don't have an account and we throw an
        // exception.
        def user = User.findByLogin(username)
        log.info "Found user '${user?.login}' in DB"
        if (!user) {
            throw new UnknownAccountException("No account found for user [${username}]")
        }
        if(!user.enabled) {
            throw new DisabledAccountException("Account for user [$username] has been disabled")
        }

        // Now check the user's password against the hashed value stored
        // in the database.
        def account = new SimpleAccount([user.id, username], user.password, "WikiRealm")
        if (!credentialMatcher.doCredentialsMatch(authToken, account)) {
            log.info 'Invalid password (DB realm)'
            throw new IncorrectCredentialsException("Invalid password for user '${username}'")
        }

        return account
    }

    def hasRole(principal, roleName) {
        def user = getUserFromPrincipal(principal)

        return null != user?.roles?.find { it.name == roleName }
    }

    def hasAllRoles(principal, roles) {
        def user = getUserFromPrincipal(principal)
        if(user == null) return false
        else {
            return roles.size() == roles.intersect(user.roles).size()            
        }
    }

    boolean isPermitted(principal, requiredPermission) {
        def permissions = userService.permissionsForUser(getUserFromPrincipal(principal))
        def retval = permissions?.find { permString ->
            // Create a real permission instance from the database
            // permission.
            def perm = shiroPermissionResolver.resolvePermission(permString)

            // Now check whether this permission implies the required
            // one.
            if (perm.implies(requiredPermission)) {
                // User has the permission!
                return true
            }
            else {
                return false
            }
        }

        return retval != null
    }

    protected final getUserFromPrincipal(principal) {
        if (principal instanceof Number) return User.get(principal)
        else return User.findByLogin(principal)
    }
}
