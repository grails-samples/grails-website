package org.grails.wiki

import org.springframework.context.ApplicationEvent

/**
 * Application event indicating that a wiki page has been modified.
 */
class WikiPageUpdateEvent extends ApplicationEvent {
    /** The title of the wiki page. */
    final String title

    /** The fully qualified name of the class representing the wiki page. */
    final String className

    WikiPageUpdateEvent(source, String title, String className) {
        super(source)
        this.title = title
        this.className = className
    }
}
