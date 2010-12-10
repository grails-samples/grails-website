
import grails.util.Environment
import javax.servlet.http.HttpServletRequest

import org.apache.commons.codec.digest.DigestUtils
import org.grails.*
import org.grails.auth.Role
import org.grails.auth.User

class BootStrap {
    def searchableService

    def init = { servletContext ->
        HttpServletRequest.metaClass.isXhr = {->
            'XMLHttpRequest' == delegate.getHeader('X-Requested-With')                
        }

        def admin = User.findByLogin("admin")
        if (!admin) {
            def password = Environment.current == Environment.TEST ? "changeit" : System.getProperty("initial.admin.password")
            if (!password) {
                throw new Exception("""
During the first run you must specify a password to use for the admin account. For example:

grails -Dinitial.admin.password=changeit run-app""")
            }
            else {
                def user = new User(login:"admin", email:"info@g2one.com",password:DigestUtils.shaHex(password))
                assert user.email
                assert user.addToRoles(name:Role.ADMINISTRATOR)
                           .addToRoles(name:Role.EDITOR)
                           .addToRoles(name:Role.OBSERVER)
                           .save(flush:true)
            }
        }
        else if (!admin.roles) {
            admin.addToRoles(name:Role.ADMINISTRATOR)
                 .addToRoles(name:Role.EDITOR)
                 .addToRoles(name:Role.OBSERVER)
                 .save(flush:true)
        }

        // We manually start the mirroring process to ensure that it comes after
        // Autobase performs its migrations.
        searchableService.startMirroring()
    }

    def destroy = {
    }
} 
