import org.grails.auth.User
import org.grails.content.Version
import org.grails.wiki.WikiPage

def documentationPage = '''\
h1. Documentation

Grails aims to bring the "coding by convention" paradigm to Groovy. It's an open-source web application framework that leverages the Groovy language and complements Java Web development. Grails is [licensed|License] under the liberal Apache 2.0 Software License.

Below is a list of documentation currently available for Grails:

# [Installation] - General installation notes
# [Quick Start] - Help to get up and running quickly
# [User Guide|http://grails.org/doc/latest/] - The official user guide.
# [User Guide PDF|http://grails.org/doc/latest/guide/single.pdf] - The official user guide as a PDF.
# [API Javadocs|http://grails.org/doc/latest/api/] - The latest API Javadocs
# [Tutorials] - Loads of info here including screencasts, podcasts, tutorials, articles and more!!
# [Books] - Available books on Grails and Groovy
# [Plugins] - A list of available plug-ins for Grails
# [Tools] - A list of related tools such as scripts and widgets
# [Success Stories] - List of sites known or suspected to be based on Grails
# [Ant and Maven integration|http://grails.org/doc/latest/guide/4.%20The%20Command%20Line.html#4.5 Ant and Maven] - How to build Grails projects using Ant or Maven
# [Developer Documentation] - Stuff to help you work on Grails itself; basically how it works

h4. Other Languages

* [Portuguese Version|Documentation Portuguese]
'''

def testimonialsPage = '''\
In addition to the case studies on this page, SpringSource also has [several available|http://www.springsource.com/customers/case-studies] on its website. Just follow the link and search the page for "Groovy" or "Grails".


h1. SpringSource Case Studies

h1. Sky.com Accelerates Release Cycle Six Fold With Groovy and Grails

Sky is the UK’s leading entertainment and communications  company, operating a multi-channel television service with 9.4+ million customers - a third of households in the UK and Ireland. Sky also operates multiple websites to deliver entertainment, news and other content to customers, and the company is the UK’s fastest growing broadband and fixed-telephony provider.

Sky’s network of sites extend the company’s reach, attracting millions of unique users each month who contribute to over 1 billion monthly page impressions. The home page of sky.com links to content across a variety of sites and plays a vital role in helping Sky achieve its goal to increase traffic across all sites and gain new customers for its portfolio of services.

* [Case Study|http://www.springsource.com/files/uploads/all/pdf_files/customer/Sky.com%20Case%20Study.pdf]


“Now using Groovy and Grails, we release every week to production, compared with every six weeks previously.”

*Jon Mullen, sky.com ScrumMaster*

h1. Wired.com Simplifies and Accelerates Development with Grails

Wired.com, the online arm of Wired Magazine, is a cutting edge guide covering how technology is changing the world. Not simply an online version of the print publication, however, Wired.com delivers a unique online experience to 12 million readers around the world.

* [Case Study|http://www.springsource.com/files/uploads/all/pdf_files/customer/Wired.com+Case+study.pdf]

"Our developers are much happier developing in Grails because they can accomplish tasks so much faster"

*Paul Fisher, Tech Mgr Wired.com*
'''

