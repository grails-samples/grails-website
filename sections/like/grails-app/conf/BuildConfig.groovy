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
    }
    dependencies {
        compile "org.apache.shiro:shiro-core:1.1.0",
                'org.springframework.security:spring-security-core:3.0.8.RELEASE', {
            export = false
            transitive = false
        }
    }
    plugins {
        runtime ":release:3.0.1", {
            export = false
        }
    }
}
