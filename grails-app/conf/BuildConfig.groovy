grails.plugin.location.websites = "${basedir}/sections/grails-sites"
grails.plugin.location.tutorials = "${basedir}/sections/tutorials"

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

        runtime ":rest-client-builder:1.0",
                ":cache-headers:1.1.5",
                ":cached-resources:1.0",
                ":database-migration:1.0",
                ":feeds:1.5",
                ":grails-ui:1.2.3",
                ":greenmail:1.2.2",
                ":hibernate:2.0.3",
                ":jquery:1.6.1.1",
                ":mail:1.0-SNAPSHOT",
                ":pretty-time:0.3",
                ":quartz:0.4.2",
                ":resources:1.1.6",
                ":richui:0.6",
                ":screencasts:0.5.6",
                ":searchable:0.6.3",
                ":shiro:1.1.3",
                ":simple-blog:0.1.5",
                ":springcache:1.3.1",
                ":spring-events:1.2",
                ":yui:2.8.2.1",
                ":zipped-resources:1.0"

        if (Environment.current == Environment.DEVELOPMENT) {
            compile ":build-test-data:1.1.1",
                    ":fixtures:1.1-SNAPSHOT"
        }
        else {
            test    ":build-test-data:1.1.1",
                    ":fixtures:1.1-SNAPSHOT"
        }
        
        test    ":geb:0.6.0",
                ":spock:0.6-rc-1", {
            excludes 'xml-apis'
        }

        build   ":tomcat:2.0.3"
    }

    dependencies {
        compile "org.twitter4j:twitter4j-core:2.1.8",
                "org.springframework:spring-context-support:3.0.3.RELEASE",
                "org.jadira.usertype:usertype.jodatime:1.9"

        test "org.codehaus.geb:geb-core:0.6.0",
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

        if (Environment.current == Environment.DEVELOPMENT) {
            runtime "org.grails:grails-test:$grailsVersion"
        }
    }

}

grails.tomcat.jvmArgs = [ '-Xmx512m', '-XX:MaxPermSize=256m' ]
