package org.grails.plugin

import grails.test.ControllerUnitTestCase
import org.grails.wiki.WikiPage
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import grails.test.mixin.*

/*
 * @author Matthew Taylor
 * @author Graeme Rocher
 */
@TestFor(PluginController)
@Mock([Plugin, WikiPage])
class PluginControllerTests {

    void testShowPlugin() {
        Plugin p = new Plugin(name:'plugin', title:'My Plugin', documentationUrl:"http://grails.org/plugin/my",
                                downloadUrl:"http://grails.org/my.zip", currentRelease:"1.0.0", authorEmail:"foo@bar.com")
        p.save flush:true

        params.name = 'plugin'

        controller.show()

        assertEquals p, model.plugin
    }

    void testCreatePluginGET() {
        params.title='My Plugin'

        controller.createPlugin()

        assert model.plugin
        assertEquals 'My Plugin', model.plugin.title
    }

    void testCreatePluginValidationSuccess() {
        request.method = 'POST'
        params.title='my plugin'
        params.name='my-plugin'
        params.authorEmail='blargh'
        params.currentRelease='1.2'
        params.downloadUrl='durl'
        def control = mockFor(PluginService)

        control.demand.initNewPlugin { arg1, arg2 -> }
        control.demand.savePlugin { arg -> true }
        controller.pluginService = control.createMock()

        controller.createPlugin()

        assert "/plugin/show?name=my-plugin" == response.redirectUrl
    }
    
    void testHomePopulatesFeaturedPluginsByDefault() {
        def plugins = [
            new Plugin(name:'not featured 1', featured:false),
            new Plugin(name:'featured 1', featured: true),
            new Plugin(name:'not featured 2', featured: false),
            new Plugin(name:'featured 2', featured: true)
        ]*.save(validate:false, flush:true)


        controller.metaClass.getCommentService = { -> [getLatestComments: { String type, num -> []}]}
        
        controller.pluginService = new PluginService()
        def model = controller.home()
        
        assertNotNull model.currentPlugins
        assertEquals 2, model.currentPlugins.size()
        assertTrue model.currentPlugins*.name.contains('featured 1')
    }
    
    void testHomePopulatesAllPlugins() {
        def plugins = [
            new Plugin(name:'not featured 1', featured:false),
            new Plugin(name:'featured 1', featured: true),
            new Plugin(name:'not featured 2', featured: false),
            new Plugin(name:'featured 2', featured: true)
        ]*.save(validate:false, flush:true)
        
        params.category = 'all'
        controller.metaClass.getCommentService = { -> [getLatestComments: { String type, num-> []}]}
        controller.pluginService = new PluginService()
        def model = controller.home()
        
        assertNotNull model.currentPlugins
        assertEquals 4, model.currentPlugins.size()
    }
    
    void testHomePopulatesMostPopularPlugins() {
        def plugins = []
        Plugin.metaClass.getAverageRating = { ->
            delegate.id % 2 == 0 ? delegate.id * 10 : delegate.id // only evens get the big ratings
        }
        Plugin.metaClass.static.countRated = {->
            20
        }
        10.times { i ->
            plugins << new Plugin(name:"number $i")
        }
        plugins*.save flush:true, validate:false
        params.category= 'popular' 
        controller.metaClass.getCommentService = { -> [getLatestComments: { String type, num-> []}]}
        controller.pluginService = new PluginService()
        def model = controller.home()
        assertNotNull model.currentPlugins
        assertEquals 5, model.currentPlugins.size()
        // assert that every rating is the same or less than the previous (sorted by popularity)
        def cur
        model.currentPlugins.each {
            if (!cur) {
                return
            }
            println "asserting $it.averageRating <= $cur.averageRating"
            assertTrue it.averageRating >= cur.averageRating
            cur = it
        }
    }
    
    void testHomePopulatesRecentlyUpdatedPlugins() {
        def plugins = [
            new Plugin(name:'fourth', lastReleased: new GregorianCalendar(2001,01,01).time),
            new Plugin(name:'second', lastReleased: new GregorianCalendar(2008,01,01).time),
            new Plugin(name:'first', lastReleased: new GregorianCalendar(2009,01,01).time),
            new Plugin(name:'third', lastReleased: new GregorianCalendar(2007,01,01).time)
        ]*.save validate:false, flush:true
        params.category= 'recentlyUpdated'
        controller.metaClass.getCommentService = { -> [getLatestComments: { String type, num-> []}]}
        controller.pluginService = new PluginService()
        def model = controller.home()
        
        assertNotNull model.currentPlugins
        assertEquals 4, model.currentPlugins.size()
        assertEquals 'first', model.currentPlugins[0].name
        assertEquals 'second', model.currentPlugins[1].name
        assertEquals 'third', model.currentPlugins[2].name
        assertEquals 'fourth', model.currentPlugins[3].name
    }
    
    // test order
    
    // test max
    
    // test sort
    
    // test offset
}
