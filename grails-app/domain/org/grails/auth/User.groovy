package org.grails.auth

import org.apache.commons.codec.digest.DigestUtils

class User {
    String email
    String login
    String password

    static hasMany = [roles:Role]

    static constraints = {
        email email: true, unique: true, blank: false
        login blank: false, size: 5..15
        password blank: false, nullable: false
    }

    static mapping = {
        cache true
    }

    def updatePassword(passwd) {
        this.password = passwd ? DigestUtils.shaHex(passwd) : null
        return this
    }

    def addStandardRoles() {
        def roles = Role.findAllByNameInList([Role.EDITOR, Role.OBSERVER])
//        println ">> Roles: $roles"
        for (r in roles) {
            this.addToRoles r
        }
//        println ">> This user's roles: ${this.roles}"
        return this
    }

    String toString() {
        login
    }
}
