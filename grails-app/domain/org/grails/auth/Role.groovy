package org.grails.auth
/**
 * @author Graeme Rocher
 * @since 1.0
 * 
 * Created: Feb 26, 2008
 */
class Role {

    static final ADMINISTRATOR = "Administrator"
    static final EDITOR = "Editor"
    static final OBSERVER = "Observer"
    
    String name

    static hasMany = [permissions: String]
    
    static constraints = {
        name blank: false, unique: true
    }
    
    static mapping = {
        cache true
    }

    String toString() {
        name
    }

    boolean equals(Object o) {
        if(o instanceof Role) {
            return this.name == o.name
        }
        return false
    }

    int hashCode() {
        name.hashCode()
    }
}
