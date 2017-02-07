package org.grails.admin

import grails.events.*
import grails.util.Environment;

import org.grails.auth.*
import org.grails.learn.screencasts.Screencast
import org.grails.learn.tutorials.Tutorial
import org.grails.community.*
class ContentAdminService {
    static transactional = false
    def mailService
    def grailsLinkGenerator

    @Listener(namespace = 'gorm')
    void afterInsert(Screencast s) {
        informAdmins(s)
    }
    @Listener(namespace = 'gorm')
    void afterInsert(Tutorial t) {
        informAdmins(t)
    }
    @Listener(namespace = 'gorm')
    void afterInsert(WebSite t) {
        informAdmins(t)
    }

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
