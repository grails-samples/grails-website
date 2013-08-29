import org.grails.learn.tutorials.Tutorial

import org.grails.auth.*
import org.grails.common.ApprovalStatus

fixture {
	def peter = User.findByLogin('peter') //depends on users fixture to be loaded first

	(0..50).each {
		"tutorial$it"(Tutorial, [title: "tutorial $it", description: "test $it", url: "http://www.test.com", submittedBy: peter, status: ApprovalStatus.APPROVED])
	}
}