def communityPage = '''\
h1. Get Involved!

Grails has a growing following and if you want to get involved the developers are all nice people (promise!) who are keen to have ideas, code and just about anything contributed. There are a number of ways to get involved with the Grails community.

h2. Keep up to date

* Follow Burt Beckwith's ["This week in Grails" blog|http://burtbeckwith.com/blog/?cat=32] (or [subscribe to the news feed|http://feeds.feedburner.com/this-week-in-grails])
* Follow [Grails on Twitter|Twitter]

h2. Communicate

* Take part in the [Mailing lists] or [Forums]
* Get onto the #grails IRC channel on irc.freenode.net
* Join one of the [User Groups]
* Meet up at a [Hackergarten|Hackergartens]
* Join the [Grails User Group on LinkedIn|http://www.linkedin.com/groups?mostPopular=&gid=39757]
* Join the [Grails Group on Facebook|http://www.facebook.com/group.php?gid=5965658003]
* Keep the Grails site up to date (it's an an [open wiki|WikiSyntax] - just click the "Edit" link)

h2. Contribute

You can also [contribute|Contribute] back to the project, for example by raising issues, improving the user guide or providing code patches. Any contributions are very welcome!

h3. Other

* Read [Testimonials] from Grails users who have experienced Grails first hand!
* Come and chat to us on IRC at [irc.codehaus.org|http://irc.codehaus.org] where most Grails developers can be found in the #groovy room
* Come and chat to other Grails users on IRC at [irc.freenode.org|http://www.freenode.net] in #grails
* *Write* a [Blog post|Developer Blogs] about Grails, or even a [Tutorial|Tutorials]! Just talking about your experiences with Grails can either help others get involved or provide constructive feedback to the Grails team.
* Look at other [OpenSource Projects] based on Grails or *opensource* your own project
* Follow what is happening with Grails now and in the future with the Grails [Issue Tracker|http://jira.codehaus.org/browse/GRAILS] and on the [Roadmap]. Or even better checkout the [latest commits|http://github.com/grails]!
* *Contribute* your own [Custom Tags|Contribute a Tag] that could eventually be *included* in Grails core!
* Post some *ideas* for new Grails features in the development [Sandbox]. This is the area where we play around with ideas before they are promoted into JIRA issues.
* Add one of the Grails buttons on the left to your site or blog to help spread the word about Grails
* [Contribute] yourself by becoming part of the [Grails Team|Who we are]!
* Are you web development company that uses Grails in your projects? Lets promote yourself on the [Grails Development Firms] page
* Keep your skills up to date with [GrailsMag|http://grailsmag.com]
* Attend a SpringSource [training course|Training Events] to get up to speed with Grails quickly!

h1. Attend a Conference

There are Groovy & Grails related conferences popping up all the time, below is a list of known event. If you know of or are organising a Groovy & Grails related conference, add it here:

h2. United States
* Minneapolis, MN - {link: GR8 Conference US 2011, June 27-28|http://gr8conf.us}

h2. Europe
* Copenhagen, DK - {link: GR8 Conference Europe 2011, May 17-19|http://gr8conf.eu}

h2. Australia

h2. Asia
* Pune, India - {link: 4th IndicThreads.com Conference On Java 2009, December 11th-12th|http://j09.indicthreads.com}
'''

def installationPage = '''\
h2. Installation from Download

h4. Prerequisites

Before you can start using Grails you will need to install a Java SDK (not just a JRE) and set the JAVA_HOME environment variable to the location of that SDK. The minimum required version of the SDK depends on which version of Grails you are using:

* Java SDK 1.4+ for Grails 1.0.x and 1.1.x
* Java SDK 1.5+ for Grails 1.2 or greater

h4. Steps

* [Download] the latest Grails [release|Releases]
* Extract the archive into an appropriate location; typically C:\\\\grails on Windows or \\~/grails on Unix
* Create a  @GRAILS_HOME@  environment variable that points to the path where you extracted the archive (eg C:\\\\grails on Windows or \\~/grails on Unix)
* If you have not set the  @JAVA_HOME@  environment variable yet, create  @JAVA_HOME@  environment variable that points to the path where you have installed Java
* Append a reference to the "bin" directory within the Grails directory to your  @PATH@  variable (eg  @%GRAILS_HOME%\\\\bin@  on Windows or  @$GRAILS_HOME/bin@  on Unix). Note that, for Windows, both  @PATH@  and  @GRAILS_HOME@  must be defined at the same environment variable level (eg. 'System variables') rather than across environment variable levels (eg. @PATH@  under 'System variables' and  @GRAILS_HOME@  under 'User variables')
* Type "grails" at the command line, if a help message is displayed you are ready to [start using Grails|Quick Start]!
* If you get an error message, try to @ chmod +x @ the @ grails @ script inside the @ bin @ directory.

h2. Installation from Git (The Version Control Repository)

h4. Prerequisites

In order to start using Grails from Git you need to have the following:
* An installation of Java 1.5 or higher and have set your  @JAVA_HOME@  variable to the install location
* A Git client

h4. Steps

* Check out Grails from the [Git repository|http://github.com/grails/grails-core/tree/master]
** by running:   @git clone git://github.com/grails/grails-core.git@ \\
* Set the  @GRAILS_HOME@  environment variable to CHECKOUT_LOCATION/grails-core
* Add the  @$GRAILS_HOME/bin@  directory to your  @PATH@  environment variable

What happens next depends on which branch you are working on.

h3. For the 1.3.x, 1.4.x and the 'master' branch:

* Go to the  @GRAILS_HOME@  directory and run:  @./gradlew libs@ \\
* Then from the @GRAILS_HOME@ directory run:   @./gradlew install@ \\

* That's it! You can now [start developing in Grails|Quick Start] using your custom copy of Grails!

h3. For the 1.2.x branch:

* If you don't have Ant 1.7 (or later) installed, you will need to
** set  @ANT_HOME@  to  @$GRAILS_HOME/ant@ \\
** add  @$ANT_HOME/bin@  to your  @PATH@  environment variable
** you may need to give the scripts in  @$ANT_HOME/bin@  execute permissions
* Increase the java memory to 1024M by setting  @ANT_OPTS=-Xmx1024M@ \\
* Go to the  @GRAILS_HOME@  directory and type  @ant jar@  to build Grails

* That's it! You can now [start developing in Grails|Quick Start] using your custom copy of Grails!

h4. Other Languages

* [Portuguese Version|Installation Portuguese]
* [Spanish Version|Installation Spanish]
'''

