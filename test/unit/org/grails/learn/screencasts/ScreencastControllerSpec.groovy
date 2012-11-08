package org.grails.learn.screencasts

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(ScreencastController)
@Mock([Screencast])
class ScreencastControllerSpec extends spock.lang.Specification{

    void "Test edit action returns 404 for non-existant screencast"() {
        when:"the edit action is called"
            controller.edit(10)
        then:"A 404 is returned if the screencast doesn't exist"
            assert response.status == 404
    }

    void "Test that the screencast is returned in the model from the edit action if it exists"() {
        given:"An existing screencast"
            def t = new Screencast(title:"blah").save(flush:true, validate:false)

        when:"The edit action is called"
            controller.edit(t.id)

        then:"The model is correct"
            view == '/screencast/create'          
            model.screencastInstance == t
    }
}
