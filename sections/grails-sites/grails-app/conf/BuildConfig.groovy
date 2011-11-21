grails.plugin.location.like = "../like"
grails.plugin.location.shared = "../shared"

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    inherits "global"
    log "warn"

    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
    }

    plugins {
        compile ":burning-image:0.5.0", ":taggable:1.0"
    }

    dependencies {
    }
}
