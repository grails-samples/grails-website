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
        mavenCentral()
        ebr()
    }
    dependencies {
        compile "org.apache.shiro:shiro-core:1.1.0",
                "org.springframework.security:org.springframework.security.core:3.0.4.RELEASE", {
            export = false
            transitive = false
        }
    }
    plugins {
        runtime ":release:1.0.0.RC3", {
            export = false
        }
    }
}
