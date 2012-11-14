package org.grails.content

import grails.events.Listener

class GenericApprovalResponseService {
    def grailsApplication
    def mailService

    boolean linkAndfirePendingApproval(GenericApprovalResponse genericApprovalResponse) {

        def itemClass = getClass().classLoader.loadClass(genericApprovalResponse.whatType)
        def item = itemClass.get(genericApprovalResponse.whatId)

        item.setDisposition(genericApprovalResponse)

        if (log.debugEnabled && !genericApprovalResponse?.submittedBy?.email || !genericApprovalResponse.responseText) {
            log.debug "Skipped sending email due to missing data"
            return true
        }

        event 'approvePendingApproval', [genericApprovalResponse:genericApprovalResponse, item:item]
        true
    }

    @Listener
    def approvePendingApproval(Map data){
        GenericApprovalResponse genericApprovalResponse = data?.genericApprovalResponse
        def item = data?.item

        // Send email to the user
        def mailConfig = grailsApplication.config.plugins.forum.mail
        def toAddress = genericApprovalResponse.submittedBy?.email ?: 'administrator@grails.org' // Debugging
        def fromAddress = mailConfig.from

        mailService.sendMail {
            to toAddress
            from fromAddress
            subject "${item} has been ${genericApprovalResponse.status}"
            text genericApprovalResponse.responseText
        }
    }
}
