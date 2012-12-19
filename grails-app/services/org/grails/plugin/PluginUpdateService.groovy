package org.grails.plugin

import groovyx.net.http.HTTPBuilder
import org.grails.auth.User
import org.grails.meta.UserInfo
import org.joda.time.DateTime
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

    def cacheService
    def shortenService
    def twitterService
    def mailService
    def grailsApplication
    def pluginService

    /**
     * <p>Triggered whenever something publishes a plugin update event to the Spring
     * application context.</p>
     *
     * <p>Note: The @Transactional annotation is used due to a bug in the Spring
     * Events plugin - http://jira.grails.org/browse/GPSPRINGEVENTS-2 </p>
     */
    @Transactional(rollbackFor = Exception)
    void onApplicationEvent(PluginUpdateEvent event) {
        log.info "Updating information for plugin ${event.name}, version ${event.version}${event.snapshot ? ' (snapshot)' : ''}"

        // Check that the given repository URL is valid.
        def pluginUpdater
        try {
            pluginUpdater = new PluginUpdater(event.version, event.group, event.repoUrl?.toString(), event.snapshot)
        }
        catch (MalformedURLException ex) {
            // If the repository URL is invalid, there's no point processing
            // this plugin update.
            log.error "Invalid repository URL provided with plugin '${event.name}': ${event.repoUrl}"
            return
        }

        log.debug "Base repository URL is ${pluginUpdater.baseUrl}"

        // We either need to create a new Plugin instance or update the
        // existing one. Since we already have the version, we can deal
        // with that too.
        def plugin = fetchOrCreatePluginInstance(event.name, event.version)
        Plugin.withSession { session ->
            try {
                session.flushMode = org.hibernate.FlushMode.MANUAL
                pluginUpdater.updatePlugin(plugin)
                pluginService.savePlugin plugin, true
                pluginUpdater.saveRelease()

                if (pluginUpdater.newVersion && !pluginUpdater.snapshot) {
                    announceRelease plugin
                }
                else log.info "Not a new plugin release - won't tweet"

            }
            finally {
                session.flushMode = org.hibernate.FlushMode.AUTO
            }
        }

        // The master plugin list will need regenerating.
        cacheService?.removePluginList()
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
            log.debug "Creating new plugin instance for $pluginName $version"
            plugin = new Plugin(name: pluginName, currentRelease: version, downloadUrl: "not provided")

            pluginService.initNewPlugin(plugin, User.findByLogin("admin"))

        }

        return plugin
    }
    
    void announceRelease(plugin, version = null) {
        try {
            def pluginUrl = siteBaseUrl + "plugin/${plugin.name}"
            announceOnPluginForum(plugin, version, pluginUrl)
            tweetRelease(plugin, version, pluginUrl)
        }
        catch (Exception ex) {
            // Don't let an exception roll back changes the database. A user can
            // do a --ping-only release to get the announcements if necessary.
            log.error "Failed to announce plugin ${plugin.name} ${version}", ex
        }
    }
    
    /**
     * Sends a tweet to @grailsplugins with details of the new release.
     * @param plugin A plugin instance with 'name', 'title' and 'currentRelease'
     * @param version The version of the plugin
     * @param url The URL of the plugin
     * properties.
     */
    void tweetRelease(plugin, version, url) {
        def msg = "${plugin.title} ${version ?: plugin.currentRelease} released: "

        // Check that the message with standard URL does not exceed the
        // Twitter length limit.
        
	if (exceedsTwitterLimit(msg, url)) url = shortenUrl(url)

        // If the message length is still over the Twitter length, we must summarize
        // the message.

        if (exceedsTwitterLimit(msg, url)) msg = summarize(msg, twitterLimit - url.size())

        log.info "Tweeting the plugin release. Message: $msg"
	
        twitterService.updateStatus(msg + url)
    }
    
    void announceOnPluginForum(plugin, version, url) {
        def mailConfig = grailsApplication.config.plugins.forum.mail
        def toAddress = mailConfig.to
        def fromAddress = mailConfig.from
        
        mailService.sendMail {
            to toAddress
            from fromAddress
            subject "${plugin.title} ${version ?: plugin.currentRelease} released"
            html view: "/mail/pluginRelease", model: [plugin: plugin, version: version, url: url]
        }
    }

    private getSiteBaseUrl() {
        return normalize(grailsApplication.config?.grails?.serverURL ?: 'http://localhost:8080/')
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

class PluginUpdater {

    private static final DEFAULT_REPOSITORIES = [
            "http://plugins.grails.org",
            "http://grails.org/plugins",
            "http://repo.grails.org/grails/plugins/",
            "http://repo.grails.org/grails/core/",
            "http://svn.codehaus.org/grails/trunk/grails-plugins",
            "http://repo1.maven.org/maven2/" ]

    private plugin
    private String groupId
    private String version
    private boolean isSnapshot
    private URL baseUrl

    private boolean isNewVersion
    private URL baseDownloadUrl
    private String filename
    private String extension
    private pom

    PluginUpdater(String version, String groupId, String baseUrl, boolean isSnapshot) throws MalformedURLException {
        this.version = version
        this.groupId = groupId
        this.isSnapshot = isSnapshot
        this.baseUrl = validateAndFixUrl(baseUrl)
    }

    def getPlugin() { return plugin }
    URL getBaseUrl() { return baseUrl }
    boolean isSnapshot() { return isSnapshot }

    boolean isNewVersion() {
        checkForPlugin()
        return isNewVersion
    }

    void updatePlugin(plugin) {
        this.plugin = plugin
        isNewVersion = !plugin.id || plugin.currentRelease != version

        // Work out what the base URL is for accessing the plugin's binary
        // package, POM, and XML descriptor.
        evaluateDownloadInfo()

        // We need to extract various bits of info from the POM, but right
        // now we need it to find out what the file extension is for this
        // plugin. "jar" for binary plugins, "zip" for source ones.
        pom = loadPom()
        filename = filename + "." + pom.packaging.text()

       

        if (!isSnapshot) {
            // Only update the plugin portal page with the new info if this
            // is a non-snapshot release.
            updatePluginProperties()
        }
    }

    void saveRelease() {
        // Check whether there are any pending releases. If yes and the most
        // recent one failed, we shouldn't add a PluginRelease record.
        /*
        def pendingReleases = PendingRelease.where {
            pluginName == plugin.name && pluginVersion == version
        }.list(sort: "dateCreated", order: "asc")
        */
        def pendingReleases = PendingRelease.findAllByPluginNameAndPluginVersion(plugin.name, version, [sort: "dateCreated", order: "asc"])

        if (pendingReleases && pendingReleases[-1].status != ReleaseStatus.COMPLETED) {
            throw new RuntimeException("Cannot create release for plugin '${plugin.name}' " +
                    "version '${version}' as the deployment status is ${pendingReleases[-1].status}")
        }

        // Now create or update the plugin release. Updates are only applicable
        // for snapshots where the version has already been published at least
        // once.
//        def pr = PluginRelease.where { plugin == plugin && releaseVersion == version }.get()
        def pr = PluginRelease.findByPluginAndReleaseVersion(plugin, version)
        if (!pr) {
            pr = new PluginRelease(
                    plugin: plugin,
                    releaseVersion: version,
                    downloadUrl: baseDownloadUrl.toURI().resolve(filename).toString(),
                    isSnapshot: isSnapshot)
        }
        pr.releaseDate = new DateTime()
        pr.save(failOnError: true, flush:true)

        // Clear out associated pending releases that were created on publish.
        PluginRelease.withSession { session ->
            session.flushMode = org.hibernate.FlushMode.AUTO
            PendingRelease.deleteAll(pendingReleases)
            session.flushMode = org.hibernate.FlushMode.MANUAL
        }
        
    }

    /**
     * Update the plugin's properties in the database, so that its portal page
     * displays the latest information.
     */
    protected void updatePluginProperties() {
        // Update the current release version of the plugin first.
        plugin.currentRelease = version
        plugin.lastReleased = new DateTime()

        // Update the Plugin instance with the information from the POM.
        plugin.with {
            groupId = pom.groupId.text()
            title = pom.name.text()
            summary = pom.description.text()
            documentationUrl = pom.url.text()
            organization = pom.organization.name.text()
            organizationUrl = pom.organization.url.text()
            scmUrl = pom.scm.url.text()
            issuesUrl = pom.issueManagement.url.text()
        }

        // Now do the same with the XML plugin descriptor to get the Grails
        // version range for the plugin.
        def xml = loadPluginXml()

        addAuthors pom.developers
        addLicenses pom.licenses

        plugin.grailsVersion = xml.@grailsVersion.text()

        // Fetch any custom repositories that may be needed by this plugin.
        def customRepoUrls = xml.repositories.repository.@url*.text().findAll { !(it in DEFAULT_REPOSITORIES) }
        addCustomRepositories customRepoUrls

        // Set the download URL for the plugin to the appropriate binary in the
        // repository, whether it be a Maven or Subversion one.
        plugin.downloadUrl = baseDownloadUrl.toURI().resolve(filename).toString()

        if (log.debugEnabled) {
            log.debug """\
                Updated plugin info:
                  name          = ${plugin.name}
                  version       = ${plugin.currentRelease}
                  groupId       = ${plugin.groupId}
                  title         = ${plugin.title}
                  docs URL      = ${plugin.documentationUrl}
                  author names  = ${plugin.authors.collect {it.name}.join(', ')}
                  author emails = ${plugin.authors.collect {it.email}.join(', ')}
                """.stripIndent()
        }
    }

    /**
     * Returns a tuple of the base URL for the plugin version, and the filename
     * of the binary (minus its extension since we don't know whether it's a JAR
     * or zip at this point). Requires internet access since it needs to check
     * the existence of paths in the repository to determine whether it's a
     * Maven-compatible one or a legacy Subversion one.
     */
    protected void evaluateDownloadInfo() {
        // We may be looking at either a Maven or a Subversion repository,
        // both of which have different directory structures. Here we test
        // which type of repository we have.
        def mavenUrl = new URL(baseUrl, "${groupId.replace('.', '/')}/${plugin.name}/${version}/")
        filename = "${plugin.name}-${version}"
        try {
            log.debug "Trying Maven URL: ${mavenUrl}"
            mavenUrl.text
            baseDownloadUrl = mavenUrl
        }
        catch (FileNotFoundException ex) {
            // 404 on the Maven URL, so use a Subversion repository URL instead.
            baseDownloadUrl = new URL(baseUrl, "grails-${plugin.name}/tags/RELEASE_${version?.replace('.', '_')}/")
            filename = "grails-" + filename
        }
    }

    protected addAuthors(pomDevelopersXml) {
        plugin.authors?.clear()
        for (developer in pomDevelopersXml.developer) {
            def email = developer.email.text()

            def user = email ? UserInfo.findOrCreateWhere(email: email) : new UserInfo()
            if (!user.name) {
                user.name = developer.name.text()
            }
            user.save(failOnError: true)
            def existing = plugin.authors?.find { it.name == user.name}
            if(!existing)
                plugin.addToAuthors(user)
        }
    }

    protected addLicenses(pomLicensesXml) {
        for (license in pomLicensesXml.license) {
            def l = License.findOrSaveWhere(name: license.name.text(), url: license.url.text()).save()
            if (!l.hasErrors()) plugin.addToLicenses(l)
            else {
                log.warn "Invalid license declared for plugin '${plugin.name}': " +
                        "${license.name.text()}(${license.url.text()})"
            }
        }
    }

    protected addCustomRepositories(repoUrls) {
        // No need to do anything if there the custom repositories
        // haven't changed.
        if (repoUrls == plugin.mavenRepositories) return

        // Take the simple approach: clear the list and re-add
        // all declared URLs.
        if (plugin.mavenRepositories == null) plugin.mavenRepositories = []

        plugin.mavenRepositories.clear()
        plugin.mavenRepositories.addAll repoUrls
    }

    /**
     * Reads the POM for the given plugin & version from the given URL and
     * returns the slurped content, i.e. a GPath result.
     */
    protected loadPom() {
        def pomUrl = new URL(baseDownloadUrl, "${plugin.name}-${version}.pom")
        return pomUrl.withReader("UTF-8") { reader ->
             new XmlSlurper().parse(reader)
        }
    }

    /**
     * Reads the XML plugin descriptor for the given plugin & version from the
     * given URL and returns the slurped content, i.e. it's a GPath result.
     */
    protected loadPluginXml() {
        def descUrl = new URL(baseDownloadUrl, "${plugin.name}-${version}-plugin.xml")
        return descUrl.withReader("UTF-8") { reader ->
            new XmlSlurper().parse(reader)
        }
    }

    private validateAndFixUrl(String url) throws MalformedURLException {
        // Check that the given repository URL is valid. May throw an exception!
        def tmpUrl = url.toURL()

        // Rectify the URL if it doesn't have a trailing slash.
        if (!tmpUrl.path.endsWith('/')) {
            tmpUrl = new URL(tmpUrl.toString() + '/')
        }

        return tmpUrl
    }

    private checkForPlugin() {
        if (!plugin) throw new IllegalStateException("You must call setPlugin() before using this method.")
    }
}
