import pl.burningice.plugins.image.engines.scale.ScaleType
import grails.converters.JSON

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts
grails.config.locations = [ "file:./${appName}-config.groovy", "classpath:${appName}-config.groovy" ]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.resources.adhoc.patterns = ["/images/*", "/css/*", "/js/*"]
grails.resources.adhoc.excludes = [ "**/*.swp" ]
grails.resources.zip.excludes = ["/**/*.png","/**/*.gif","/**/*.jpg"]

wiki.supported.upload.types = ['image/png','image/jpg','image/jpeg','image/gif']
// location of plugins-list.xml
plugins.pluginslist = "http://plugins.grails.org/.plugin-meta/plugins-list.xml"
plugins.fisheye = "http://svn.codehaus.org/grails-plugins"
plugins.location = "http://plugins.grails.org"

plugins.forum.mail.to = "plugins-announce@nowhere.net"
plugins.forum.mail.from = "test@grails.org"

grails.mail.port = 3025

// Mailgun config - set if the connection information is provided through the environment.
environments {
    cloud {
        def mailEnv = System.getenv("GRAILSORG_MAIL_SERVICE")
        if (mailEnv) {
            def mailConf = JSON.parse(mailEnv)
            grails {
                mail {
                    host = mailConf.url
                    username = mailConf.username
                    password = mailConf.password
                }
            }
        }
    }
}

// Application name for Cloud Foundry
grails.plugin.cloudfoundry.appname = "grails-website"

grails.mime.use.accept.header = true
grails.mime.file.extensions = false // enables the parsing of file extensions from URLs into the request format
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text-plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"
grails.app.context = "/"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

grails.json.legacy.builder=false

// set per-environment serverURL stem for creating absolute links
environments {
    test {
        searchable.compassConnection = "ram://test-index"
    }
    development {
        download.versions = ["1.4 beta", "1.3", "1.2"]
    }
}


bi {
    WebSite {
        prefix = 'website-'
        images {
            large {
                scale = [width: 300, height: 280, type: ScaleType.APPROXIMATE]
            }
        }
        constraints {
            nullable = true
            maxSize = 2 * 1024 * 1024
            contentType = ['image/gif', 'image/png', 'image/jpeg']
        }
    }

    WikiImage {
        prefix = 'wiki-'
        images {
            large {
                scale = [width: 600, height: 700, type: ScaleType.APPROXIMATE]
            }
        }
        constraints {
            nullable = true
            maxSize = 2 * 1024 * 1024
            contentType = ['image/gif', 'image/png', 'image/jpeg']
        }
    }
    
}


searchable {
    compassConnection = null
    compassSettings = [
            'compass.engine.analyzer.default.type': "Snowball",
            'compass.engine.analyzer.default.name': "English"]
    defaultExcludedProperties = ["password"]
    defaultFormats = [:]
    defaultMethodOptions = [
        search: [reload: false, escape: false, offset: 0, max: 10, defaultOperator: "and"],
        suggestQuery: [userFriendly: true]
    ]
    mirrorChanges = false
    bulkIndexOnStartup = false

    domain {
        comment = {
            root false
            only = ["body"]
            body name: "comment"
        }
        tag = {
            root false
            name name: "tag"
        }
        screencast = [only: ["title", "description"]]
    }
}

security.shiro.filter.config = """\
[main]
myAuth = org.grails.auth.RestBasicAuthFilter
myAuth.applicationName = grails.org

[urls]
/plugin/** = myAuth[PUT]
/api/v1.0/publish/** = myAuth[POST]
"""

