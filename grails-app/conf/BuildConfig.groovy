grails.project.work.dir = "target"
grails.project.test.reports.dir = "target/test-reports"
grails.project.plugins.dir = "plugins"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" )
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {        
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenRepo "http://repository.codehaus.org"
    }
    plugins {
        runtime ":autobase:0.8.5",
                ":avatar:0.3",
                ":bubbling:2.1.2",
                ":commentable:0.7.4",
                ":feeds:1.5",
                ":grails-ui:1.2-SNAPSHOT",
                ":hibernate:1.3.3",
                ":mail:0.5",
                ":pretty-time:0.3",
                ":quartz:0.4.1-SNAPSHOT",
                ":rateable:0.6.2",
                ":richui:0.6",
                ":screencasts:0.4",
                ":searchable:0.5.5",
                ":shiro:1.1-SNAPSHOT",
                ":simple-blog:0.1.3",
                ":springcache:1.2",
                ":taggable:0.6.1",
                ":yui:2.7.0.1"

        build   ":db-util:0.4",
                ":tomcat:1.3.3"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        runtime "net.sf.ehcache:ehcache:1.6.1", {
            excludes 'jms', 'commons-logging', 'servlet-api'
        }
    }

}
