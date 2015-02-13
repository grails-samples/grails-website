[![Build Status](https://travis-ci.org/grails-samples/grails-website.svg)](https://travis-ci.org/grails-samples/grails-website)

# Grails.org (including Wiki)

This is the Grails project for [Grails.org](http://www.grails.org). Please fork and submit pull requests for any changes made, and don't forget to use the appropriate version of Grails as specified in [application.properties](https://github.com/grails-samples/grails-website/blob/master/application.properties). Alternatively, you can use the Grails wrapper script in the root of the project, `grailsw`.

## Quick Start

Once you have cloned the repository, you can immediately try it out with

    ./grailsw compile
    ./grailsw -Dreindex=1 run-app

Ignore the warnings about not finding the site-config.groovy files - they are expected.

The above command starts the application in the development environment with some sample data. Note that the application depends on quite a few plugins so you will have to wait for those to be downloaded and installed. Also be aware that the plugins are installed in a sub-directory of the project called `plugins`. 

The default development and test environments are configured to use an in-memory H2 database, as you can see if you take a look at the [DataSource.groovy](https://github.com/grails-samples/grails-website/blob/master/grails-app/conf/DataSource.groovy) file. The sample data comes from [some fixtures](https://github.com/grails-samples/grails-website/tree/master/fixtures/) that are loaded by [BootStrap.groovy](https://github.com/grails-samples/grails-website/blob/master/grails-app/conf/BootStrap.groovy). If you want to add or otherwise modify the sample data, simply modify the fixtures! Note that these fixtures require the [Fixtures plugin](http://grails.org/plugin/fixtures).

This setup is fine for most development, but if you want to modify the data model in any way you will need to understand how the application runs in production.

## Production Environment

The production environment uses a MySQL database, for which you need to configure the data source. The easiest way to do this is to create a `site-config.groovy` file in the root of the project that contains the data source configuration for MySQL:

    dataSource {
        pooled = true
        driverClassName = "com.mysql.jdbc.Driver"       
        url = "jdbc:mysql://localhost/grails"
        username = "root"
        password = ""
        dbCreate = null
    }

You don't have to use the credentials and database name specified above, but make sure that the database you specify exists because it won't be created automatically! Also note that the `dbCreate` setting is important as we want to disable Hibernate's automatic schema creation.

You'll then want to make sure the database migration plugin's autorun feature is enabled in your 'site-config.groovy' file so that the necessary tables are created in your database:
    
    grails.plugin.databasemigration.updateOnStart = true

With the data source configured you can just start the application:

    ./grailsw -Dinitial.admin.password=changeit -Dload.fixtures=true prod run-app

We start it in the production environment because that's required for the database migrations to run. If you run without the 'prod', your database will be empty and the application will fail to start. Remember, we're not using Hibernate's automatic schema creation.

Once you have started the application using the above command line, you can subsequently run it using the much simpler

    ./grailsw run-app

or 

    ./grailsw prod run-app

Since the data source in site-config.groovy isn't tied to a particular environment, both development and production will run against the configured MySQL database.

### Changing the Domain Model

If you make any changes to the domain model, such as adding or removing domain classes or domain class properties, then you will have to create an associated changelog. This application uses the [Database Migration plugin](http://grails.org/plugin/database-migration) and its migration scripts are kept under the [migrations directory](https://github.com/grails-samples/grails-website/tree/master/migrations/). The policy is to have a separate changelog file for each new version of the application, and each commit to a changelog should have its own `changeSet` definition (or more than one). Any changes to a changelog should be committed with the corresponding domain model changes!

More to come...

## Testing

Documentation to follow.
