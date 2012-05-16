package org.grails.content

class GenericApprovalResponseService {
    def grailsApplication
    def mailService

    def setDispositionOfPendingApproval(GenericApprovalResponse genericApprovalResponse) {

        def itemClass = getClass().classLoader.loadClass(genericApprovalResponse.whatType)
        def item = itemClass.get(genericApprovalResponse.whatId)

        item.setDisposition(genericApprovalResponse)

        if (!genericApprovalResponse?.submittedBy?.email || !genericApprovalResponse.responseText) {
            println "Skipped sending email due to missing data"
            return true
        }

        // Send email to the user
        def mailConfig = grailsApplication.config.plugins.forum.mail
        def toAddress = genericApprovalResponse.submittedBy?.email ?: 'cavneb@gmail.com' // Debugging
        def fromAddress = mailConfig.from

        mailService.sendMail {
            to toAddress
            from fromAddress
            subject "${item} has been ${genericApprovalResponse.status}"
            text genericApprovalResponse.responseText
        }

        return true
    }
}
