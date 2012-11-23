import org.grails.auth.User
import org.grails.content.Version
import org.grails.plugin.License
import org.grails.plugin.Plugin
import org.grails.plugin.PluginTab
import org.joda.time.DateTime

fixture {
    build {
        // Plugin tab 'wiki' pages for each of the above plugins.
        def admin = User.findByLogin("admin")

        apacheLicense(License,
                name: "Apache License 2.0",
                url: "http://www.apache.org/licenses/LICENSE-2.0.txt")

        gpl2License(License,
                name: "GNU General Public License 2",
                url: "http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt")

        gpl3License(License,
                name: "GNU General Public License 3",
                url: "http://www.gnu.org/licenses/gpl.txt")
        
        // First Shiro
        shiroInstallation(PluginTab, title: "plugin-shiro-installation", body: "@grails install-plugin shiro@")
        siVersion(Version,
                title: shiroInstallation.title,
                body: shiroInstallation.body,
                number: 0,
                current: shiroInstallation,
                author: admin)
        
        shiroDescription(PluginTab, title: "plugin-shiro-description", body: "Apache Shiro integration for Grails")
        sdVersion(Version,
                title: shiroDescription.title,
                body: shiroDescription.body,
                number: 0,
                current: shiroDescription,
                author: admin)
        
        shiroFaq(PluginTab, title: "plugin-shiro-faq", body: "")
        sfVersion(Version,
                title: shiroFaq.title,
                body: shiroFaq.body,
                number: 0,
                current: shiroFaq,
                author: admin)
        
        shiroScreenshots(PluginTab, title: "plugin-shiro-screenshots", body: "")
        ssVersion(Version,
                title: shiroScreenshots.title,
                body: shiroScreenshots.body,
                number: 0,
                current: shiroScreenshots,
                author: admin)
        
        shiro(Plugin,
                name: "shiro",
                title: "Apache Shiro Plugin",
                currentRelease: "1.1-SNAPSHOT",
                authors: [peterInfo],
                organization: "KataSoft",
                summary: "Integrates the Apache Shiro security framework into your Grails applications.",
                lastReleased: new DateTime(2009, 12, 10, 10, 00),
                installation: shiroInstallation,
                description: shiroDescription,
                faq: shiroFaq,
                screenshots: shiroScreenshots,
                licenses: [ apacheLicense ])
        
        // Fixtures
        fixturesInstallation(PluginTab, title: "plugin-fixtures-installation", body: "@grails install-plugin fixtures@")
        fiVersion(Version,
                title: fixturesInstallation.title,
                body: fixturesInstallation.body,
                number: 0,
                current: fixturesInstallation,
                author: admin)
        
        fixturesDescription(PluginTab, title: "plugin-fixtures-description", body: "Set up fixture data for your Grails application.")
        fdVersion(Version,
                title: fixturesDescription.title,
                body: fixturesDescription.body,
                number: 0,
                current: fixturesDescription,
                author: admin)
        
        fixturesFaq(PluginTab, title: "plugin-fixtures-faq", body: "")
        ffVersion(Version,
                title: fixturesFaq.title,
                body: fixturesFaq.body,
                number: 0,
                current: fixturesFaq,
                author: admin)
        
        fixturesScreenshots(PluginTab, title: "plugin-fixtures-screenshots", body: "")
        fsVersion(Version,
                title: fixturesScreenshots.title,
                body: fixturesScreenshots.body,
                number: 0,
                current: fixturesScreenshots,
                author: admin)
        
        fixtures(Plugin,
                name: "fixtures",
                title: "Fixtures Plugin",
                currentRelease: "1.0.1",
                authors: [gpcInfo],
                summary: "Declare sample data using a Spring Bean DSL like syntax and load it form anywhere in your application: tests, BootStrap, controllers, in fact anyhere. Also integrates with Build Test Data plugin.",
                lastReleased: new DateTime(2011, 5, 5, 17, 11),
                installation: fixturesInstallation,
                description: fixturesDescription,
                faq: fixturesFaq,
                screenshots: fixturesScreenshots)
        
        // Build Test Data
        btdInstallation(PluginTab, title: "plugin-build-test-data-installation", body: "@grails install-plugin build-test-data@")
        btddVersion(Version,
                title: btdInstallation.title,
                body: btdInstallation.body,
                number: 0,
                current: btdInstallation,
                author: admin)
        
        btdDescription(PluginTab, title: "plugin-build-test-data-description", body: "Set up fixture data for your Grails application.")
        btddVersion(Version,
                title: btdDescription.title,
                body: btdDescription.body,
                number: 0,
                current: btdDescription,
                author: admin)
        
        btdFaq(PluginTab, title: "plugin-build-test-data-faq", body: "")
        btdfVersion(Version,
                title: btdFaq.title,
                body: btdFaq.body,
                number: 0,
                current: btdFaq,
                author: admin)
        
        btdScreenshots(PluginTab, title: "plugin-build-test-data-screenshots", body: "")
        btdsVersion(Version,
                title: btdScreenshots.title,
                body: btdScreenshots.body,
                number: 0,
                current: btdScreenshots,
                author: admin)
        
        buildTD(Plugin,
                name: "build-test-data",
                title: "Build Test Data Plugin",
                currentRelease: "1.2",
                authors: [tedInfo],
                summary: """\
This plugin allows the user to easily create testing data through the use of a "build" method that is added to all Domain Classes. The build method inspects all of the constraints and creates default values for those required properties.

This testing data is much more robust as it changes with the domain classes when new properties and constraints are added. The only tests that will break are the ones directly related to the changes you're making, rather than any test using any part of that domain class.

This is a test list:

* One
* Two
* What comes next?
""",
                lastReleased: new DateTime(2012, 1, 23, 0, 0),
                installation: btdInstallation,
                description: btdDescription,
                faq: btdFaq,
                screenshots: btdScreenshots)
        
        // Hibernate
        hibernateInstallation(PluginTab, title: "plugin-hibernate-installation", body: "@grails install-plugin hibernate@")
        hiVersion(Version,
                title: hibernateInstallation.title,
                body: hibernateInstallation.body,
                number: 0,
                current: hibernateInstallation,
                author: admin)
        
        hibernateDescription(PluginTab, title: "plugin-hibernate-description", body: "Hibernate for GORM.")
        hdVersion(Version,
                title: hibernateDescription.title,
                body: hibernateDescription.body,
                number: 0,
                current: hibernateDescription,
                author: admin)
        
        hibernateFaq(PluginTab, title: "plugin-hibernate-faq", body: "")
        hfVersion(Version,
                title: hibernateFaq.title,
                body: hibernateFaq.body,
                number: 0,
                current: hibernateFaq,
                author: admin)
        
        hibernateScreenshots(PluginTab, title: "plugin-hibernate-screenshots", body: "")
        hsVersion(Version,
                title: hibernateScreenshots.title,
                body: hibernateScreenshots.body,
                number: 0,
                current: hibernateScreenshots,
                author: admin)
        
        hibernate(Plugin,
                name: "hibernate",
                title: "Hibernate ORM Plugin",
                currentRelease: "1.3.4",
                authors: [springSourceInfo],
                organization: "SpringSource",
                organizationUrl: "http://www.springsource.org/",
                lastReleased: new DateTime(2011, 12, 31, 0, 0),
                installation: hibernateInstallation,
                description: hibernateDescription,
                faq: hibernateFaq,
                screenshots: hibernateScreenshots,
                licenses: [ apacheLicense, gpl3License ])


        //Cache 
       cacheInstallation(PluginTab, title: "plugin-cache-installation", body: "@grails install-plugin cache@")
        cacheVersion(Version,
                title:cacheInstallation.title,
                body:cacheInstallation.body,
                number: 0,
                current:cacheInstallation,
                author: admin)
        
        cacheDescription(PluginTab, title: "plugin-cache-description", body: "Cache Plugin.")
        cdVersion(Version,
                title: cacheDescription.title,
                body: cacheDescription.body,
                number: 0,
                current: cacheDescription,
                author: admin)
        
        cacheFaq(PluginTab, title: "plugin-cache-faq", body: "")
        cfVersion(Version,
                title: cacheFaq.title,
                body: cacheFaq.body,
                number: 0,
                current: cacheFaq,
                author: admin)
        
        cacheScreenshots(PluginTab, title: "plugin-cache-screenshots", body: "")
        csVersion(Version,
                title: cacheScreenshots.title,
                body: cacheScreenshots.body,
                number: 0,
                current: cacheScreenshots,
                author: admin)
        
        cache(Plugin,
                name: "cache",
                title: "Cache Plugin",
                currentRelease: "1.3.4",
                authors: [springSourceInfo],
                organization: "SpringSource",
                organizationUrl: "http://www.springsource.org/",
                lastReleased: new DateTime(2011, 12, 31, 0, 0),
                installation: cacheInstallation,
                description: cacheDescription,
                faq: cacheFaq,
                screenshots: cacheScreenshots,
                licenses: [ apacheLicense, gpl3License ])        


        //Mail
       mailInstallation(PluginTab, title: "plugin-mail-installation", body: "@grails install-plugin mail@")
        mailVersion(Version,
                title:mailInstallation.title,
                body:mailInstallation.body,
                number: 0,
                current:mailInstallation,
                author: admin)
        
        mailDescription(PluginTab, title: "plugin-mail-description", body: "mailPlugin.")
        mdVersion(Version,
                title: mailDescription.title,
                body: mailDescription.body,
                number: 0,
                current: mailDescription,
                author: admin)
        
        mailFaq(PluginTab, title: "plugin-mail-faq", body: "")
        mfVersion(Version,
                title: mailFaq.title,
                body: mailFaq.body,
                number: 0,
                current: mailFaq,
                author: admin)
        
        mailScreenshots(PluginTab, title: "plugin-mail-screenshots", body: "")
        msVersion(Version,
                title: mailScreenshots.title,
                body: mailScreenshots.body,
                number: 0,
                current: mailScreenshots,
                author: admin)
        
        mail(Plugin,
                name: "mail",
                title: "mailPlugin",
                currentRelease: "1.3.4",
                authors: [springSourceInfo],
                organization: "SpringSource",
                organizationUrl: "http://www.springsource.org/",
                lastReleased: new DateTime(2011, 12, 31, 0, 0),
                installation: mailInstallation,
                description: mailDescription,
                faq: mailFaq,
                screenshots: mailScreenshots,
                licenses: [ apacheLicense, gpl3License ])        

       // Feeds
       feedsInstallation(PluginTab, title: "plugin-feeds-installation", body: "@grails install-plugin feeds@")
        feedsVersion(Version,
                title:feedsInstallation.title,
                body:feedsInstallation.body,
                number: 0,
                current:feedsInstallation,
                author: admin)
        
        feedsDescription(PluginTab, title: "plugin-feeds-description", body: "Feeds Plugin.")
        fdVersion(Version,
                title: feedsDescription.title,
                body: feedsDescription.body,
                number: 0,
                current: feedsDescription,
                author: admin)
        
        feedsFaq(PluginTab, title: "plugin-feeds-faq", body: "")
        ffVersion(Version,
                title: feedsFaq.title,
                body: feedsFaq.body,
                number: 0,
                current: feedsFaq,
                author: admin)
        
        feedsScreenshots(PluginTab, title: "plugin-feeds-screenshots", body: "")
        fsVersion(Version,
                title: feedsScreenshots.title,
                body: feedsScreenshots.body,
                number: 0,
                current: feedsScreenshots,
                author: admin)
        
        feeds(Plugin,
                name: "feeds",
                title: "Feeds Plugin",
                currentRelease: "1.3.4",
                authors: [],
                organization: "SpringSource",
                organizationUrl: "http://www.springsource.org/",
                lastReleased: new DateTime(2011, 12, 31, 0, 0),
                installation: feedsInstallation,
                description: feedsDescription,
                faq: feedsFaq,
                screenshots: feedsScreenshots,
                licenses: [ apacheLicense, gpl3License ])  
        
        // Tomcat
        tomcatInstallation(PluginTab, title: "plugin-tomcat-installation", body: "@grails install-plugin tomcat@")
        tiVersion(Version,
                title: tomcatInstallation.title,
                body: tomcatInstallation.body,
                number: 0,
                current: tomcatInstallation,
                author: admin)
        
        tomcatDescription(PluginTab, title: "plugin-tomcat-description", body: "Use Tomcat for run-app.")
        tdVersion(Version,
                title: tomcatDescription.title,
                body: tomcatDescription.body,
                number: 0,
                current: tomcatDescription,
                author: admin)
        
        tomcatFaq(PluginTab, title: "plugin-tomcat-faq", body: "")
        tfVersion(Version,
                title: tomcatFaq.title,
                body: tomcatFaq.body,
                number: 0,
                current: tomcatFaq,
                author: admin)
        
        tomcatScreenshots(PluginTab, title: "plugin-tomcat-screenshots", body: "")
        tsVersion(Version,
                title: tomcatScreenshots.title,
                body: tomcatScreenshots.body,
                number: 0,
                current: tomcatScreenshots,
                author: admin)
        
        tomcat(Plugin,
                name: "tomcat",
                title: "Tomcat",
                currentRelease: "1.3.4",
                authors: [springSourceInfo],
                lastReleased: new DateTime(2010, 8, 11, 22, 30),
                installation: tomcatInstallation,
                description: tomcatDescription,
                faq: tomcatFaq,
                screenshots: tomcatScreenshots,
                usage: 0.6)
        
        // GWT
        gwtInstallation(PluginTab, title: "plugin-gwt-installation", body: "@grails install-plugin gwt@")
        gwtiVersion(Version,
                title: gwtInstallation.title,
                body: gwtInstallation.body,
                number: 0,
                current: gwtInstallation,
                author: admin)
        
        gwtDescription(PluginTab, title: "plugin-gwt-description", body: "GWT integration.")
        gwtdVersion(Version,
                title: gwtDescription.title,
                body: gwtDescription.body,
                number: 0,
                current: gwtDescription,
                author: admin)
        
        gwtFaq(PluginTab, title: "plugin-gwt-faq", body: "")
        gwtfVersion(Version,
                title: gwtFaq.title,
                body: gwtFaq.body,
                number: 0,
                current: gwtFaq,
                author: admin)
        
        gwtScreenshots(PluginTab, title: "plugin-gwt-screenshots", body: "")
        gwtsVersion(Version,
                title: gwtScreenshots.title,
                body: gwtScreenshots.body,
                number: 0,
                current: gwtScreenshots,
                author: admin)
        
        gwt(Plugin,
                name: "gwt",
                title: "GWT Integration",
                currentRelease: "0.2.3-SNAPSHOT",
                grailsVersion: "1.1 > *",
                lastReleased: new DateTime(2010, 8, 10, 14, 21),
                authors: [peterInfo, anonymousInfo],
                installation: gwtInstallation,
                description: gwtDescription,
                faq: gwtFaq,
                screenshots: gwtScreenshots,
                usage: 0.3)
        
        // Gemfire
        gemfireInstallation(PluginTab, title: "plugin-gemfire-installation", body: "@grails install-plugin gemfire@")
        gfiVersion(Version,
                title: gemfireInstallation.title,
                body: gemfireInstallation.body,
                number: 0,
                current: gemfireInstallation,
                author: admin)
        
        gemfireDescription(PluginTab, title: "plugin-gemfire-description", body: "")
        gfdVersion(Version,
                title: gemfireDescription.title,
                body: gemfireDescription.body,
                number: 0,
                current: gemfireDescription,
                author: admin)
        
        gemfireFaq(PluginTab, title: "plugin-gemfire-faq", body: "")
        gffVersion(Version,
                title: gemfireFaq.title,
                body: gemfireFaq.body,
                number: 0,
                current: gemfireFaq,
                author: admin)
        
        gemfireScreenshots(PluginTab, title: "plugin-gemfire-screenshots", body: "")
        gfsVersion(Version,
                title: gemfireScreenshots.title,
                body: gemfireScreenshots.body,
                number: 0,
                current: gemfireScreenshots,
                author: admin)
        
        gemfire(Plugin,
                name: "gemfire",
                title: "Gemfire Plugin",
                summary: "Implements the GORM API to map domain classes to a Gemfire data grid.",
                currentRelease: "1.0",
                grailsVersion: "1.3 > *",
                featured: true,
                issuesUrl: "http://jira.grails.org/browse/GPGEMFIRE",
                scmUrl: "https://github.com/grails-plugins/grails-gemfire",
                documentationUrl: "http://grails-plugins.github.com/grails-gemfire/",
                lastReleased: new DateTime(2011, 3, 7, 8, 30),
                authors: [springSourceInfo],
                installation: gemfireInstallation,
                description: gemfireDescription,
                faq: gemfireFaq,
                screenshots: gemfireScreenshots)

        // Spock
        spockInstallation(PluginTab, title: "plugin-spock-installation", body: "@grails install-plugin spock@")
        gfiVersion(Version,
                title: spockInstallation.title,
                body: spockInstallation.body,
                number: 0,
                current: spockInstallation,
                author: admin)
        
        spockDescription(PluginTab, title: "plugin-spock-description", body: "")
        gfdVersion(Version,
                title: spockDescription.title,
                body: spockDescription.body,
                number: 0,
                current: spockDescription,
                author: admin)
        
        spockFaq(PluginTab, title: "plugin-spock-faq", body: "")
        gffVersion(Version,
                title: spockFaq.title,
                body: spockFaq.body,
                number: 0,
                current: spockFaq,
                author: admin)
        
        spockScreenshots(PluginTab, title: "plugin-spock-screenshots", body: "")
        gfsVersion(Version,
                title: spockScreenshots.title,
                body: spockScreenshots.body,
                number: 0,
                current: spockScreenshots,
                author: admin)
        
        spock(Plugin,
                name: "spock",
                groupId: "org.spockframework.grails",
                title: "Spock Plugin",
                summary: "Allows you to write your tests using Spock instead of JUnit.",
                currentRelease: "1.0",
                grailsVersion: "1.3 > *",
                authors: [gpcInfo],
                featured: true,
                issuesUrl: "http://jira.grails.org/browse/GPSPOCK",
                scmUrl: "https://github.com/grails-plugins/grails-spock",
                documentationUrl: "http://grails-plugins.github.com/grails-spock/",
                lastReleased: new DateTime(2011, 3, 7, 15, 59),
                installation: spockInstallation,
                description: spockDescription,
                faq: spockFaq,
                screenshots: spockScreenshots,
                mavenRepositories: ["http://m2repo.spockframework.org/snapshots", "http://repo.grails.org/spock"])
    }
}
