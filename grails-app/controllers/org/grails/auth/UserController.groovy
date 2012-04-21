package org.grails.auth

import grails.validation.Validateable

import org.apache.commons.codec.digest.DigestUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.util.WebUtils

import org.grails.auth.User
import org.grails.meta.UserInfo

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 19, 2008
*/
class UserController {
    private static final String ACCOUNT_SESSION_KEY = "accountCommand"

    def scaffold = User

    def mailService
    def userService

    def show() {
        if (!params.id) {
            render status: 404, text: "No ID specified"
            return
        }

        def user
        if (params.id.isLong()) {
            user = User.get(params.id)
        }
        else {
            user = User.findByLogin(params.id)
        }

        if (!user) {
            render status: 404, text: "No user found with ID '${params.id}'"
            return
        }

        [userInstance: user]
    }

    def update(Long id) {
        def user = User.get(id)

        if(user) {
            bindData user, params, [exclude: ["permissions"]]
            setPermissionsFromString user, params.permissions

            if(!user.save(flush:true)) {
               render view:"edit", model:[userInstance:user]
            }
            else {
               redirect action:"show", id: user.id
            }
        } else {
            redirect action:"list"
        }   
    }

    def passwordReminder() {
        if(request.method == 'POST') {
            def user = User.findByLogin(params.login)
            if(user && user.login!='admin') {
                def newPassword = randomPass()
                user.password = DigestUtils.shaHex(newPassword)
                user.save()
                mailService.sendMail {
                    from "wiki@grails.org"
                    to user.email
                    title "Grails.org password reset"
                    body "Your password has been reset. Please login with the following password: ${newPassword}"
                }
            }
            else {
                flash.message = "Username not found"
            }
        }
    }

    def profile() {
        def userInfo = UserInfo.findByUser(request.user)
        if(request.method == 'POST') {
            if(!userInfo) userInfo = new UserInfo(user:request.user)
            userInfo.properties = params
            userInfo.save()
            if(params.password) {
                request.user.password = DigestUtils.shaHex(params.password) 
                request.user.save()
            }
        }
        return [user:request.user, userInfo:userInfo]

    }

    def register(){
        def renderParams = [ model:[targetUri:params.targetUri, async:request.xhr] ]
        
        if(request.xhr)
            renderParams.template = "registerForm"
        else
            renderParams.view = "register"

        if(request.method == 'POST') {
            if (!params.login) {
                log.warn "'login' not provided during registration - probably a spam bot."
                renderParams.model.message= "You must provide a login!"
                render(renderParams)
                return
            }

            def user = User.findByLogin(params.login)
            if(user) {

                renderParams.model.message= "auth.user.already.exists"
                render(renderParams)
            }
            else {
                if(params.password != params.password2) {
                    renderParams.model.message= "auth.password.mismatch"
                    render(renderParams)

                }
                else {

                    user = new User(login:params.login, password: (params.password ? DigestUtils.shaHex(params.password) : null), email:params.email)
                            .addToRoles(Role.findByName(Role.EDITOR))
                            .addToRoles(Role.findByName(Role.OBSERVER))

                    if(!user.hasErrors() && user.save(flush:true)) {
                        def userInfo = new UserInfo(params)
                        userInfo.user = user
                        userInfo.save()
                        
                        def authToken = new UsernamePasswordToken(user.login, params.password)
                        SecurityUtils.subject.login(authToken)

                        if(params.targetUri) {

                            redirect(url:params.targetUri, params:params)
                        }
                        else {
                            redirect(uri:"/")
                        }
                    }
                    else {
                        renderParams.model.user = user
                        render(renderParams)
                    }
                }


            }
        }
        else {
            render(renderParams)
        }

    }

    /**
     * Page that allows users to link an OAuth account to an existing or new
     * Shiro account.
     */
    def askToLinkOrCreateAccount() {
        def cmd = session[ACCOUNT_SESSION_KEY]
        session.removeAttribute ACCOUNT_SESSION_KEY

        def token = session["shiroAuthToken"]
        println "Access token: ${session.oasAccessToken.rawResponse}"
        [login: cmd?.login ?: token.principal, email: cmd?.email, bean: cmd]
    }

