package org.grails.learn

import static org.junit.Assert.*
import org.grails.learn.tutorials.*
import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(TutorialController)
@Mock([Tutorial])
class TutorialControllerSpec extends spock.lang.Specification{

    void "Test edit action returns 404 for non-existant tutorial"() {
    	when:"the edit action is called"
    		controller.edit(10)
    	then:"A 404 is returned if the tutorial doesn't exist"
    		assert response.status == 404
    }

    void "Test that the tutorial is returned in the model from the edit action if it exists"() {
    	given:"An existing tutorial"
    		def t = new Tutorial(title:"blah").save(flush:true, validate:false)

    	when:"The edit action is called"
    		controller.edit(t.id)

    	then:"The model is correct"
			view == '/tutorial/create'    		
			model.tutorialInstance == t
    }
}
