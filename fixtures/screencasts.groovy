import org.grails.learn.screencasts.Screencast

import org.grails.auth.*
import org.grails.common.ApprovalStatus

fixture {
	def peter = User.findByLogin('peter') //depends on users fixture to be loaded first

	(0..50).each {
		"screencast$it"(Screencast, [title: "screencast $it", description: "test $it", submittedBy: peter, status: ApprovalStatus.APPROVED])
	}
}