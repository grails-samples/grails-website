import grails.util.Environment

import static org.compass.core.mapping.rsem.builder.RSEM.*

import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.compass.core.CompassCallback
import org.compass.core.CompassSession
import org.compass.core.CompassTemplate
import org.compass.core.config.CompassConfiguration
import org.grails.*
import org.grails.auth.Role
import org.grails.auth.User
import org.grails.content.Version
import org.grails.downloads.Mirror
import org.grails.plugin.Plugin
import org.jsoup.Jsoup

class BootStrap {
    def fixtureLoader
    def grailsApplication
    def searchableService

    def init = { servletContext ->
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
                    'downloads',
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
            indexGrailsDocs()
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
        safelyAddPermission editor, "webSite:create,edit,save,update"
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

    private indexGrailsDocs() {
        def docsDir = grailsApplication.config.grails.guide.dir
        if (!docsDir) {
            log.warn "Base directory for user guide not configured (grails.guide.dir)"
            return
        }

        docsDir = new File(docsDir)

        if (!docsDir.exists()) {
            log.warn "Base directory for user guide not found at '${docsDir}'"
            return
        }

        log.info "Indexing docs in ${docsDir}"
        final compass = grailsApplication.mainContext.getBean("compass")
        final compassTemplate = new CompassTemplate(compass)
        compassTemplate.execute( { CompassSession session ->
            def resources = []
            // Index all HTML files in 'guide' except single.html and index.html.
            // Also, don't recurse into the 'pages' directory (or any sub-directory
            // for that matter).
            new File(docsDir, "guide").eachFileMatch(~/^.+(?<!(index|single))\.html$/) { f ->
                resources << createCompassResourceFromDocPage(f, "guide", compass.resourceFactory, session)
            }

            new File(docsDir, "ref").traverse(
                    type: FileType.FILES,
                    nameFilter: ~/^.+\.html$/) { f ->
                resources << createCompassResourceFromDocPage(f, "ref/" + f.parentFile.name, compass.resourceFactory, session)
            }

            return resources
        } as CompassCallback )
    }

    private createCompassResourceFromDocPage(file, basePath, resourceFactory, compassSession) {
        def id = file.name - ".html"
        log.info "Indexing chapter '${id}'"

        try {
            def (title, content) = extractMainDocPageContent(file.text)
            def resource = resourceFactory.createResource("doc")
            resource.addProperty("id", id)
            resource.addProperty("title", title)
            resource.addProperty("url", basePath + "/" + file.name)
            resource.addProperty("body", content)
            compassSession.save(resource)

            return resource
        }
        catch (Exception ex) {
            log.error "Problem processing ${file}: ${ex.message}"
            return null
        }
    }

    /**
     * Returns a tuple containing the title and main content (minus reference links)
     * of the given Grails user guide page.
     */
    private extractMainDocPageContent(String pageContent) {
        def mainElement = Jsoup.parse(pageContent).getElementById("main")
        return [mainElement.getElementsByTag("h1").first()?.text(), mainElement.text()]
    }
}