    /**
     * Links an oauth account to an existing Shiro account if the submitted
     * credentials are correct. Otherwise it redirects back to the page that
     * asks the user for those credentials.
     */
    def linkAccount(AccountCommand cmd) {
        if (!handleCommandForLinkingAccounts(cmd)) return

        try {
            def userId = userService.loginUser(params.login, params.password)
            forward controller: "shiroOAuth", action: "linkAccount", params: [userId: userId]
        }
        catch (AuthenticationException) {
            cmd.errors.reject "auth.invalid.login", "Username or password is invalid"
            redirectToAskToLinkPage cmd
        }
    }

    /**
     * Creates a new Shiro account and links it to the OAuth token that's in
     * the current HTTP session.
     */
    def createAccount(AccountCommand cmd) {
        if (!handleCommandForLinkingAccounts(cmd)) return

        def user = userService.createUser(params.login, params.email)
        forward controller: "shiroOAuth", action: "linkAccount", params: [userId: user.id]
    }

    def logout() {
        SecurityUtils.subject.logout()
        redirect(uri:"/")
    }

    def login() {
        if(request.method == 'POST') {
            def authToken = new UsernamePasswordToken(params.login, params.password)

            // Support for "remember me"
            if (params.rememberMe) {
                authToken.rememberMe = true
            }

            // If a controller redirected to this page, redirect back
            // to it. Otherwise redirect to the root URI.
            def targetUri = params.targetUri ?: "/"
            
            // Handle requests saved by Shiro filters.
            def savedRequest = WebUtils.getSavedRequest(request)
            if (savedRequest) {
                targetUri = savedRequest.requestURI - request.contextPath
                if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
            }
        
            try {
                SecurityUtils.subject.login(authToken)

                log.info "Redirecting to '${targetUri}'."
                redirect(uri: targetUri)
            } catch (AuthenticationException ex){
                log.info "Authentication failure for user '${params.username}'."
                if(request.xhr) {
                    params.remove 'password'
                    render(template:"loginForm", model:[targetUri:params.remove('targetUri'),
                                                        update: params._ul,
                                                        async:true,
                                                        message:"auth.invalid.login"])
                } else {
                    flash.message = "Invalid username and/or password"

                    redirect(action: 'login', params: [ username: params.username, targetUri:params.targetUri ])
                }
            }
        } else {            
            render(view:"login", model: [targetUri:params.targetUri])
        }
    }

    def unauthorized()  {}

    /**
     * Checks for errors in the command object and if there are any, puts the
     * command object in the session and redirects to the page asking the user
     * for the account to link to. If there are no errors, then the object is
     * removed from the session instead.
     */
    protected handleCommandForLinkingAccounts(cmd) {
        if (cmd.hasErrors()) {
            redirectToAskToLinkPage cmd
            return false
        }
        else {
            session.removeAttribute ACCOUNT_SESSION_KEY
            return true
        }
    }

    /**
     * Puts the given command object in the session and redirects to the page
     * asking the user for an account to link to.
     */
    protected redirectToAskToLinkPage(cmd) {
        session[ACCOUNT_SESSION_KEY] = cmd
        redirect action: "askToLinkOrCreateAccount"
    }

    protected String randomPass() {
        UUID uuid = UUID.randomUUID()
        uuid.toString()[0..7]
    }

    protected setPermissionsFromString(user, String newlineSeparatedPermissions) {
        newlineSeparatedPermissions = newlineSeparatedPermissions?.trim()
        def perms = !newlineSeparatedPermissions ? [] : (newlineSeparatedPermissions.split(/\s*[\n;]\s*/) as List)

        // Take the simple approach: clear the list and re-add all declared permissions.
        if (user.permissions == null) {
            user.permissions = perms
        }
        else {
            user.permissions.clear()
            user.permissions.addAll perms
        }
    }
}

@Validateable
class AccountCommand {
    transient userService

    String login
    String email

    static constraints = {
        login nullable: false, blank: false, validator: { obj, val ->
            obj.userService.isLoginUnique(val) ? null : "user.login.unique"
        }
        email nullable: false, blank: false, validator: { obj, val ->
            obj.userService.isEmailUnique(val) ? null : "user.email.unique"
        }
    }
}
