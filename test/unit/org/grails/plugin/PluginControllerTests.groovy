package org.grails.plugin

import org.grails.plugin.Plugin
import org.grails.taggable.*
import grails.test.ControllerUnitTestCase
import grails.test.mixin.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.wiki.WikiPage
import org.joda.time.DateTime

import static org.junit.Assert.*
/*
 * @author Matthew Taylor
 * @author Graeme Rocher
 */
@TestFor(PluginController)
@Mock([Plugin, WikiPage,Tag, org.grails.taggable.TagLink])
class PluginControllerTests {

    void testShowPlugin() {
        Plugin p = new Plugin(name:'plugin', title:'My Plugin', documentationUrl:"http://grails.org/plugin/my",
                                downloadUrl:"http://grails.org/my.zip", currentRelease:"1.0.0", authorEmail:"foo@bar.com")
        p.save flush:true, validate:false

        controller.tagService = createMockTagService() 
        def model = controller.show('plugin')

        assert p == model.plugin
    }

    private createMockTagService() {
        def tagService = mockFor(TagService)
        tagService.demand.getPluginTagArray {->[]}
        return tagService.createMock()
    }

    void testHomePopulatesFeaturedPluginsByDefault() {
        def plugins = [
            new Plugin(name:'not featured 1', featured:false),
            new Plugin(name:'featured 1', featured: true),
            new Plugin(name:'not featured 2', featured: false),
            new Plugin(name:'featured 2', featured: true)
        ]*.save(validate:false, flush:true)


        controller.tagService = createMockTagService()        
        controller.pluginService = new PluginService()
        def model = controller.list()
        
        assertNotNull model.plugins
        assertEquals 2, model.plugins.size()
        assertTrue model.plugins*.name.contains('featured 1')
    }
    
    void testHomePopulatesAllPlugins() {
        def plugins = [
            new Plugin(name:'not featured 1', featured:false),
            new Plugin(name:'featured 1', featured: true),
            new Plugin(name:'not featured 2', featured: false),
            new Plugin(name:'featured 2', featured: true)
        ]*.save(validate:false, flush:true)
        
        params.filter = 'all'
        controller.tagService = createMockTagService()        
        controller.pluginService = new PluginService()
        def model = controller.list()
        
        assert model.plugins != null
        assert 4 == model.plugins.size()
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
        params.filter = 'popular'
        controller.tagService = createMockTagService()        
        controller.pluginService = new PluginService()
        def model = controller.list()
        assert model.plugins != null
        assert 10 == model.plugins.size()
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
            new Plugin(name:'fifth', lastReleased: new DateTime(2001, 01, 01, 0, 0)),
            new Plugin(name:'third', lastReleased: new DateTime(2008, 01, 01, 0, 0)),
            new Plugin(name:'second', lastReleased: new DateTime(2009, 01, 01, 0, 0)),
            new Plugin(name:'first', lastReleased: new DateTime(2009, 01, 01, 10, 0)),
            new Plugin(name:'fourth', lastReleased: new DateTime(2007, 01, 01, 0, 0))
        ]*.save validate:false, flush:true
        params.filter = 'recentlyUpdated'
        controller.tagService = createMockTagService()        
        controller.pluginService = new PluginService()
        def model = controller.list()
        
        assert model.plugins != null
        assert model.plugins.size() == 5
        assert model.plugins[0].name == 'first'
        assert model.plugins[1].name == 'second'
        assert model.plugins[2].name == 'third'
        assert model.plugins[3].name == 'fourth'
        assert model.plugins[4].name == 'fifth'
    }
    
    // test order
    
    // test max
    
    // test sort
    
    // test offset
}
