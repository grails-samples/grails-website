// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts
grails.config.locations = [ "file:./${appName}-config.groovy", "classpath:${appName}-config.groovy" ]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

wiki.supported.upload.types = ['image/png','image/jpg','image/jpeg','image/gif']
// location of plugins-list.xml
plugins.pluginslist = "http://plugins.grails.org/.plugin-meta/plugins-list.xml"
plugins.fisheye = "http://fisheye.codehaus.org/browse/grails-plugins"
plugins.location = "http://plugins.grails.org"

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

// Load GSPs from external location so that they can easily be updated. The
// actual location of the views is specified in the site-config.properties
// file - see the beginning of this file for its location.
grails.gsp.enable.reload = true

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

grails.json.legacy.builder=false

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.grails.org"
		grails.screencasts.upload.directory = "/var/www/domains/grails.org/www/htdocs/dist/screencasts"
    }
    test {
        grails.serverURL = "http://www.grails.org"
    }
    development {
        grails.serverURL = "http://localhost:8080"
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

format.date = 'MMM d, yyyy'
screencasts.page.layout="subpage"
blog.page.layout="subpage"
blog.author.evaluator= {
	request.user
}

// log4j configuration
log4j = {
    warn   'org.codehaus.groovy.grails.web.servlet',
           'org.codehaus.groovy.grails.web.pages', //  GSP
	   'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	   'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
	   'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
	   'org.springframework',
	   'org.hibernate'

}
