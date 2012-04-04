hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
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
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost/grails"
            username = "root"
            password = ""
        }
    }
}
