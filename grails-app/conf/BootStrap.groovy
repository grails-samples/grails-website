import grails.util.Environment
import javax.servlet.http.HttpServletRequest

import org.apache.commons.codec.digest.DigestUtils
import org.grails.*
import org.grails.auth.Role
import org.grails.auth.User
import org.grails.content.Version
import org.grails.plugin.Plugin

class BootStrap {
    def fixtureLoader
    def searchableService

    def init = { servletContext ->
        HttpServletRequest.metaClass.isXhr = {->
            'XMLHttpRequest' == delegate.getHeader('X-Requested-With')                
        }
        
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
                assert admin.addToRoles(name:Role.ADMINISTRATOR)
                           .addToRoles(name:Role.EDITOR)
                           .addToRoles(name:Role.OBSERVER)
                           .save(flush:true)
            }
        }
        else if (!admin.roles) {
            admin.addToRoles(name:Role.ADMINISTRATOR)
                 .addToRoles(name:Role.EDITOR)
                 .addToRoles(name:Role.OBSERVER)
                 .save(flush:true)
        }
        
        updatePluginTabs admin
        
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
        println "Performing bulk index"
        searchableService.reindex()
        println "Starting mirror service"
        searchableService.startMirroring()
    }

    def destroy = {
    }
    
    private updatePluginTabs(adminUser) {
        // Clean up some excess records.
        Plugin.withTransaction {
            for (Plugin p in Plugin.list()) {
                if (!p.installation.versions?.size()) {
                    def current = Version.findByTitle(p.installation.title)?.current
                    if (current == null) {
                        println "Creating version for installation tab of plugin '${p.name}'"
                        
                        def v = p.installation.createVersion()
                        v.author = adminUser
                        v.save(failOnError: true)
                    }
                    else {
                        p.installation = current
                    }
                }
                if (!p.description.versions?.size()) {
                    def current = Version.findByTitle(p.description.title)?.current
                    if (current == null) {
                        println "Creating version for description tab of plugin '${p.name}'"
                        
                        def v = p.description.createVersion()
                        v.author = adminUser
                        v.save(failOnError: true)
                    }
                    else {
                        p.description = current
                    }
                }
                if (!p.faq.versions?.size()) {
                    def current = Version.findByTitle(p.faq.title)?.current
                    if (current == null) {
                        println "Creating version for faq tab of plugin '${p.name}'"
                        
                        def v = p.faq.createVersion()
                        v.author = adminUser
                        v.save(failOnError: true)
                    }
                    else {
                        p.faq = current
                    }
                }
                if (!p.screenshots.versions?.size()) {
                    def current = Version.findByTitle(p.screenshots.title)?.current
                    if (current == null) {
                        println "Creating version for screenshots tab of plugin '${p.name}'"
                        
                        def v = p.screenshots.createVersion()
                        v.author = adminUser
                        v.save(failOnError: true)
                    }
                    else {
                        p.screenshots = current
                    }
                }
                
                p.save()
            }
        }
    }
}