oauth {
    providers {
        twitter {
            api = org.scribe.builder.api.TwitterApi
            successUri = '/oauth/success?provider=twitter'
            failureUri = '/unauthorized'
            key = 'twitter-key'
            secret = 'twitter-secret'
            callback = "http://localhost:8080/oauth/twitter/callback"
        }
        facebook {
            api = org.scribe.builder.api.FacebookApi
            successUri = '/oauth/success?provider=facebook'
            failureUri = '/unauthorized'
            key = 'google-key'
            secret = 'google-secret'
            callback = "http://localhost:8080/oauth/google/callback"
        }
        google {
            api = org.scribe.builder.api.GoogleApi
            successUri = '/oauth/success?provider=google'
            failureUri = '/unauthorized'
            scope = 'https://www.googleapis.com/auth/userinfo.email'
            key = 'google-key'
            secret = 'google-secret'
            callback = "http://localhost:8080/oauth/google/callback"
        }
//        github {
//            api = org.scribe.builder.api.GitHubApi
//            successUri = '/oauth/success?provider=facebook'
//            failureUri = '/unauthorized'
//        }
    }
}

security {
    shiro {
        oauth {
            linkAccountUrl = "/oauth/linkaccount"
        }
    }
}

springcache {
    disabled = true
    defaults {
        // set default cache properties that will apply to all caches that do not override them
        eternal = false
        diskPersistent = false
        overflowToDisk = false
    }
    caches {

        contentCache {
            // set any properties unique to this cache
            timeToLive = 300
            diskPersistent = false
            overflowToDisk = false
        }
        pluginCache {
            // set any properties unique to this cache
            timeToLive = 300
            diskPersistent = false
            overflowToDisk = false
        }
        downloadCache {
            // set any properties unique to this cache
            timeToLive = 300
            diskPersistent = false
            overflowToDisk = false
        }
    }
}

grails.cache.config = {
    cache {
        name 'content'
        eternal false
        overflowToDisk false
        maxElementsInMemory 10000
        maxElementsOnDisk 10000000
    }
    cache {
        name 'wiki'
    }
    cache {
        name 'pluginMetaList'
    }
    cache {
        name 'permissions'
    }
}

// Dummy Twitter settings.
twitter4j.oauth.consumerKey="dummy"
twitter4j.oauth.consumerSecret="notVerySecret"
twitter4j.oauth.accessKey="ksdfhkasfjhksdfjhklsad"
twitter4j.oauth.accessSecret="test"


rest.dateFormat = "yyyy-MM-dd"
format.date = 'MMM d, yyyy'
screencasts.page.layout="subpage"
blog.page.layout="subpage"
grails.blog.author.evaluator= {
    request.user
}

grails.gorm.default.mapping = {
    'user-type' type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
    'user-type' type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate, class: org.joda.time.LocalDate
}

rabbitmq {
    connectionfactory {
        username = 'guest'
        password = 'guest'
        hostname = 'localhost'
    }

    queues = {
        exchange name: 'website.eventbus', type: fanout, durable: false, autoDelete: true
    }
    
}

// Enable these in a site-config.groovy file.
/*
grails.plugin.databasemigration.changelogLocation = "migrations"
grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ["changelog.groovy"]
*/

// log4j configuration
log4j = {
    appenders {
        appender new org.springframework.amqp.rabbit.log4j.AmqpAppender(
                name: "amqp",
                exchangeName: "amq.topic",
                exchangeType: "topic",
                routingKeyPattern: "logs.grails-website",
                applicationId: "grails-website")
    
    }

    root {
        info 'stdout', 'amqp'
    }

    off     'grails.app.service.org.grails.plugin.resource'

    error   'org.hibernate'
    
    warn    'org.codehaus.groovy.grails.web.servlet',
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework'
            
    //debug 'grails.app.services'
    //debug 'grails.app.controllers'
    //debug 'org.grails.auth'
    //trace 'org.apache.shiro'
    //debug 'org.codehaus.groovy.grails.web.mapping'
}

ConfigLoader.addEntries(loadJson(fetchJson()), this)
def fetchJson() { return System.getenv("GRAILS_APP_CONFIG") }
def loadJson(content) { return content ? grails.converters.JSON.parse(content) : [:] }