def quickStartPage = '''\
h1. Quick Start

The following makes it simple to start a grails project. There is also a [series of screencasts|http://grails.org/screencast/search?tag=gswg] that will take you from the basics to a full working Grails application.

* [Quick Start|#Quick Start]
** [Create a Grails project|#Create a Grails project]
** [Configure a Data Source (Optional)|#Configure a Data Source (Optional)]
** [Create a Domain Class|#Create a Domain Class]
** [Create a controller|#Create a controller]
** [Start Grails|#Start Grails]
* [What's Next|#What's Next]

{note}
There is a [Portuguese Version|Quick Start Portuguese] on this Quick Start guide.
{note}

h2. Create a Grails project

Once you have [installed|Installation] Grails you can use the built-in target for creating new projects:
{code}
grails create-app my-project
{code}
This creates a new directory named after your application and containing the project structure below:
{code}
%PROJECT_HOME%
    + grails-app
       + conf                 ---> location of configuration artifacts
           + hibernate        ---> optional hibernate config
           + spring           ---> optional spring config
       + controllers          ---> location of controller artifacts
       + domain               ---> location of domain classes
       + i18n                 ---> location of message bundles for i18n
       + services             ---> location of services
       + taglib               ---> location of tag libraries
       + util                 ---> location of special utility classes
       + views                ---> location of views
           + layouts          ---> location of layouts
   + lib
   + scripts                  ---> scripts
   + src
       + groovy               ---> optional; location for Groovy source files
                                   (of types other than those in grails-app/*)
       + java                 ---> optional; location for Java source files
   + test                     ---> generated test classes
   + web-app
       + WEB-INF
{code}

h2. Create a Domain Class

The core of most Grails applications is the domain model, which represents the persistent data of the application. Creating some persistent data is as simple as creating a new domain class:
{code}
cd my-project

grails create-domain-class org.example.Book
{code}

This command will create the file @Book.groovy@ in the @grails-app/domain/org/example@ directory. This is a straight text file that you can edit with your favorite text editor or [IDE|IDE Integration].

{note}
*Note:* The names of database tables are automatically inferred from the name of the domain class. This can cause problems if the table name matches a keyword in you database. For example, the domain class @Group@ becomes the table 'group' by default, but this is a keyword in MySQL. In such cases, try a different name such as @UserGroup@ or look at the section titled Custom ORM Mapping in the user guide.
{note}

All explicitly typed properties are by default persisted to the database, so let's add some to our @Book@ class now:

{code:title=Book.groovy}
package org.example

class Book {
    String title
    String author

    static constraints = {
        title(blank: false)
        author(blank: false)
    }
}
{code}

The result of the above code is that when we save an instance of @Book@ , its @title@ and @author@ are persisted to the database. Note that we haven't configured the database nor have we created the tables and columns: this all happens automatically.

So what's the @constraints@ property about? That allows you to specify validation requirements for the properties of your domain class. In this particular case, we're declaring that neither @title@ nor @author@ can be blank, i.e. an empty string or a string that only contains whitespace (spaces, tabs, etc.). There are quite a few built-in constraints and you can even add your own - check out the [user guide|http://grails.org/doc/latest/guide/7.%20Validation.html] for more information on validation. You can for example specify that a property must be an email address or have a particular length.

h2. Create a controller

[Controllers|http://grails.org/doc/latest/guide/6.%20The%20Web%20Layer.html#6.1%20Controllers] are central to generating your user interface or providing a programmatic REST interface. They typically handle the web requests that come from a browser or some other client and you'll find that each URL of your application is usually handled by one controller.

Grails provides a feature called scaffolding that automatically creates a user interface for a domain class that allows you create new instances, modify them, and delete them. Enabling scaffolding is very straightforward. We start by creating a controller for our domain class:

{code}
grails create-controller org.example.Book // Note the capital B
{code}

Note how we specify the full class name of the domain class, including the package. This commands creates us the file @grails-app/controllers/org/example/BookController.groovy@ that contains our controller code. Notice how it is named after the domain class but with a 'Controller' suffix? The suffix is important as it identifies the class as a controller.

So now that we have the controller class, open it up and modify it to make the content look like
{code}
package org.example

class BookController {
    def scaffold = Book // Note the capital "B"
}
{code}

This will mean removing the @def index = { }@ line, but that's fine. You're now ready to test out your first Grails application!

h2. Start Grails

To start your Grails application run the following command:

{code}
grails run-app
{code}

This will start up a servlet container (Tomcat). Once the command says the container has started and prints out the URL, copy the link to a browser and view the page. It should display a welcome message and a link to @org.example.BookController@ - click that link to display the scaffolded pages for @Book@ . You'll be able to create new books, modify existing ones, and delete them.

Congratulations, you have written your first Grails application!

h1. What's Next

So, you've written your first Grails application. What should your next steps be? Let's start by looking at some enhancements you can make to the application.

h2. Creating test data

Wouldn't it be great if every time you started the server you saw a list of books already displayed in the scaffolded pages? This is easy to achieve by creating the underlying @Book@ instances in the Grails @BootStrap@ class, which you can find in @grails-app/conf/BootStrap.groovy@ :

{code}
import org.example.Book

class BootStrap {
    def init = { servletContext ->
        // Check whether the test data already exists.
        if (!Book.count()) {
            new Book(author: "Stephen King", title: "The Shining").save(failOnError: true)
            new Book(author: "James Patterson", title: "Along Came a Spider").save(failOnError: true)
        }
    }

    def destroy = {
    }
}
{code}

You see, this @init@ block is executed every time your application starts, whether via @grails run-app@ or when its deployed to a servlet container as a WAR file. The @destroy@ block is likewise executed when the application is terminated.

The first thing to note is the call to @Book.count()@ . This method returns the number of @Book@ instances currently in the database, so we can use it to check whether the test data is already there. If there's no data the method returns 0, which Groovy treats as equivalent to the boolean @false@ in the condition.

So, once we've determined that the test data doesn't exist, we create new @Book@ instances using what Groovy calls 'named arguments' to set the properties. The @save()@ method then persists the @Book@ instances to the database.

{note}
The @failOnError@ option ensures that an exception is thrown if the save fails for any reason, for example if the domain class's constraints are violated. By default, @save()@ returns @null@ if the save fails. An exception is particularly useful in @BootStrap@ because you typically expect the save to succeed and any failure is down to programmer error.
{note}

You can put any code you like in @BootStrap@ . You can even do different things based on the current environment. To find out more, check out the [user guide|http://grails.org/doc/latest/guide/3.%20Configuration.html#3.2 Environments].

If you want your data to last beyond the server shutting down, you will need to configure an alternative database.

h2. Configure a Data Source (Optional)

All new Grails applications include the file @grails-app/conf/DataSource.groovy@ which contains details of the database the application uses. This allows you to set both common settings and per-environment ones.

By default, an application is configured to use an in-memory HSQLDB database for development and testing. If you wanted to use MySQL in production for example, you would have to configure it here:

{code:title=DataSource.groovy}
dataSource {
    ...
}
// environment specific settings
environments {
    development {
        dataSource {
            ...
        }
    }
    test {
        dataSource {
            ...
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/my_app"
            username = "root"
            password = ""
        }
    }
}
{code}

Most of these settings are self-explanatory if you're familiar with JDBC (you can easily find the appropriate settings for your database by doing a web search for "JDBC" + the name of your database provider, e.g. PostgreSQL), but @dbCreate@ deserves a special mention. It determines what happens to your database between server restarts. For example, a value of @create-drop@ means that the database tables will be dropped and then recreated when you start the server, so you lose any data that was in there. This is fine for testing, but is definitely not what you want in production. The [user guide|http://grails.org/doc/latest/guide/3.%20Configuration.html#3.3.3 Automatic Database Migration] has more information on these settings.

{note}
See this [post|http://jlorenzen.blogspot.com/2008/01/how-to-connect-to-grails-hsql-database.html] on how to connect to an HSQL Database using DBVisualizer (this is useful to see the schema grails automatically generates).
{note}

If you want to run your application against a database provider other than HSQLDB, then changing your settings in @DataSource.groovy@ isn't enough. You must also add the JDBC driver, which is typically provided in a JAR. Now, you can drop this the driver JAR into your application's @lib@ directory, but from Grails 1.2 onwards it's much better to declare it as a dependency in @grails-app/conf/BuildConfig.groovy@ - as shown in this example:

{code:title=BuildConfig.groovy}
grails.project.dependency.resolution = {
    inherits "global"
    log "warn"

    repositories {
        grailsHome()
        grailsPlugins()
        grailsCentral()

        mavenCentral()
    }

    dependencies {
        ...
        runtime "postgresql:postgresql:8.3-603.jdbc3"
    }
}
{code}

Make sure to specify the JDBC driver as a @runtime@ dependency and you will almost certainly want to add @mavenCentral()@ to your repositories as well so Grails can find it.

h2. And thenâ€¦

* Check out the [Tutorials] available
* Browse the [User Guide|http://grails.org/doc/latest/]
* See [Checking Projects into SVN] on how to add your new grails application into source control.
* [Import|IDE Integration] your grails application into an IDE
* Browse the many grails [plugins|Plugins] and try some out
'''

