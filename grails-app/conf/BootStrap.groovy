import grails.util.Environment
import org.apache.commons.codec.digest.DigestUtils
import org.grails.auth.Role
import org.grails.auth.User

import javax.imageio.ImageIO

class BootStrap {
    def fixtureLoader
    def searchableService

    def init = { servletContext ->
        // disable JAI caching which causes problems, http://stackoverflow.com/questions/18517463/jai-create-seems-to-leave-file-descriptors-open
        // JAI is used in burning image plugin for image scaling
        ImageIO.setUseCache(false)
        User.withNewTransaction { doInit() }
    }

    void doInit() {
        def (adminRole, editorRole, observerRole) = setUpRoles()

        def admin = User.findByLogin("admin")
        if (!admin) {
            def password = Environment.current != Environment.PRODUCTION ? "changeit" : System.getProperty("initial.admin.password")
            if (!password) {
                throw new Exception("""
During the first run you must specify a password to use for the admin account. For example:

grails -Dinitial.admin.password=changeit -Dload.fixtures=true prod run-app""")
            }
            else {
                admin = new User(login:"admin", email:"info@g2one.com",password:DigestUtils.shaHex(password))
                admin.save(flush: true, failOnError: true)
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
        if ((System.getProperty("load.fixtures")
            || Environment.current == Environment.DEVELOPMENT
            || Environment.current == Environment.TEST) && User.count() < 2) {
            println "Loading fixture data"
            fixtureLoader.with {
                load("users").load("plugins").load("tags", "ratings")
                [
                    'videohosts',
                    'wiki',
                    'testimonials',
                    'screencasts',
                    'tutorials'
                ].each {
                    load(it)
                }
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
        safelyAddPermission editor, "screencast:create,edit,save,update"
        safelyAddPermission editor, "tutorial:create,edit,save,update"
        safelyAddPermission editor, "likeDislike:like,dislike"

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
