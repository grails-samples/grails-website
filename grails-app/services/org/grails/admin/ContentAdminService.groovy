package org.grails.admin

import grails.events.*
import grails.util.Environment;

import org.grails.auth.*
class ContentAdminService {
    static transactional = false
    def mailService
    def grailsLinkGenerator

    private informAdmins(object) {
        if(!Environment.current.isDevelopmentMode()) {
            Thread.start {
                User.withNewSession {
                    try {
                        def adminUsers = User.where {
                            roles.name == Role.ADMINISTRATOR
                        }.list()


                        for(admin in adminUsers) {
                            def e = admin.email
                            def s = "Grails.org: There is a ${object.class.simpleName} awaiting approval"
                            def b = "Go to ${grailsLinkGenerator.link(controller:'contentPendingApproval', action:'list', absolute:true)} to approve."

                            mailService.sendMail {
                                to e
                                subject s
                                body b
                            }
                        }
                    }
                    catch(e) {
                        log.error("Error occurred informing admins of new content: ${e.message}", e)
                    }
                }
            }
        } else {
            println "Grails.org: There is a ${object.class.simpleName} awaiting approval"
        }
    }
}
