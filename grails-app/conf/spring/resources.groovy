import org.apache.shiro.authc.credential.Sha1CredentialsMatcher
import org.grails.auth.ShiroUserBean
import org.grails.wiki.GrailsWikiEngineFactoryBean
import org.radeox.engine.context.BaseInitialRenderContext
import org.springframework.social.connect.support.ConnectionFactoryRegistry
import org.springframework.social.twitter.api.impl.TwitterTemplate
import org.springframework.social.twitter.connect.TwitterConnectionFactory
// Place your Spring DSL code here
beans = {
    currentUser(ShiroUserBean)

    // Shiro defaults to SHA-256 for password hashing. We're going to
    // use SHA-1 for now.
    credentialMatcher(Sha1CredentialsMatcher) {
        storedCredentialsHexEncoded = true
    }

    authcBasicFilter(org.grails.auth.RestBasicAuthFilter) {
        applicationName = 'grails.org'
    }

    wikiContext(BaseInitialRenderContext)
    wikiEngine(GrailsWikiEngineFactoryBean) {
        cacheService = ref('cacheService')
        contextPath = application.config.grails.serverURL ?: ""
        context = wikiContext
    }

    def consumerKey = application.config.twitter.consumerKey
    def consumerSecret = application.config.twitter.consumerSecret
    def accessKey = application.config.twitter.accessKey
    def accessSecret = application.config.twitter.accessSecret
    twitterApi(TwitterTemplate, consumerKey.toString(), consumerSecret.toString(), accessKey.toString(),  accessSecret.toString())
}
