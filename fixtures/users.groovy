import org.apache.commons.codec.digest.DigestUtils
import org.grails.auth.*
import org.grails.meta.UserInfo

fixture {
    build {
        def observer = Role.findByName(Role.OBSERVER)
        def editor = Role.findByName(Role.EDITOR)
        def admin = Role.findByName(Role.ADMINISTRATOR)
        
        peter(User,
                login: "peter",
                password: DigestUtils.shaHex("password"),
                email: "peter@somewhere.net",
                roles: [ observer, editor ])
        
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
                
        peterInfo(UserInfo,
                name: "Peter Ledbrook",
                email: "peter@somewhere.net",
                user: peter)
                
        springSourceInfo(UserInfo,
                name: "SpringSource",
                email: "springsource@somewhere.net")
                
        gpcInfo(UserInfo,
                name: "Grails Plugin Collective",
                email: "gpc@somewhere.net")
                
        tedInfo(UserInfo,
                name: "Ted Naleid",
                email: "ted@somewhere.net")
        
        anonymousInfo(UserInfo,
                name: "Anonymous",
                email: "anonymous@somewhere.net")
        
    }
}
