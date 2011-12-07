import grails.util.Environment

// The overall database change log
databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "site-autobase"

    if (Environment.current == Environment.PRODUCTION) {
        include file: "Rhyolight/PluginPortalCommentsMigration.groovy"
        include file: "changelog-1.0.1.groovy"
        include file: "changelog-1.0.5.groovy"
        include file: "changelog-1.0.8.groovy"
        include file: "changelog-1.0.10.groovy"
        include file: "changelog-1.1.groovy"
        include file: "changelog-1.1.1.groovy"
        include file: "changelog-1.1.2.groovy"
    }
}
