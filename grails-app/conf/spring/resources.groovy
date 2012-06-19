import org.apache.shiro.authc.credential.Sha1CredentialsMatcher
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.auth.ShiroUserBean
import org.grails.content.notifications.ContentAlertStack
import org.grails.wiki.GrailsWikiEngineFactoryBean
import org.radeox.engine.context.BaseInitialRenderContext
import org.springframework.cache.ehcache.EhCacheFactoryBean

// Place your Spring DSL code here
beans = {
    currentUser(ShiroUserBean)

    // Shiro defaults to SHA-256 for password hashing. We're going to
    // use SHA-1 for now.
    credentialMatcher(Sha1CredentialsMatcher) {
        storedCredentialsHexEncoded = true
    }

    textCache(EhCacheFactoryBean) {
        timeToLive = 300
        maxElementsInMemory = 100
        overflowToDisk=false
    }
    downloadCache(EhCacheFactoryBean) {
        timeToLive = 300
        maxElementsInMemory = 100
        overflowToDisk=false        
    }
    contentCache(EhCacheFactoryBean) {
        timeToLive = 300
        maxElementsInMemory = 1000
        overflowToDisk=false        
    }
    wikiCache(EhCacheFactoryBean) {
        timeToLive = 300
        maxElementsInMemory = 200
        overflowToDisk=false        
    }
    pluginListCache(EhCacheFactoryBean) {
        timeToLive = 600
        maxElementsInMemory = 10
        overflowToDisk=false        
    }
    wikiContext(BaseInitialRenderContext)
    wikiEngine(GrailsWikiEngineFactoryBean) {
        cacheService = ref('cacheService')
        def config = ConfigurationHolder.getConfig()
        contextPath = config.grails.serverURL ?: ""
        context = wikiContext
    }
}