def idePage = '''\
h1. IDE Integration

Grails development needs nothing more than a text editor, but of course there are many sophisticated tools available to improve productivity, including syntax highlighting, refactorings and grails command execution.
* [STS Integration] - Using Grails projects in the [SpringSource Tool Suite|http://www.springsource.com/products/sts?__utma=1.131307101616800200.1238651025.1254178059.1256316353.18&__utmb=1.1.10.1256316353&__utmc=1&__utmx=-&__utmz=1.1254178059.17.4.utmcsr=infoq.com|utmccn=%28referral%29|utmcmd=referral|utmcct=/news/2009/05/spring-roo-1.0-m1-release&__utmv=-&__utmk=180557735], an Eclipse-based IDE.  See also the [STS FAQ]
* [NetBeans IDE Integration|NetBeans Integration] - Using NetBeans IDE with Grails projects
* [IDEA Integration] - Using IntelliJ IDEA IDE with Grails projects
* [TextMate Bundle] - TextMate Grails bundle
* [Gedit] - a TextMate like alternative for Linux
* [jEdit|http://www.jedit.org/] - A cross platform programmer's text editor written in Java, has excellent Groovy plugin. jEdit is open source software.
* Emacs - [GSP|http://web.me.com/tcury/site/Creations.html]  support for Emacs. [Groovy-Mode|http://svn.groovy.codehaus.org/browse/~raw,r=HEAD/groovy/trunk/groovy/ide/emacs/groovy-mode.el], [Grails-Minor-Mode|http://github.com/wolfmanjm/grails-mode]


h2. More about IDEs

* Formatting [Doc Comments for GSP tags] (for plugin developers)
'''

