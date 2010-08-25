import org.grails.plugin.Plugin

fixture {
    build {
        shiro(Plugin, name: "shiro", title: "Integration of Apache Shiro security library", currentRelease: "1.1-SNAPSHOT")
        fixtures(Plugin, name: "fixtures", title: "Fixtures Plugin", currentRelease: "1.0.1")
        buildTD(Plugin, name: "build-test-data", title: "Build Test Data Plugin", currentRelease: "1.2")
        hibernate(Plugin, name: "hibernate", title: "Hibernate ORM Plugin", currentRelease: "1.3.4")
        tomcat(Plugin, name: "tomcat", title: "Tomcat", currentRelease: "1.3.4")
        gwt(Plugin, name: "gwt", title: "GWT Integration", currentRelease: "0.2.3-SNAPSHOT")
    }
}
