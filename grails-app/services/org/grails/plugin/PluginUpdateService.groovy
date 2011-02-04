package org.grails.plugin

import groovyx.net.http.HTTPBuilder
import org.grails.wiki.WikiPage
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.transaction.annotation.Transactional

/**
 * Spring event listener that picks up updates to plugins and synchronises
 * the changes with the database. This involves accessing the repository
 * where the plugin was published and reading the POM and plugin descriptor.
 */
class PluginUpdateService implements ApplicationListener<PluginUpdateEvent> {
    static transactional = false

    protected int twitterLimit = 140

    def shortenService
    def twitterService
    def grailsApplication

    /**
     * <p>Triggered whenever something publishes a plugin update event to the Spring
     * application context.</p>
     *
     * <p>Note: The @Transactional annotation is used due to a bug in the Spring
     * Events plugin - http://jira.codehaus.org/browse/GRAILSPLUGINS-2552 </p>
     */
    @Transactional
    void onApplicationEvent(PluginUpdateEvent event) {
        log.info "Updating information for plugin ${event.name}, version ${event.version}"

        // Check that the given repository URL is valid.
        def baseUrl
        try {
            baseUrl = event.repoUrl.toURL()
        }
        catch (MalformedURLException ex) {
            // If the repository URL is invalid, there's no point processing
            // this plugin update.
            log.error "Invalid repository URL provided with plugin '${event.name}': ${event.repoUrl}"
            return
        }

        // Rectify the URL if it doesn't have a trailing slash.
        if (!baseUrl.path.endsWith('/')) {
            baseUrl = new URL(baseUrl.toString() + '/')
        }

        log.debug "Base repository URL is ${baseUrl}"

        // We either need to create a new Plugin instance or update the
        // existing one. Since we already have the version, we can deal
        // with that too.
        def plugin = fetchOrCreatePluginInstance(event.name, event.version)
        def isNewVersion = plugin.currentRelease != event.version
        if (!event.snapshot) {
            plugin.currentRelease = event.version
        }

        // We may be looking at either a Maven or a Subversion repository,
        // both of which have different directory structures. Here we test
        // which type of repository we have.
        def mavenUrl = new URL(baseUrl, "${event.group.replace('.', '/')}/${event.name}/${event.version}/")
        try {
            log.debug "Trying Maven URL: ${mavenUrl}"
            mavenUrl.text
            baseUrl = mavenUrl
        }
        catch (FileNotFoundException ex) {
            // 404 on the Maven URL.
            baseUrl = new URL(baseUrl, "grails-${event.name}/tags/RELEASE_${event.version?.replace('.', '_')}/")
        }

        log.debug "Fetching plugin information from ${baseUrl}"

        // Pull in the POM and parse it.
        def parser = new XmlSlurper()
        def pomUrl = new URL(baseUrl, "${plugin.name}-${event.version}.pom")
        def xml = null
        pomUrl.withReader { reader ->
            xml = parser.parse(reader)
        }
        
        // Update the Plugin instance with the information from the POM.
        plugin.groupId = xml.groupId.text()
        plugin.title = xml.name.text()
        plugin.summary = xml.description.text()
        plugin.documentationUrl = xml.url.text()
        plugin.author = xml.developers.developer[0].name.text()
        plugin.authorEmail = xml.developers.developer[0].email.text()
        plugin.scmUrl = xml.scm.url.text()

        // Now do the same with the XML plugin descriptor.
        def descUrl = new URL(baseUrl, "${plugin.name}-${event.version}-plugin.xml")
        descUrl.withReader { reader ->
            xml = parser.parse(reader)
        }

        plugin.grailsVersion = xml.@grailsVersion.text()

        if (log.debugEnabled) {
            log.debug """\
                Updated plugin info:
                  name         = ${plugin.name}
                  version      = ${plugin.currentRelease}
                  groupId      = ${plugin.groupId}
                  title        = ${plugin.title}
                  docs URL     = ${plugin.documentationUrl}
                  author name  = ${plugin.author}
                  author email = ${plugin.authorEmail}
                """.stripIndent()
        }

        // Now we can update the plugin instance in the database.
        plugin.save(failOnError: true)

        // Assuming the instance saved OK, we can tweet the release if it's
        // a new version.
        if (isNewVersion) tweetRelease(plugin)
        else log.info "Not a new plugin release - won't tweet"
    }

    /**
     * Fetches the plugin instance for the given name from the database
     * and returns it. If the plugin isn't in the database yet, this
     * methods creates a new instance and returns that. Note that the
     * new instance is not saved and only has the name set.
     * @param pluginName The name of the plugin.
     * @param version If the plugin needs to be created, it's current version
     * is set to this value.
     */
    @Transactional
    Plugin fetchOrCreatePluginInstance(String pluginName, String version) {
        def plugin = Plugin.findByName(pluginName)
        if (!plugin) {
            plugin = new Plugin(name: pluginName, currentRelease: version, downloadUrl: "not provided")

            // Add the wiki pages for this new plugin.
            Plugin.WIKIS.each { wiki ->
                def body = ''
                if (wiki == 'installation') {
                    body = "{code}grails install-plugin ${plugin.name}{code}"
                }

                // Saves don't cascade from the plugin to the wiki pages, so
                // we have to save them before saving the plugin.
                def wikiPage = new WikiPage(title:"plugin-${plugin.name}-${wiki}", body:body)
                wikiPage.save()

                plugin."$wiki" = wikiPage
            }
        }

        return plugin
    }

    /**
     * Sends a tweet to @grailsplugins with details of the new release.
     * @param plugin A plugin instance with 'name', 'title' and 'currentRelease'
     * properties.
     */
    void tweetRelease(plugin) {
        def msg = "${plugin.title} ${plugin.currentRelease} released: "
        def url = baseUrl + "plugin/${plugin.name}"

        // Check that the message with standard URL does not exceed the
        // Twitter length limit.
        if (exceedsTwitterLimit(msg, url)) url = shortenUrl(url)

        // If the message length is still over the Twitter length, we must summarize
        // the message.
        if (exceedsTwitterLimit(msg, url)) msg = summarize(msg, twitterLimit - url.size())

        log.info "Tweeting the plugin release"
        twitterService.updateStatus(msg + url)
    }

    private getBaseUrl() {
        return normalize(grailsApplication.config?.grails?.serverURL)
    }

    private normalize(url) {
        return url.endsWith('/') ? url : url + '/'
    }

    private shortenUrl(url) {
        return shortenService.shortenUrl(url)
    }

    private exceedsTwitterLimit(Object[] strs) {
        return strs*.size().sum() > twitterLimit
    }

    private summarize(str, limit) {
        def chopPoint = limit.intdiv(2) - 2
        return str[0..<chopPoint] + "..." + str[(-chopPoint)..-1]
    }
}
