package org.grails.wiki

import org.grails.cache.CacheService
import org.grails.common.Helpers
import org.grails.wiki.HtmlShortener
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 19, 2008
*/
class WikiTagLib implements ApplicationContextAware  {
    static final String ROBOTS_ATTRIBUTE = "org.grails.wiki.ROBOTS_CONTENT"

    static namespace = 'wiki'

    CacheService cacheService
    def wikiPageService
    ApplicationContext applicationContext

    /**
     * Shortens HTML text to a set limit. The text can either be provided via
     * the {@code html} attribute, which should be HTML content; the {@code wikiText}
     * attribute, whose contents will be converted from wiki text to HTML before
     * shortening; or as the body of the tag (HTML markup).
     * @attr html The HTML text to shorten.
     * @attr wikiText The wiki text to convert to HTML and shorten.
     * @attr key A unique key for this fragment that can be used for caching.
     * @attr length REQUIRED The maximum length of the resulting content.
     * @attr camelCase Capitalises all the words in the content.
     */
    def shorten = { attrs, body ->
        def text = attrs.html ?: wikiToHtml(attrs.wikiText?.toString()) ?: body()
        def cacheKey = attrs.key
        def length = attrs.length?.toInteger() ?: 25
        def camelCase = attrs.camelCase ? Boolean.valueOf(attrs.camelCase) : false
        def ret = ""

        // Returned cached content if it's available.
        if (cacheKey) {
            def content = cacheService.getShortenedWikiText(cacheKey)
            if (content) {
                out << content
                return
            }
        }

        // Shorten the text
        def shortener = new HtmlShortener()
        def content = shortener.shorten(text.toString(), length)

        if (camelCase) {
            content = Helpers.capitalizeWords(content)
        }

        // Cache the content if a key is provided.
        if (cacheKey) cacheService.putShortenedWikiText(cacheKey, content)

        out << content
    }
	
    def preview = { attrs, body ->
        def text = wikiToHtml(body().toString())

        if(text.size() > 150) {
            text = text.replaceAll(/<.+?>/, '').replaceAll(/<\/\S+?>/, '')
            out << text
        }
        else {
            out << text
        }
    }

    def text = { attrs, body ->
        def cached
        if(attrs.key) {
            cached = cacheService.getWikiText(attrs.key)
        }
        if(cached) {
            out << cached            
        }
        else {
            def content 
            if(attrs.page) {
                content = wikiPageService?.getCachedOrReal(attrs.page)?.body ?: ""
            }
            else {
                content  = body()
            }

            def text = wikiToHtml(content.toString())
            if(attrs.key) {
                cacheService.putWikiText(attrs.key, text)
            }
            out << text
        }
    }

    /**
     * Designed for use in layouts.
     */
    def robots = { attrs ->
        def content = request[ROBOTS_ATTRIBUTE] ?: attrs.content

        out << "<meta name=\"robots\" content=\"${content ?: 'NOODP'}\">"
    }

    def deprecated = { attrs ->
        out << "<blockquote class=\"warning\">This page is deprecated"
        if (attrs.uri) {
            out << " and has been <a href=\"${attrs.uri.encodeAsHTML()}\">replaced</a>"
        }
        out << "</blockquote>"

        request[ROBOTS_ATTRIBUTE] = "noindex, nofollow"
    }

    def uploadImages = {attrs ->
       def prefix = attrs.prefix ?: ""

       out << """
          <div id="images-container">
            ${g.hiddenField(name: "image.prefix", value: prefix)}
            <div id="images"></div>
            <p class="spinner" style="display:none"><img src="${resource(dir:'img',file:'spinner.gif')}" alt="Loading" /></p>
            <div class=\"progress alert\" style=\"display:none\"></div>
            <p><a class="btn upload-image">Upload image</a></p>
            <div class="alert alert-error error" style="display:none"></div>
        </div>
       """
    }

    private wikiToHtml(String wikiText) {
        if (!wikiText) return wikiText

        def engine = applicationContext.getBean('wikiEngine')
        def context = applicationContext.getBean('wikiContext')
        return engine.render(wikiText.trim(), context)
    }

}
