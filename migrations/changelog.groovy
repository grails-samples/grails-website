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

        // See end of the file for why this is here...
        include file: "changelog-1.3.groovy"
        include file: "changelog-1.2.18.groovy"

        include file: "changelog-2.0.groovy"
    }
}

/*
  A Story of Woe

  For anyone dealing with these database migrations, I think an explanation is in order. You
  will find all sorts of bizarre tricks hidden in the pre-2.0 changelogs and it behoves you
  to understand why they are there.

  The first thing you need to know is that the early production database for grails.org was
  not managed via database migration. Just Hibernate's 'update' auto-ddl setting. Of course,
  it was later discovered that not all, and in fact surprisingly few, refactorings are supported
  by Hibernate's update. That's when Autobase was introduced.

  Autobase was designed to work in conjunction with Hibernate's update support, providing
  the extra refactorings that were needed. Unfortunately, you couldn't just take the app and
  run it against an empty database because Hibernate 'update' would just use the latest GORM
  model and the Autobase refactorings would fail.

  When the Database Migration plugin came on the scene, it seemed it would solve all our
  problems. And it would have done. If we hadn't made more mistakes. So init-changelog.groovy
  was created so that the initial schema could be created in an empty database with all the
  subsequent refactorings working. And it did work. And we were happy. Until we decided to
  finally switch the MySQL database from MyISAM to InnoDB. Uh-oh.

  The switch highlighted some problems with existing refactorings, which meant that they
  wouldn't work against an empty database. And if you started with an empty database in a
  MySQL server set to InnoDB by default, even more things stopped working. Unfortuantely,
  most of the changesets had already been run against the production database so changing
  their IDs or modifying them simply didn't work.

  To add to the fun and games, a development changelog, changelog-1.3.groovy, got executed
  against the production database - not clever. But when changelog-1.2.18.groovy was created,
  the assumption was that it had already run. And of course the migrations worked against
  the existing database. But they didn't work against an empty one. So the former had to
  be moved before the latter, even though it's number is higher. Such is life.

  Going back to the InnoDB changes, there are several changesets that create indexes, foreign
  keys, etc. just so that subsequent changesets that remove those things actually work. Of
  course, those changesets haven't run against the production database so all those indexes
  (and tables!) will be created again. Fortunately, we can mark all those changesets as
  having already been run, and this currently only affects two databases that we control.

  Going forward from here, everything should become much saner as the changesets required
  to get an empty database up to the latest version are now in sync with the production
  servers.

  If there is one lesson to learn from this story it's that you should start using a database
  refactoring tool _as soon as you hit production_! And switch of Hibernate's 'update' at
  the same time. If we'd done that, none of this sorry story would have happened.
*/
