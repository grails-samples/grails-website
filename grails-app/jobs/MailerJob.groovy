import org.grails.meta.UserInfo
import org.grails.content.Content
import org.radeox.engine.context.BaseInitialRenderContext
import org.grails.wiki.GrailsWikiEngine
import org.grails.wiki.WikiPageUpdateEvent
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import javax.servlet.ServletContext
import org.grails.content.Version
import org.grails.plugin.Plugin
import org.grails.plugin.PluginTab
import org.codehaus.groovy.grails.plugins.*

class MailerJob {
    def startDelay = 60000
    def timeout =    60000     // execute job every minute

    def mailService
    def pluginService
    def cacheService
    def wikiPageService

    def execute() {
		try {
            def wikiPageUpdates = wikiPageService.wikiPageUpdates
	        WikiPageUpdateEvent pageUpdate = wikiPageUpdates.poll()
	        if (pageUpdate) {
	            ServletContext servletContext = ServletContextHolder.servletContext
	            def context = new BaseInitialRenderContext();
	            context.set(GrailsWikiEngine.CONTEXT_PATH, servletContext.contextPath)
	            context.set(GrailsWikiEngine.CACHE, cacheService)
	            def engine = new GrailsWikiEngine(context)

	            context.setRenderEngine engine
	            def emails = UserInfo.executeQuery(
                            "select ui.user.email from UserInfo as ui where ui.emailSubscribed = ?",
                            [true])
                
                def classLoader = getClass().classLoader
	            while (pageUpdate) {
                        log.info "Mailing changes about '${pageUpdate.title}'"

                        def pageClass = classLoader.loadClass(pageUpdate.className)
                        def content = pageClass.findByTitle(pageUpdate.title)
                        
	                def text = new StringBuffer()
	                def titleUrlEscaped = pageUpdate.title.encodeAsURL()
	                def url = "http://grails.org/${titleUrlEscaped}"
	                def myTitle = pageUpdate.title
	                // make some alterations to the email if this wiki is a part of a plugin
	                if (content.instanceOf(PluginTab)) {
                        def plugin = content.plugin
	                    url = "http://grails.org/plugin/${plugin.name}"
                        def titleParts = content.title.split('-')
                        
                        // The plugin tab type is encoded in the page title in different
                        // ways depending on when the page was created. The old style is
                        // '$wikiType-nnn, whereas the new style is 'plugin-$pluginName-$wikiType'.
                        def wikiType
                        if (titleParts[1] ==~ /\d+/) wikiType = titleParts[0]
                        else wikiType = titleParts[-1]
                        
	                    myTitle = "${plugin.title} (${wikiType[0].toUpperCase() + wikiType[1..-1]} tab)"
	                }

	                def pageVersions = Version.findAllByCurrent(content, [sort:'number',order:'desc', max:1, cache:true])

					if(pageVersions) {
		                def version = pageVersions[-1] //last version
						text << "<div>To unsubscribe from receiving these emails go to <a href=\"http://grails.org/profile\">http://grails.org/profile</a>, login and uncheck the 'Receive E-mail Updates for Content Changes?' box. </div><br><br>"
		                text << '<div style="color:black;"><br><br>'
		                text << "Page <a href=\"${url}\">${myTitle}<a/> <br><br>"
		                text << "</div>"
		                text << '<div style="color:black;"><br><br>'
		                text << "Edited by <b>${version.author?.login}</b>. <a href=\"http://grails.org/previous/${titleUrlEscaped}/${version.number}\">View change</a> <br><br>"
		                text << "</div>"
		                text << engine.render(content.body, context)

		                for (email in emails) {
		                    mailService?.sendMail {
		                        title "Grails > ${myTitle}"
		                        from "wiki@grails.org"
		                        replyTo "wiki@grails.org"
		                        to email
		                        html text.toString()
		                    }
		                }						
					}
	                pageUpdate = wikiPageUpdates.poll()
					Content.withSession { session -> session.clear() }
	            }
	        }		
		}
		finally {
			DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP.get().clear()
		}
    }
}
