package org.grails.auth

import org.apache.shiro.SecurityUtils

class ShiroUserBean {
    def shiroSecurityService

    String getPrincipal() {
        return SecurityUtils.subject.principal?.toString()
    }
}
