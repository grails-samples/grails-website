import org.grails.community.Testimonial

import org.grails.auth.*
import org.grails.common.ApprovalStatus

fixture {
	def peter = User.findByLogin('peter') //depends on users fixture to be loaded first

	(0..50).each {
		"testimonial$it"(Testimonial, [title: "testimonial $it", body: "test $it", submittedBy: peter, status: ApprovalStatus.APPROVED])
	}
}