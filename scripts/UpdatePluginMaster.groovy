import org.springframework.mock.jndi.*
import org.apache.commons.dbcp.*

includeTargets << grailsScript( "Bootstrap" )  

target(main:"the default target") {
	depends(parseArguments, configureProxy)
	
	def username = argsMap.user
	def password = argsMap.pass
	def url = argsMap.url
	
        if (url) {
            if (username == null || password == null) {
                println "When you use the --url argument, you must also specify --username and --password."
                exit(1)
            }

            // mock out the production JNDI settings
            def builder = new SimpleNamingContextBuilder();
            def ds = new BasicDataSource()
            ds.username = username
            ds.password = password
            ds.url = url
            ds.driverClassName = "com.mysql.jdbc.Driver"
	
            builder.bind("java:comp/env/jdbc/grailsSiteDS", ds)
            builder.activate()
        }
	
	try {
            bootstrap()
            def pluginService = appCtx.getBean("pluginService")
            println "Syncing plugin list. Please wait.."
            pluginService.runMasterUpdate()
            println "Plugin list sync complete."
	}
	catch(e) {
            println "Error occurred syncing plugin list ${e.message}"
            e.printStackTrace()
	}

	
}
setDefaultTarget(main)
