/*
import org.springframework.cloud.CloudFactory

def cloud

try {
  cloud = new CloudFactory().cloud
} catch(e) {}


// configure sendgrid SMTP sender
grails {
   mail {
     def smtpServiceInfo = cloud.getServiceInfo('sendgrid')
     host = "smtp.sendgrid.net"
     port = 587
     username = smtpServiceInfo.userName
     password = smtpServiceInfo.password
     props = ["mail.smtp.starttls.enable":"true", 
              "mail.smtp.port":"587"]
   }
}
*/
grails.serverURL = "http://staging.grails.org/"

dataSource {
    dbCreate = ""
    pooled = true
    jmxExport = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    def dbUri = new URI(System.env.DATABASE_URL ?: "mysql://foo:bar@localhost")
    username = dbUri.userInfo ? dbUri.userInfo.split(":")[0] : ""
    password = dbUri.userInfo ? dbUri.userInfo.split(":")[1] : ""
    url = "jdbc:mysql://" + dbUri.host + dbUri.path
    properties {
       // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
       jmxEnabled = true
       initialSize = 3
       maxActive = 14
       minIdle = 3
       maxIdle = 10
       maxWait = 10000
       maxAge = 10 * 60000
       timeBetweenEvictionRunsMillis = 5000
       minEvictableIdleTimeMillis = 60000
       validationQuery = "SELECT 1"
       validationQueryTimeout = 3
       validationInterval = 15000
       testOnBorrow = true
       testWhileIdle = true
       testOnReturn = false
       jdbcInterceptors = "ConnectionState"
       defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
    }
}

grails.plugin.excludes = ['javaBuildpackAutoReconfiguration']
