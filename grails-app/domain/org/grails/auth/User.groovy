package org.grails.auth

import org.grails.plugin.PluginPendingApproval

class User {
    String email
    String login
    String password

    static hasMany = [
            roles:Role,
            permissions:String,
            pluginPendingApprovals:PluginPendingApproval
    ]

    static constraints = {
        email email: true, unique: true, blank: false
        login blank: false, size: 5..15
        password blank: false, nullable: false, display: false
    }

    static mapping = {
        cache true
    }

    String toString() {
        login
    }
}
