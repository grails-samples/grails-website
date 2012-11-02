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
        // UserInfo instances need to be created for plugin authors, who
        // may only be in the database as a name, an email address or
        // both. They may also not be users in grails.org, hence why 'user'
        // must be optional too.
        user nullable: true
        email nullable: true, email: true
        name nullable: true
    }

}
