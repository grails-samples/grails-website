import grails.util.Environment
import pl.burningice.plugins.image.engines.scale.ScaleType

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts
//grails.config.locations = [ "file:./${appName}-config.groovy", "classpath:${appName}-config.groovy" ]

// local file for storing API keys at dev time
def localUserConfig = new File(System.getProperty("user.home"), ".grails-website-config.groovy")
if(localUserConfig.exists()) {
    grails.config.locations << localUserConfig.toURI().toURL()
}

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

wiki.supported.upload.types = ['image/png','image/jpg','image/jpeg','image/gif']
// location of plugins-list.xml
plugins.pluginslist = "http://plugins.grails.org/.plugin-meta/plugins-list.xml"
plugins.fisheye = "http://svn.codehaus.org/grails-plugins"
plugins.location = "http://plugins.grails.org"

plugins.forum.mail.to = "plugins-announce@nowhere.net"
plugins.forum.mail.from = "test@grails.org"

grails.plugins.disqus.shortname = "grails"

grails.mail.port = 3025

grails.mime.use.accept.header = true
grails.mime.file.extensions = false // enables the parsing of file extensions from URLs into the request format
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
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
grails.scaffolding.templates.domainSuffix="Instance"
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

grails.json.legacy.builder=false

// set per-environment serverURL stem for creating absolute links
environments {
    test {
        searchable.compassConnection = "ram://test-index"
    }
}


bi {
    WikiImage {
        prefix = 'wiki-'
        images {
            large {
                scale = [width: 600, height: 700, type: ScaleType.APPROXIMATE]
            }
            small {
                scale = [width: 100, height: 100, type: ScaleType.APPROXIMATE]
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
    compassConnection = new File(System.getProperty("user.home"), ".grails-website-searchable-index").absolutePath
    compassSettings = [
            'compass.engine.analyzer.default.type': "Snowball",
            'compass.engine.analyzer.default.name': "English",
            'compass.engine.optimizer.schedule.period': '300',
            'compass.engine.mergeFactor':'1000',
            'compass.engine.maxBufferedDocs':'1000',
            'compass.engine.ramBufferSize': '128',
            'compass.engine.useCompoundFile': 'false',
            'compass.transaction.processor': 'read_committed',
            'compass.transaction.processor.read_committed.concurrentOperations': 'false',
            'compass.transaction.lockTimeout': '30',
            'compass.transaction.lockPollInterval': '500',
            'compass.transaction.readCommitted.translog.connection': 'ram://'
            ]

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
    }
}

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
        filter {
            basicAppName='grails.org'
            filterChainDefinitions = """
/plugin/** = authcBasic[PUT]
/api/v1.0/publish/** = authcBasic[POST]
"""
        }
    }
}

springcache {
    disabled = false
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
    }
}

/*
beans {
     pluginDeployService {
         releaseUrl = "http://localhost:8085/artifactory/plugins-release-local/org/grails/plugins"
         snapshotUrl = "http://localhost:8085/artifactory/plugins-snapshot-local/org/grails/plugins"
         deployUsername = "admin"
         deployPassword = "password"
     }
}*/

grails.cache.config = {
    cache {
        name 'content'
        eternal false
        overflowToDisk true
        timeToLiveSeconds 300
        maxElementsInMemory 10000
        maxElementsOnDisk 100000
    }
    cache {
        name 'wiki'
        eternal false
        overflowToDisk true
        timeToLiveSeconds 300
        maxElementsInMemory 10000
        maxElementsOnDisk 100000
    }
    cache {
        name 'text'
        eternal false
        overflowToDisk false
        maxElementsInMemory 10000
    }
    cache {
        name 'plugin'
        eternal false
        overflowToDisk false
        maxElementsInMemory 10000
    }
    cache {
        name 'permissions'
        eternal false
        overflowToDisk false
        maxElementsInMemory 10000
    }
}

// Dummy Twitter settings.
twitter.consumerKey="E1U4T7KgPdaGFagdpbdQ"
twitter.consumerSecret="hUseBLu9jcGPEdvaco2c0yAA9tIQDZS34QsTG0GsY"
twitter.accessKey="ksdfhkasfjhksdfjhklsad"
twitter.accessSecret="test"

rest.dateFormat = "yyyy-MM-dd"
format.date = 'MMM d, yyyy'
blog.page.layout="subpage"
grails.blog.author.evaluator= {
    request.user
}

grails.gorm.default.mapping = {
    'user-type' type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
    'user-type' type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate, class: org.joda.time.LocalDate
}


// Enable these in a site-config.groovy file.
grails.plugin.databasemigration.changelogLocation = "migrations"
grails.plugin.databasemigration.updateOnStart = false
grails.plugin.databasemigration.updateOnStartFileNames = ["changelog.groovy"]

// log4j configuration
log4j = {
    if(Environment.current == Environment.PRODUCTION || System.getProperty("use.prod.logging")) {
        appenders {
            rollingFile name:"errorLog", maxFileSize:"5MB", maxBackupIndex: 10, file:"${System.getProperty('user.home')}/logs/grails-website-error.log", 'append':true, threshold:org.apache.log4j.Level.WARN
            rollingFile name:"stacktrace", maxFileSize:"5MB", maxBackupIndex: 10, file:"${System.getProperty('user.home')}/logs/grails-website-stacktrace.log", 'append':true, threshold:org.apache.log4j.Level.ALL
            console name:'stdout', threshold:org.apache.log4j.Level.WARN
        }
        root {
            warn "errorLog", "stdout"
        }
    }
    
    error   'org.hibernate', 
            'org.springframework.web.client.RestTemplate',
            'org.radeox'

    warn    'org.codehaus.groovy.grails.web.servlet',
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework'
}

// databinding config
grails.databinding.convertEmptyStringsToNull=false

// exclude some assets from default precompilation, these assets get included
grails.assets.excludes = ['**/bootstrap/**', '**/font-awesome/**', '**/grails-*.less', '**/responsive/**', '**/libs/**', '**/fancybox/**']

// read-only token for Github API access, no GH oauth scopes should be granted to the token
githubApiReadOnlyToken = ''

// cache queries by default
grails.hibernate.cache.queries = true
