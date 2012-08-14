import grails.util.Environment

// The overall database change log
databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "site-autobase"

    if (Environment.current == Environment.PRODUCTION) {
        // Initial changelog to bootstrap the database.
        include file: "init-changelog.groovy"

        // Fixes so that the following Autobase migrations work. I don't want
        // to modify them now that they're applied to the production database.
        include file: "pre-fixes-changelog.groovy"

        // Autobase plugin changes.
        include file: "Rhyolight/PluginPortalCommentsMigration.groovy"

        // More fixes that need to happen after the Autobase migrations.
        include file: "post-fixes-changelog.groovy"

        // Database Migration plugin changes start here.
        include file: "changelog-1.0.1.groovy"
        include file: "changelog-1.0.5.groovy"
        include file: "changelog-1.0.8.groovy"
        include file: "changelog-1.0.10.groovy"
        include file: "changelog-1.1.groovy"
        include file: "changelog-1.1.1.groovy"
        include file: "changelog-1.1.2.groovy"
        include file: "changelog-1.2.groovy"
        include file: "changelog-1.2.2.groovy"
        include file: "changelog-1.2.18.groovy"
        include file: "changelog-1.3.groovy"
        include file: "changelog-2.0.groovy"
    }
}