def tutorialsPage = '''\
Below is a list of available tutorials/resources about Grails ordered by category. Please have a look at our [Quick Start] and the [Grails Screencasts] to quickly experience the power of the Grails Framework. Besides this, the [Developers' Blogs|Developer Blogs] and the [Grails Podcast] provide additional timely information about the Grails Framework.

{note}
*Please feel free to edit this page (using the Edit button at the top of the page) and link to further Grails resources! You can also use the* *[*Grails User List*|Mailing lists]* *to announce new resources!*
{note}

h2. Beginner Tutorial Sequence

* *[Getting Started with Grails|http://www.infoq.com/minibooks/grails-getting-started]* - free PDF book

* *Mastering Grails trail* @ [IBM developerWorks|http://www.ibm.com/developerworks]

** *[Build your first Grails application|http://www.ibm.com/developerworks/java/library/j-grails01158]*\\\\Java programmers needn't abandon their favorite language and existing development infrastructure to adopt a modern Web development framework. In the first installment, Java expert Scott Davis introduces Grails and demonstrates how to build your first Grails  application.
** *[GORM: Funny name, serious technology|http://www.ibm.com/developerworks/java/library/j-grails02128]*\\\\Any good Web framework needs a solid persistence strategy. In this second installment of his Mastering Grails series, Scott Davis introduces the Grails Object Relational Mapping (GORM) API. See how easy it is to create relationships between tables, enforce data validation rules, and change relational databases in your Grails applications.
** *[Changing the view with Groovy Server Pages|http://www.ibm.com/developerworks/java/library/j-grails03118]*\\\\Groovy Server Pages (GSP) puts the "Web" in the Grails Web framework. In the third installment of his Mastering Grails  series, Scott Davis shows you the ins and outs of working with GSP. See how easy it is to use Grails TagLibs, mix together partial fragments of GSPs, and customize the default templates for the automatically generated (scaffolded) views.
'''

