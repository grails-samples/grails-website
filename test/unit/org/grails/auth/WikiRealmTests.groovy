package org.grails.auth

import org.apache.commons.logging.LogFactory
import org.apache.shiro.authc.*
import org.apache.shiro.authc.credential.CredentialsMatcher
import grails.test.mixin.*
import static org.junit.Assert.*
/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 28, 2008
*/
@Mock([User, Role])
class WikiRealmTests {

    void testAuthenticate() {
        def realm = new WikiRealm()

        shouldFail(AccountException) {
            realm.authenticate( [:] )
        }

        shouldFail(UnknownAccountException) {
            realm.authenticate username:"Freddy"
        }
    }

    void testAuthenticateDisabledUser() {
        def realm = new WikiRealm()

        realm.credentialMatcher = [doCredentialsMatch:{ AuthenticationToken authenticationToken, Account account-> false }] as CredentialsMatcher
        new User(login:"Freddy", enabled:false).save(flush:true, validate:false)

        assert User.count() == 1
        assert User.findByLogin("Freddy").enabled == false

        shouldFail(DisabledAccountException) {
            realm.authenticate( new UsernamePasswordToken("Freddy", "Frog"))
        }
    }

    void testAuthenticateBadPassword() {
        def realm = new WikiRealm()
        realm.credentialMatcher = [doCredentialsMatch:{ AuthenticationToken authenticationToken, Account account-> false }] as CredentialsMatcher

        new User(login: "Freddy", password: "sdfds", email: "fred@nowhere.net").save()

        shouldFail(IncorrectCredentialsException) {
            realm.authenticate( new UsernamePasswordToken("Freddy", "Frog" ) )
        }

        realm.credentialMatcher = [doCredentialsMatch:{ AuthenticationToken authenticationToken, Account account-> true }] as CredentialsMatcher

        assert realm.authenticate(new UsernamePasswordToken("Freddy", "Frog" )).principals.oneByType(String) == "Freddy"
    }

    void testHasRole() {
        User.metaClass.static.findByLogin = { String s -> 
            return null 
        }
        
        WikiRealm.metaClass.getLog = {-> LogFactory.getLog(WikiRealm) }
        def realm = new WikiRealm()

        assert ! realm.hasRole("Freddy", "Administrator")

        User.metaClass.getRoles={-> [] }
        User.metaClass.static.findByLogin = { String s -> new User(login:"Freddy") }

        assert ! realm.hasRole("Freddy", "Administrator")

        User.metaClass.getRoles={-> [[name:"Administrator"]] }

        assertTrue realm.hasRole("Freddy", "Administrator")

    }

    void testHasAllRoles() {
        WikiRealm.metaClass.getLog = {-> LogFactory.getLog(WikiRealm) }
        def realm = new WikiRealm()

        User.metaClass.static.createCriteria = {-> [list:{Closure c -> [] }] }

        assert ! realm.hasAllRoles( [name:"Freddy"], ["Administrator", "Editor"])

        User.metaClass.static.createCriteria = {-> [list:{Closure c -> ["Editor"] }] }

        assert ! realm.hasAllRoles( [name:"Freddy"], ["Administrator", "Editor"])

        def u = new User(login:"Freddy")
                    .addToRoles(new Role(name:"Administrator"))
                    .addToRoles(new Role(name: "Editor"))
                    .save(flush:true, validate:false)
        assert User.count() == 1
        assert realm.hasAllRoles( "Freddy", [new Role(name:"Administrator"), new Role(name:"Editor")])
    }


    void testIsPermitted() {
        // permissions not implemented, placeholder
    }
}
