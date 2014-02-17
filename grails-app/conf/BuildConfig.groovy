grails.plugin.location.like = "${basedir}/sections/like"

grails.project.work.dir = "target/$grailsVersion"
grails.project.test.reports.dir = "target/test-reports"
grails.project.plugins.dir = "plugins"

grails.project.dependency.resolver="maven"
grails.project.fork = [
   // test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true], // configure settings for the test-app JVM
   run: [maxMemory: 1024, minMemory: 256, debug: false, maxPerm: 256], // configure settings for the run-app JVM
   war: [maxMemory: 1024, minMemory: 256, debug: false, maxPerm: 256], // configure settings for the run-war JVM
   console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]// configure settings for the Console UI JVM
]
grails.project.dependency.resolution = {
    inherits "global", {
        excludes "xml-apis", "commons-digester", "ehcache"
    }

    log "warn"

    repositories {        
        inherit false
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "https://oss.sonatype.org/content/repositories/releases/"
    }

    plugins {
        compile ":burning-image:0.5.1",
                ":joda-time:1.3.1",
                ":taggable:1.0.1"

        compile ":rateable:0.7.1"

        runtime ":avatar:0.3",
                ":rest-client-builder:2.0.1",
                ":cache:1.1.1",                
                ":cache-headers:1.1.5",
                ":cached-resources:1.0",
                ":database-migration:1.3.8",
                ":disqus:0.1",
                ":feeds:1.5",
                ":greenmail:1.2.2",
                ":hibernate:3.6.10.8",
                ":jquery:1.7.2",
                ":jquery-ui:1.8.24",
                ":mail:1.0",
                ":pretty-time:0.3",
                ":quartz:1.0.1",
                ":resources:1.2.1",
                ":searchable:0.6.6",
                ":shiro:1.2.0",                
                ":spring-events:1.2",
                ":zipped-resources:1.0", {
                    exclude 'spring-test'
                }

        runtime ":shiro-oauth:0.2", {
            excludes 'shiro-core'
        }



        if (Environment.current == Environment.DEVELOPMENT) {
            compile ":build-test-data:1.1.1",
                    ":fixtures:1.2"
        }
        else {
            test    ":build-test-data:1.1.1",
                    ":fixtures:1.2"
        }

        compile ":platform-core:1.0.M6"
        runtime ":cache-ehcache:1.0.0", { exclude "cache" }
        

        test ":geb:0.9.2", {
            excludes 'xml-apis'
            exclude "spock-grails-support"
        }

        build   ":tomcat:7.0.50.1"
        compile ":scaffolding:1.0.0"
    }

    dependencies {
        build "org.lesscss:lesscss:1.3.0"

        compile "org.grails:grails-gdoc-engine:1.0.1"
        compile "org.springframework.social:spring-social-twitter:1.0.5.RELEASE",
                "org.springframework:spring-context-support:3.2.7.RELEASE",
                "org.jadira.usertype:usertype.jodatime:1.9",
                "org.jsoup:jsoup:1.6.3"

        compile "org.apache.shiro:shiro-core:1.2.2"
        runtime 'org.apache.lucene:lucene-snowball:2.4.1'

        test "org.gebish:geb-core:0.9.2",
             "org.gmock:gmock:0.8.1"
        test    "org.codehaus.groovy.modules.http-builder:http-builder:0.5.0", {
            excludes "commons-logging", "httpclient", "xml-apis", "groovy"
        }
        test    "org.seleniumhq.selenium:selenium-htmlunit-driver:2.0a7", {
            excludes "htmlunit", "xml-apis"
        }
        test    "net.sourceforge.htmlunit:htmlunit:2.8", {
            excludes "xml-apis", "commons-logging"
        }

        test 'org.gebish:geb-spock:0.9.2'

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
        if (Environment.current == Environment.DEVELOPMENT) {
            runtime "org.grails:grails-test:$grailsVersion"
        }
    }

}

grails.tomcat.jvmArgs = [ '-Xmx512m', '-XX:MaxPermSize=256m','-Dinitial.admin.password=changeit',
        '-Dload.fixtures=1' ]
