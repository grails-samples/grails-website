grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    inherits "global"
    log "warn"

    repositories {
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        runtime "org.codehaus.groovy.modules.http-builder:http-builder:0.5.1", {
            excludes "commons-logging", "xml-apis", "groovy", "nekohtml"
        }
    }

    plugins {
        build ":release:1.0.0.RC3", {
            export = false
        }
    }
}
