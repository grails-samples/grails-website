//dataSource {
//    pooled = true
//    driverClassName = "com.mysql.jdbc.Driver"
//    url = "jdbc:mysql://localhost/grails"
//    username = "root"
//    password = ""
//    dialect = org.grails.db.Utf8InnodbDialect
//}
hibernate {
    cache.use_second_level_cache=false
    cache.use_query_cache=false
//    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:h2:mem:devDb"
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:h2:mem:testDb"
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
        }
    }
    production {
        dataSource {
            pooled = true
            dbCreate = "update"
            url = "jdbc:h2:prodDb"
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            jmxEnabled = true
            initialSize = 5
            maxActive = 50
            minIdle = 5
            maxIdle = 25
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
            jdbcInterceptors = "ConnectionState;StatementCache(max=200)"
            defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED            
        }
    }
}
