package org.grails.meta

import org.grails.auth.User

/**
 * @author Graeme Rocher
 * @since 1.0
 * 
 * Created: May 14, 2008
 */
class UserInfo {

    User user
    boolean emailSubscribed
    String email
    String name
    
    static constraints = {
        user nullable: true
        email blank: false, email: true
        name blank: false
    }

}