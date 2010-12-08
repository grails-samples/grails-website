package org.grails.plugin

import org.springframework.context.ApplicationEvent

/**
 * Application event that notifies the plugin portal that the details of
 * a particular plugin need updating, perhaps due to a new release.
 */
class PluginUpdateEvent extends ApplicationEvent {
    /** The base name of the plugin - e.g. "shiro", not "grails-shiro" or "Shiro Plugin". */
    final String name

    /** The version of the plugin from which to get the details. */
    final String version

    /** The group ID for the plugin artifact. */
    final String group

    /** Whether this is a snapshot release or not. */
    final boolean snapshot

    /**
     * The base URI of the Maven or Subversion repository where the plugin
     * is deployed. Ideally the path should have a trailing slash, but it's
     * not essential.
     */
    final URI repoUrl

    PluginUpdateEvent(source, String name, String version, String group, boolean snapshot, URI repoUrl) {
        super(source)
        this.name = name
        this.version = version
        this.group = group
        this.snapshot = snapshot
        this.repoUrl = repoUrl
    }
}
