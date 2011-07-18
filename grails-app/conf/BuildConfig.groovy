grails.project.work.dir = "target"
grails.project.test.reports.dir = "target/test-reports"
grails.project.plugins.dir = "plugins"

grails.project.dependency.resolution = {
    inherits "global"
    log      "warn"

    repositories {        
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://repository.codehaus.org"
        mavenRepo "http://maven.springframework.org/milestone"
        mavenRepo "http://snapshots.repository.codehaus.org"
    }

    plugins {
        runtime ":avatar:0.3",
                ":cache-headers:1.1.5",
                ":cached-resources:1.0",
                ":commentable:0.7.5",
                ":database-migration:0.2.1",
                ":feeds:1.5",
                ":grails-ui:1.2",
                ":greenmail:1.2.2",
                ":hibernate:1.3.7",
                ":mail:1.0-SNAPSHOT",
                ":pretty-time:0.3",
                ":quartz:0.4.2",
                ":rateable:0.7.0",
                ":resources:1.0",
                ":richui:0.6",
                ":screencasts:0.5.2",
                ":searchable:0.6",
                ":shiro:1.1.3",
                ":simple-blog:0.1.5",
                ":springcache:1.3.1",
                ":spring-events:1.1",
                ":taggable:0.6.3",
                ":zipped-resources:1.0"
        
        test    ":build-test-data:1.1.1",
                ":fixtures:1.0.7",
                ":geb:0.5-SNAPSHOT",
                ":spock:0.5-groovy-1.7-SNAPSHOT", {
            excludes 'xml-apis'
        }

        build   ":tomcat:1.3.7"
    }

    dependencies {
        compile "org.twitter4j:twitter4j-core:2.1.8"

        test    "org.codehaus.groovy.modules.http-builder:http-builder:0.5.0", {
            excludes "commons-logging", "httpclient", "xml-apis", "groovy"
        }
        test    "org.seleniumhq.selenium:selenium-htmlunit-driver:2.0a7", {
            excludes "htmlunit", "xml-apis"
        }
        test    "net.sourceforge.htmlunit:htmlunit:2.8", {
            excludes "xml-apis", "commons-logging"
        }
    }

}

grails.tomcat.jvmArgs = [ '-Xmx512m', '-XX:MaxPermSize=256m' ]
