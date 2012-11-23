package org.grails.news

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import org.grails.common.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(NewsItemController)
@Mock(NewsItem)
class NewsItemControllerSpec extends spock.lang.Specification{

    void "Test that index action only returns approved news items"() {
        given:"A domain model with approved and pending news items"
            new NewsItem(title:"one").save(validate:false)
            new NewsItem(title:"two", status: ApprovalStatus.APPROVED).save(validate:false, flush:true)
            
        when:"The index action is called"
            controller.index()
            
        then:"Then the list view is rendered with only approved news items"
            NewsItem.count() == 2
            view == '/newsItem/list'
            model != null
            model.newsItems?.size() == 1
            model.newsItems[0].title == 'two'
    }
    
    void "Test that show action only returns approved news items"() {
        given:"A domain model with approved and pending news items"
            def notApproved = new NewsItem(title:"one").save(validate:false)        
            def approved = new NewsItem(title:"two", status: ApprovalStatus.APPROVED).save(validate:false, flush:true)
            
        when:"The show action is called for a unapproved news item"
            def resp = controller.show(notApproved.id)
            
        then:"A 404 is returned"
            resp == null
            response.status == 404
            
        when:"the show action is called for an approved item"
            response.reset()
            resp = controller.show(approved.id)
            
        then:"The correct model is returned"
            resp != null
            resp.newsItem?.title == "two"
            response.status == 200
    }    
}
