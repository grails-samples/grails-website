package org.grails

import org.grails.news.NewsItem
import org.grails.ContentController
import org.grails.cache.CacheService
import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import org.grails.content.Version
import org.grails.content.Content
import org.codehaus.groovy.grails.plugins.codecs.URLCodec
import org.grails.wiki.*
import org.grails.plugin.PluginService
import grails.test.mixin.*

/**
 * @author Graeme Rocher
 * @since 1.0
 *
 * Created: Feb 28, 2008
 */
@TestFor(ContentController)
@Mock([Content, WikiPage, Version])
class ContentControllerSpec extends spock.lang.Specification {

    void "Test index no id"() {
        when:"A POST request is issued with no id"
            request.method = "POST"
            CacheService cacheService = Mock()
            controller.wikiPageService = new WikiPageService(cacheService: cacheService)
            controller.index()
        then:"The home page is rendered"
            response.status == 404
    }

    void "Test index with id"() {
        when:"A POST request is issued with an id for a page that doesn't exist"
            request.method = "POST"
            params.id = "home"
            CacheService cacheService = Mock()
            controller.wikiPageService = new WikiPageService(cacheService: cacheService)
            controller.index()
        then:"The home page is rendered"
            response.status == 404
    }


    void "Test index action with id for page that exists"() {
        when:"A POST request is issued with an id for a page that exists"
            request.method = "POST"
            params.id = "Introduction"
            new WikiPage(title:"Introduction").save(flush:true,validate:false)
            CacheService cacheService = Mock()
            controller.wikiPageService = new WikiPageService(cacheService: cacheService)
            controller.index()
        then:"The page is rendered"
            assert "/content/contentPage" == view
            assert model.content instanceof WikiPage
            assert "Introduction" == model.content.title
    }

    void "Test index action with id for a page that exists with Ajax"() {
        when:"An ajax request with an id for a page that exists is executed"
            request.makeAjaxRequest()
            request.method = "POST"
            params.id = "Introduction"
            new WikiPage(title:"Introduction").save(flush:true,validate:false)
            CacheService cacheService = Mock()
            controller.wikiPageService = new WikiPageService(cacheService: cacheService)
            controller.index()

        then:"The page is rendered as a template"
            response.text.contains 'wiki:text key="Introduction"'
    }

    void testShowWikiVersion() {
        ContentController.metaClass.getParams = {-> [id: "Introduction", number: 7] }
        WikiPage.metaClass.static.findByTitle = {String title -> new WikiPage(title: title) }
        def v
        Version.metaClass.static.findByCurrentAndNumber = {WikiPage page, Long n -> v = new Version(number: n, current: page)}

        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.showWikiVersion()
        assertEquals "showVersion", renderParams.view

        assertEquals v, renderParams.model?.content
        assertEquals 7, renderParams.model?.content.number
    }

    void testShowWikiVersionWithInvalidNumber() {
        def title = "Installation"
        mockDomain(WikiPage, [ new WikiPage(title: title) ])

        controller.params["id"] = title
        controller.params["number"] = "invalid"

        shouldFail(NumberFormatException) {
            controller.showWikiVersion()
        }
    }

    void testShowWikiVersionNotFound() {
        ContentController.metaClass.getParams = {-> [id: "Introduction", number: 7] }
        def page
        WikiPage.metaClass.static.findByTitle = {String title -> page = new WikiPage(title: title) }
        Version.metaClass.static.findByCurrentAndNumber = {WikiPage p, Long n -> null}

        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.showWikiVersion()
        assertEquals "contentPage", renderParams.view
        assertEquals page, renderParams.model.content

    }

    void testMarkupWikiPage() {
        ContentController.metaClass.getParams = {-> [id: "Introduction", number: 7] }
        def page
        WikiPage.metaClass.static.findByTitle = {String title -> page = new WikiPage(title: title) }

        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.markupWikiPage()
        assertEquals "wikiFields", renderParams.template

        assertEquals page, renderParams.model?.wikiPage

    }

