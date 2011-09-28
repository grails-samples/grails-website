package org.grails.wiki



import org.junit.*
import grails.test.mixin.*
import javax.servlet.http.HttpServletResponse

@TestFor(WikiPageController)
@Mock(WikiPage)
class WikiPageControllerTests {

    void testIndex() {
        controller.index()
        assert "/wikiPage/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.wikiPageInstanceList.size() == 0
        assert model.wikiPageInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.wikiPageInstance != null
    }

    void testSave() {
        controller.save()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.save()

        assert model.wikiPageInstance != null
        assert view == '/wikiPage/create'

        response.reset()

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/wikiPage/show/1'
        assert controller.flash.message != null
        assert WikiPage.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/wikiPage/list'


        def wikiPage = new WikiPage()

        // TODO: populate domain properties

        assert wikiPage.save() != null

        params.id = wikiPage.id

        def model = controller.show()

        assert model.wikiPageInstance == wikiPage
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/wikiPage/list'


        def wikiPage = new WikiPage()

        // TODO: populate valid domain properties

        assert wikiPage.save() != null

        params.id = wikiPage.id

        def model = controller.edit()

        assert model.wikiPageInstance == wikiPage
    }

    void testUpdate() {

        controller.update()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/wikiPage/list'

        response.reset()


        def wikiPage = new WikiPage()

        // TODO: populate valid domain properties

        assert wikiPage.save() != null

        // test invalid parameters in update
        params.id = wikiPage.id

        controller.update()

        assert view == "/wikiPage/edit"
        assert model.wikiPageInstance != null

        wikiPage.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/wikiPage/show/$wikiPage.id"
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert response.status == HttpServletResponse.SC_METHOD_NOT_ALLOWED

        response.reset()
        request.method = 'POST'
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/wikiPage/list'

        response.reset()

        def wikiPage = new WikiPage()

        // TODO: populate valid domain properties
        assert wikiPage.save() != null
        assert WikiPage.count() == 1

        params.id = wikiPage.id

        controller.delete()

        assert WikiPage.count() == 0
        assert WikiPage.get(wikiPage.id) == null
        assert response.redirectedUrl == '/wikiPage/list'
    }
}
