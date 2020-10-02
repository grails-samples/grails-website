[![Build Status](https://travis-ci.org/grails-samples/grails-website.svg)](https://travis-ci.org/grails-samples/grails-website)

# Grails.org (including Wiki) - An Amazing Project

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

## Testing plugin publishing locally

### Setting up local test environment

Add these release plugin settings to ~/.grails/settings.groovy
```
grails.project.portal.my.url = "http://localhost:8080/plugin/"
grails.project.portal.my.username = "admin"
grails.project.portal.my.password = "changeit"

grails.project.repos.myRepo.url = "http://localhost:8080/plugins"
grails.project.repos.myRepo.type = "grailsCentral"
grails.project.repos.myRepo.username = "admin"
grails.project.repos.myRepo.password = "changeit"
grails.project.repos.myRepo.portal = "my"
```

Download Artifactory zip from http://www.jfrog.com/open-source/ and unzip it to some location.
Modify tomcat/conf/server.xml file before starting Artifactory. Change the port from 8081 to 8085 .
```
        <Connector port="8085"/>
```
Then start Artifactory.
```
./bin/artifactory.sh start
```

Add a file site-config.groovy to the grails-website directory with this content:
```
grails.serverURL='http://localhost:8080'
beans {
     pluginDeployService {
         releaseUrl = "http://localhost:8085/artifactory/plugins-release-local/org/grails/plugins"
         snapshotUrl = "http://localhost:8085/artifactory/plugins-snapshot-local/org/grails/plugins"
         deployUsername = "admin"
         deployPassword = "password"
     }
}
```

### Testing plugin publishing

Start grails-website with run-app:
```grails run-app```

Start artifactory if it's not already running:
```ARTIFACTORY_INSTALL_DIR/bin/artifactory.sh start```

Publish some plugin (f.e. mail) that already exists in the repository (so that it's already approved):
```grails publish-plugin --repository=myRepo --portal=my --stacktrace```


## Production deployments

### Deploying to production

Production deployments are automated with Travis CI. Travis will deploy the app to production when a tag starting with ```"prod_"``` gets pushed to Github.
Blue-Green deployment model is used to do zero-downtime upgrades. Currently there isn't any downtime, but the sessions aren't replicated so they are lost.
By default the app will get deployed to the inactive node and that won't get activated by default.
When the tag starting with ```"prod_"``` also ends with ```"_activate"```, the app will get activated when the app has started up successfully.

Here is an example of ```cf apps``` output for the current production environment.
```
Getting apps in org grails-org / space production as someuser@somedomain...
OK

name                            requested state   instances   memory   disk   urls
plugins-grails-org-prod-blue    started           1/1         2G       1G     prod-blue.grails.org, plugins-grails-org-prod-blue.cfapps.io
plugins-grails-org-prod-green   started           1/1         2G       1G     plugins-grails-org-prod.cfapps.io, prod.grails.org, prod-green.grails.org, plugins-grails-org-prod-green.cfapps.io, grails.org, www.grails.org, plugins.grails.org
```

"Activation" means in this case the process of switching the URL routes prod.grails.org, grails.org, www.grails.org and plugins.grails.org links to point to the recently updated app. A script ```cf-swap-blue-green.sh``` does this switching. Adding the parameter ```-Pprod``` will target the production environment.

If you decide to activate the recently deployed app manually, you can do it with this command:
```./cf-swap-blue-green.sh -Pprod```
You should put your CloudFoundry username and password to the file ```cf-deploy.gradle.properties```
```
cfUsername=some@domain
cfPassword=somepass
```

### Production configuration

The file cf-deploy-war.tar.gz.gpg contains encrypted production and development environment configuration.
The production config is in a file ```cf-deployment-production/WEB-INF/classes/site-config.groovy```.
```travis-encrypt-files.sh``` and ```travis-decrypt-files.sh``` scripts are used to encrypt and decrypt these files.
These scripts assume that the symmetric crypt key is in ```CF_FILES_CRYPT_KEY``` environment variable.
The cipher used is symmetric key AES256 with gpg.