fixture {
    build {
        def admin = User.findByLogin("admin")
        
        // Required wiki pages. These have direct links from navigation panels
        // around the site.
        documentation(WikiPage,
                title: "Documentation",
                body: documentationPage)
        documentationVersion(Version,
                title: "Documentation",
                body: documentationPage,
                number: 0,
                current: documentation,
                author: admin)
        
        testimonials(WikiPage, title: "Testimonials", body: testimonialsPage)
        testimonialsVersion(Version,
                title: "Testimonials",
                body: testimonialsPage,
                number: 0,
                current: testimonials,
                author: admin)
        
        community(WikiPage, title: "Community", body: communityPage)
        communityVersion(Version,
                title: "Community",
                body: communityPage,
                number: 0,
                current: community,
                author: admin)
        
        installation(WikiPage, title: "Installation", body: installationPage)
        installationVersion(Version,
                title: "Installation",
                body: installationPage,
                number: 0,
                current: installation,
                author: admin)
        
        quickStart(WikiPage, title: "Quick Start", body: quickStartPage)
        quickStartVersion(Version,
                title: "Quick Start",
                body: quickStartPage,
                number: 0,
                current: quickStart,
                author: admin)
        
        ide(WikiPage, title: "IDE Integration", body: idePage)
        ideVersion(Version,
                title: "IDE Integration",
                body: idePage,
                number: 0,
                current: ide,
                author: admin)
        
        tutorials(WikiPage, title: "Tutorials", body: tutorialsPage)
        tutorialsVersion(Version,
                title: "Tutorials",
                body: tutorialsPage,
                number: 0,
                current: tutorials,
                author: admin)
    }
}
