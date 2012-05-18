import grails.util.Environment

grails.plugin.location.websites = "${basedir}/sections/grails-sites"
grails.plugin.location.tutorials = "${basedir}/sections/tutorials"
grails.plugin.location.mailgun = "${basedir}/sections/mailgun"
grails.plugin.location.'events-si' = "${basedir}/../grails-plugins/grails-events-si"

grails.project.work.dir = "target"
grails.project.test.reports.dir = "target/test-reports"
grails.project.plugins.dir = "plugins"

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
        mavenCentral()
        mavenRepo "https://oss.sonatype.org/content/repositories/releases/" 
    }

    plugins {
        compile ":burning-image:0.5.0",
                ":commentable:0.7.6",
                ":joda-time:1.3.1",
                ":taggable:1.0.1"

        compile ":rateable:0.7.1", {
            exclude "yui"
        }

        runtime ":avatar:0.3",
                ":rest-client-builder:1.0",
                ":cache-headers:1.1.5",
                ":cached-resources:1.0",
                ":cloud-foundry:1.2",
                ":cloud-support:1.0.6",
                ":database-migration:1.0",
//                ":elasticsearch:0.17.8.1",
                ":feeds:1.5",
                ":grails-ui:1.2.3",
                ":greenmail:1.2.2",
                ":hibernate:$grailsVersion",
                ":jquery:1.6.1.1",
                ":mail:1.0",
                ":platform-core:1.0.M2d-SNAPSHOT",
                ":pretty-time:0.3",
                ":quartz:0.4.2",
                ":rabbitmq:1.0.0.RC1",
                ":resources:1.1.6",
                ":screencasts:0.5.6",
                ":searchable:0.6.3",
                ":shiro:1.2.0-SNAPSHOT",
                ":shiro-oauth:0.2",
                ":simple-blog:0.1.5",
                ":spring-events:1.2",
                ":yui:2.8.2.1",
                ":zipped-resources:1.0"

        if (Environment.current == Environment.DEVELOPMENT) {
            compile ":build-test-data:1.1.1",
                    ":fixtures:1.1", {
                exclude "svn"
            }
        }
        else {
            test    ":build-test-data:1.1.1",
                    ":fixtures:1.1", {
                exclude "svn"
            }
        }

        if (Environment.current == Environment.PRODUCTION) {
            compile ":cache-redis:1.0.0.M2"
        }
        else {
            compile ":cache-ehcache:1.0.0.M2"
        }
        
        test    ":geb:0.6.0",
                ":spock:0.6", {
            excludes 'xml-apis'
        }

        build   ":tomcat:$grailsVersion"
    }

    dependencies {
        compile "org.twitter4j:twitter4j-core:2.1.8",
                "org.springframework:spring-context-support:3.0.3.RELEASE",
                "org.jadira.usertype:usertype.jodatime:1.9"

        test "org.codehaus.geb:geb-core:0.6.0",
             "org.gmock:gmock:0.8.1"
        test    "org.seleniumhq.selenium:selenium-htmlunit-driver:2.0a7", {
            excludes "htmlunit", "xml-apis"
        }
        test    "net.sourceforge.htmlunit:htmlunit:2.8", {
            excludes "xml-apis", "commons-logging"
        }

        runtime "org.apache.httpcomponents:httpclient:4.1.2"
        runtime "org.codehaus.groovy.modules.http-builder:http-builder:0.5.1", {
            excludes "commons-logging", "httpclient", "xml-apis", "groovy"
        }

        if (Environment.current == Environment.DEVELOPMENT) {
            runtime "org.grails:grails-test:$grailsVersion"
        }

//        if (Environment.current == Environment.PRODUCTION) {
            runtime "org.springframework.amqp:spring-amqp:1.1.0.RELEASE"
            compile "org.springframework.integration:spring-integration-amqp:2.1.1.RELEASE"
            /*
            compile "org.springframework.amqp:spring-rabbit:1.0.0.RELEASE", {
                excludes 'junit',
                         'spring-aop',
                         'spring-core', // Use spring-core from Grails.
                         'spring-oxm',
                         'spring-test',
                         'spring-tx',
                         'slf4j-log4j12',
                         'log4j'
            }
            */
//        }
    }

}

grails.tomcat.jvmArgs = [ '-Xmx512m', '-XX:MaxPermSize=256m' ]
