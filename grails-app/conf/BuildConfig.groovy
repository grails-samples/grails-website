grails.plugin.location.like = "${basedir}/inline-plugins/like"

grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
grails.project.war.file = "target/grails-website.war"
grails.project.plugins.dir = "target/plugins"

grails.project.fork = [
   // test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true], // configure settings for the test-app JVM
   run: [maxMemory: 1024, minMemory: 256, debug: false, maxPerm: 256], // configure settings for the run-app JVM
   war: [maxMemory: 1024, minMemory: 256, debug: false, maxPerm: 256], // configure settings for the run-war JVM
   console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]// configure settings for the Console UI JVM
]

if (System.getProperty("grails.debug")) {
   grails.project.fork.war += [debug: true]
   grails.project.fork.run += [debug: true]
   println "Using debug for run-war"
}

grails.project.dependency.resolver="maven"
grails.project.dependency.resolution = {
    inherits "global", {
        excludes "xml-apis", "commons-digester", "ehcache", 'cglib'
    }

    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherit false
        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenRepo "https://repo.grails.org/grails/core"
        mavenRepo "https://oss.sonatype.org/content/repositories/releases/"
        mavenRepo "http://repo.spring.io/milestone"
        mavenRepo "https://repository.jboss.org/maven2/"
    }

    plugins {
        compile ":burning-image:0.5.2",
                ":joda-time:1.5",
                ":taggable:1.1.0"

        compile ":rateable:0.7.1", {
            excludes 'yui', 'svn'
        }
        compile ":yui:2.8.2.1", {
            excludes 'svn'
        }

        runtime ":avatar:0.6.3",
                ":rest-client-builder:2.0.3",
                ":cache:1.1.8",
                ":cache-headers:1.1.7",
                ":database-migration:1.4.0",
                ":disqus:0.3",
                ":feeds:1.6",
                ":greenmail:1.3.4",
                ":hibernate:3.6.10.18",
                ":jquery:1.11.1",
                ":jquery-ui:1.10.4",
                ":mail:1.0.7",
                ":pretty-time:2.1.3.Final-1.0.1",
                ":quartz:1.0.2",
                ":searchable:0.6.9",
                ":spring-events:1.2"

        runtime ":shiro:1.2.1", {
            exclude 'org.opensymphony.quartz:quartz'
        }
        runtime ":oauth:2.6.1"

        runtime ":shiro-oauth:0.3", {
            excludes 'shiro-core'
        }

        compile ":asset-pipeline:2.1.0"
        compile ":less-asset-pipeline:2.0.8"

        String build_test_data_scope = (Environment.current == Environment.DEVELOPMENT) ? "compile" : "test"
        "$build_test_data_scope" ":build-test-data:2.2.3", ":fixtures:1.3"

        compile ":platform-core:1.0.0"
        runtime ":cache-ehcache:1.0.0", { exclude "cache" }


        test ":geb:0.10.0", {
            excludes 'xml-apis'
            exclude "spock-grails-support"
        }

        build   ":tomcat:8.0.15"
        compile ":scaffolding:2.1.2"
    }

    dependencies {
        compile "org.grails:grails-gdoc-engine:1.0.1"
        compile "org.springframework.social:spring-social-twitter:1.0.5.RELEASE",
                "org.springframework:spring-context-support:$springVersion",
                "org.jadira.usertype:usertype.jodatime:1.9",
                "org.jsoup:jsoup:1.7.3"

        compile('org.apache.lucene:lucene-highlighter:2.4.1',
                'org.apache.lucene:lucene-analyzers:2.4.1',
                'org.apache.lucene:lucene-queries:2.4.1',
                'org.apache.lucene:lucene-snowball:2.4.1',
                'org.apache.lucene:lucene-spellchecker:2.4.1')

        compile "org.apache.shiro:shiro-core:1.2.2"

        test "org.gebish:geb-core:0.10.0",
             "org.gmock:gmock:0.8.3"
        test    "org.codehaus.groovy.modules.http-builder:http-builder:0.7.1", {
            excludes "commons-logging", "httpclient", "xml-apis", "groovy", "groovy-all", "xercesImpl", "nekohtml"
        }
        test    "org.seleniumhq.selenium:selenium-htmlunit-driver:2.41.0", {
            excludes "htmlunit", "xml-apis"
        }
        test    "net.sourceforge.htmlunit:htmlunit:2.13", {
            excludes "xml-apis", "commons-logging", "xercesImpl"
        }

        test 'org.gebish:geb-spock:0.10.0'

        if (Environment.current == Environment.DEVELOPMENT) {
            runtime "org.grails:grails-test:$grailsVersion"
        }

        compile "org.springframework.cloud:spring-cloud-cloudfoundry-connector:1.1.0.RELEASE"
        compile "org.springframework.cloud:spring-cloud-spring-service-connector:1.1.0.RELEASE"

        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"

        compile 'org.ajoberstar:grgit:0.4.0'

        runtime 'mysql:mysql-connector-java:5.1.34'
    }

}

if(!System.getProperty('nofixtures')) {
  grails.project.fork.war.jvmArgs = [ '-Dinitial.admin.password=changeit', '-Dload.fixtures=1' ]
}

// make sure ~/.grails-static-website directory exists since META-INF/context.xml references it
new File(System.getProperty("user.home"), ".grails-static-website").mkdir()
