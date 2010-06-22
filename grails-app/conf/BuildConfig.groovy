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

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        mavenCentral()
        mavenRepo "http://repository.codehaus.org"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        // uncomment to enable ehcache
    }

}
