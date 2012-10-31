grails.plugin.location.like = "${basedir}/sections/like"

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
        compile ":burning-image:0.5.1",
                ":joda-time:1.3.1",
                ":taggable:1.0.1"

        compile ":rateable:0.7.1"

        runtime ":grails-ui:1.2.3", {
            exclude "yui"
        }

        runtime ":avatar:0.3",
                ":rest-client-builder:1.0",
                ":cache:1.0.0",
                ":cache-headers:1.1.5",
                ":cached-resources:1.0",
                ":database-migration:1.0",
                ":disqus:0.1",
                ":feeds:1.5",
                ":greenmail:1.2.2",
                ":hibernate:$grailsVersion",
                ":jquery:1.7.2",
                ":mail:1.0",
                ":pretty-time:0.3",
                ":quartz:0.4.2",
                ":resources:1.2.RC2",
                ":searchable:0.6.4",
                ":shiro:1.2.0-SNAPSHOT",
                ":shiro-oauth:0.2",
                ":spring-events:1.2",
                ":zipped-resources:1.0"

        if (Environment.current == Environment.DEVELOPMENT) {
            compile ":build-test-data:1.1.1",
                    ":fixtures:1.2"
        }
        else {
            test    ":build-test-data:1.1.1",
                    ":fixtures:1.2"
        }
        
        test    ":geb:0.6.0",
                ":spock:0.6", {
            excludes 'xml-apis'
        }

        build   ":tomcat:$grailsVersion"
    }

    dependencies {
        build "org.lesscss:lesscss:1.3.0"

        compile "org.twitter4j:twitter4j-core:2.1.8",
                "org.springframework:spring-context-support:3.0.3.RELEASE",
                "org.jadira.usertype:usertype.jodatime:1.9",
                "org.jsoup:jsoup:1.6.3"

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

grails.tomcat.jvmArgs = [ '-Xmx512m', '-XX:MaxPermSize=256m','-Dinitial.admin.password=changeit',
        '-Dload.fixtures=1' ]
