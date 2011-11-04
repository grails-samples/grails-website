import grails.util.Environment
import javax.servlet.http.HttpServletRequest

import org.apache.commons.codec.digest.DigestUtils
import org.grails.*
import org.grails.auth.Role
import org.grails.auth.User
import org.grails.content.Version
import org.grails.downloads.Mirror
import org.grails.plugin.Plugin

class BootStrap {
    def fixtureLoader
    def searchableService

    def init = { servletContext ->
        HttpServletRequest.metaClass.isXhr = {->
            'XMLHttpRequest' == delegate.getHeader('X-Requested-With')                
        }

        def (adminRole, editorRole, observerRole) = setUpRoles()
        
        def admin = User.findByLogin("admin")
        if (!admin) {
            def password = Environment.current != Environment.PRODUCTION ? "changeit" : System.getProperty("initial.admin.password")
            if (!password) {
                throw new Exception("""
During the first run you must specify a password to use for the admin account. For example:

grails -Dinitial.admin.password=changeit run-app""")
            }
            else {
                admin = new User(login:"admin", email:"info@g2one.com",password:DigestUtils.shaHex(password))
                assert admin.email
                assert admin.addToRoles(adminRole)
                           .addToRoles(editorRole)
                           .addToRoles(observerRole)
                           .save(flush:true, failOnError: true)
            }
        }
        else if (!admin.roles) {
            admin.addToRoles(adminRole)
                 .addToRoles(editorRole)
                 .addToRoles(observerRole)
                 .save(flush:true, failOnError: true)
        }
        
        // Load dev data to make it easier to work on the application.
        if (Environment.current == Environment.DEVELOPMENT && User.count() < 2) {
            println "Loading fixture data"
            fixtureLoader.with {
                load("users").load("plugins").load("tags", "ratings")
                load("wiki")
                load("downloads")
            }
        }
        
        // We manually start the mirroring process to ensure that it comes after
        // Autobase performs its migrations.
        if (Environment.current != Environment.DEVELOPMENT || System.getProperty("reindex")) {
            println "Performing bulk index"
            searchableService.reindex()
        }
        println "Starting mirror service"
        searchableService.startMirroring()
    }

    def destroy = {
    }

    private setUpRoles() {
        // Admin role first. Adminstrator can access all parts of the application.
        def admin = Role.findByName(Role.ADMINISTRATOR) ?: new Role(name: Role.ADMINISTRATOR).save(failOnError: true)
        safelyAddPermission admin, "*"

        // Editor can edit pages, add screencasts, etc.
        def editor = Role.findByName(Role.EDITOR) ?: new Role(name: Role.EDITOR).save(failOnError: true)
        safelyAddPermission editor, "pluginTab:editWikiPage"

        // Observer: can't do anything that an anonymous user can't do.
        def observer = Role.findByName(Role.OBSERVER) ?: new Role(name: Role.OBSERVER).save(failOnError: true)

        return [admin, editor, observer]
    }

    private safelyAddPermission(entity, String permission) {
        if (!entity.permissions?.contains(permission)) {
            entity.addToPermissions permission
        }
    }
}
