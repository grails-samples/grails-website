## Grails.org (including Wiki)

This is the Grails project for [Grails.org](http://www.grails.org). Please fork and submit pull requests for any changes made.

### Getting Started

This application was developed against Grails 2.0.3. 

If you are running the app in *DEVELOPMENT* mode, you can start the app with the `run-app` command:

```bash
grails run-app
```

If you are running the app in *PRODUCTION* mode, you can run the following command in order to change the admin password:

```bash
grails -Dinitial.admin.password=changeit run-app
```

### Continuing after an update

If other developers have made database migrations and properly annotated them in the [Liquibase Changelog](https://github.com/cavneb/grails-website/blob/master/migrations/changelog.groovy), you'll need to
run `grails migrate` to update your local database. This should be run whenever anything in the grails-app/migrations folder has changed.

If you start seeing unexplained SQL exceptions, it is probably because you or someone else didn't keep the changelog
current.