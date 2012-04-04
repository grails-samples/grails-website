# Grails.org (including Wiki)

This is the Grails project for [Grails.org](http://www.grails.org). Please fork and submit pull requests for any changes made.

This application was developed against Grails 2.0.3 and should be used when making any changes.

## Development Enviroment

The development enviroment is meant to be used with the H2 database. 
The configuration in `DataSource.groovy` is as follows:

```groovy
development {
    dataSource {
        dbCreate = "update" // one of 'create', 'create-drop','update'
        url = "jdbc:h2:mem:devDb"
        driverClassName = "org.h2.Driver"
        username = "sa"
        password = ""
    }
}
```

To start the application, run the following command:

```bash
grails run-app
```

## Production Environment

The production enviroment is meant to be used with the MySQL database. Ensure that the database `grails` exists.
You can modify the username/password in the `DataSource.groovy` file as needed. The default username is 'root' with no password.

The configuration in `DataSource.groovy` is as follows:

```groovy
production {
    dataSource {
        pooled = true
        driverClassName = "com.mysql.jdbc.Driver"  		
        url = "jdbc:mysql://localhost/grails"
        username = "root"
        password = ""
    }
}
```

To start the application, you can simply run the following command:

```bash
grails prod run-app
```

However, if this is the first time the app is run, you can use this command in order to change the admin password:

```bash
grails prod -Dinitial.admin.password=changeit run-app
```

### Continuing after an update

If other developers have made database migrations and properly annotated them in the [Liquibase Changelog](https://github.com/cavneb/grails-website/blob/master/migrations/changelog.groovy), you'll need to
run `grails migrate` to update your local database. This should be run whenever anything in the grails-app/migrations folder has changed.

If you start seeing unexplained SQL exceptions, it is probably because you or someone else didn't keep the changelog
current.

## Testing

Documentation to follow.

