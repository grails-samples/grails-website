import org.apache.commons.codec.digest.DigestUtils
import org.grails.auth.*

fixture {
    build {
        def observer = Role.findByName(Role.OBSERVER)
        def editor = Role.findByName(Role.EDITOR)
        def admin = Role.findByName(Role.ADMINISTRATOR)
        
        peter(User,
                login: "peter",
                password: DigestUtils.shaHex("password"),
                email: "peter@somewhere.net",
                roles: [ observer, editor ],
                permissions: ["plugin:publish:shiro"])
        
        dilbert(User,
                login: "dilbert",
                password: DigestUtils.shaHex("password"),
                email: "dilbert@somewhere.net",
                roles: [ observer, editor ])
        
        spammer(User,
                login: "spammer",
                password: DigestUtils.shaHex("spam"),
                email: "spambot@spam.com",
                roles: [ observer ])
    }
}
