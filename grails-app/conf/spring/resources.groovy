import org.apache.shiro.authc.credential.Sha1CredentialsMatcher
import org.codehaus.groovy.grails.commons.ConfigurationHolder
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

    wikiContext(BaseInitialRenderContext)
    wikiEngine(GrailsWikiEngineFactoryBean) {
        cacheService = ref('cacheService')
        def config = ConfigurationHolder.getConfig()
        contextPath = config.grails.serverURL ?: ""
        context = wikiContext
    }

    twitterApi(TwitterTemplate, '${twitter.consumerKey}', '${twitter.consumerSecret}', '${twitter.accessToken}', '${twitter.accessSecret}')
}