    void testInfoWikiPage() {
        ContentController.metaClass.getParams = {-> [id: "Introduction", number: 7] }
        def page
        WikiPage.metaClass.static.findByTitle = {String title -> page = new WikiPage(title: title) }
        Version.metaClass.static.findAllByCurrent = {Content c -> []}
        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.infoWikiPage()
        assertEquals "wikiInfo", renderParams.template

        assertEquals page, renderParams.model?.wikiPage
        assertEquals([], renderParams.model.versions)

    }

    void testEditWikiPage() {
        ContentController.metaClass.getParams = {-> [id: "Introduction", number: 7] }
        def page
        WikiPage.metaClass.static.findByTitle = {String title -> page = new WikiPage(title: title) }
        Version.metaClass.static.findAllByCurrent = {Content c -> []}
        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.editWikiPage()
        assertEquals "wikiEdit", renderParams.template

        assertEquals page, renderParams.model?.wikiPage

    }

    void testCreateWikiPage() {
        ContentController.metaClass.getParams = {-> [id: "Introduction"] }

        def controller = new ContentController()
        def model = controller.createWikiPage()

        assertEquals "Introduction", model.pageName

    }

    void testSaveWikiPageGET() {

        def errorArg = 404
        ContentController.metaClass.getResponse = {-> [sendError: { errorArg = it}]  }
        ContentController.metaClass.getRequest = {-> [method: "GET"] }


        def controller = new ContentController()
        def model = controller.saveWikiPage()


        assertEquals 403, errorArg
    }

    void testSaveWikiPageWhenPageNotFoundFailure() {
        ContentController.metaClass.getRequest = {-> [method: "POST"] }
        ContentController.metaClass.getParams = {-> [id: "Introduction", title: "Introduction", body: "hello"] }
        WikiPage.metaClass.static.findByTitle = {String title -> null }
        WikiPage.metaClass.setId = { }
        WikiPage.metaClass.save = {-> delegate }
        WikiPage.metaClass.hasErrors = {-> true }
        String.metaClass.encodeAsURL = {-> URLCodec.encode(delegate)}

        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.saveWikiPage()

        assertEquals "createWikiPage", renderParams.view
        assertEquals "Introduction", renderParams?.model?.pageName
        assertEquals "Introduction", renderParams?.model?.wikiPage?.title

    }

    void testSaveWikiPageWhenPageNotFoundSuccess() {
        def wikiControl = mockFor(WikiPage)
        wikiControl.demand.static.findByTitle {String t -> null }
        wikiControl.demand.save {-> delegate }
        wikiControl.demand.hasErrors {-> false }
        wikiControl.demand.createVersion {->
            new Version()
        }
        def versControl = mockFor(Version)
        versControl.demand.save {-> delegate }

        mockParams.id = 'Introduction'
        mockParams.title = 'Introduction'
        mockParams.body = 'hello'
        mockRequest.method = 'POST'

        def redirectParams = [:]
        ContentController.metaClass.redirect = {Map args -> redirectParams = args }

        def controller = new ContentController()
        controller.saveWikiPage()

        assertEquals "/Introduction", redirectParams.uri
    }


    void testSaveWikiPageWhenPageExistsOptimisticLockingFailure() {
        ContentController.metaClass.getRequest = {-> [method: "POST"] }
        ContentController.metaClass.getParams = {-> [id: "Introduction", title: "Introduction", body: "hello", version: "3"] }
        WikiPage.metaClass.static.findByTitle = {String title -> new WikiPage(title: title) }
        WikiPage.metaClass.setId = { }
        WikiPage.metaClass.save = {-> delegate }
        WikiPage.metaClass.hasErrors = {-> false }
        WikiPage.metaClass.getVersion = {-> 2 }
        Version.metaClass.save = {-> delegate }

        def renderParams = [:]
        ContentController.metaClass.render = {Map args -> renderParams = args }

        def controller = new ContentController()
        controller.saveWikiPage()

        assertEquals "wikiEdit", renderParams.template
        assertEquals "page.optimistic.locking.failure", renderParams.model.error

    }
}
