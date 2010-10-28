// The overall database change log
databaseChangeLog(logicalFilePath:'site-autobase') { 
  include('./migrations/Rhyolight/PluginPortalCommentsMigration.groovy')
  include('./migrations/changelog-1.0.1.groovy')
}
