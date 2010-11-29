import grails.util.Environment

// The overall database change log
databaseChangeLog(logicalFilePath:'site-autobase') { 
    if (Environment.current == Environment.PRODUCTION) {
        include('./migrations/Rhyolight/PluginPortalCommentsMigration.groovy')
        include('./migrations/changelog-1.0.1.groovy')
    }
}
