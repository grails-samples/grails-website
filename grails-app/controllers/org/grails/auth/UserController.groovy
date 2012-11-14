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

    static scaffold = User

    transient mailService
    transient userService

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
    
    def search(String q) {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def results = User.findAllByLoginLike("%$q%", params)
        def total = User.countByLoginLike("%$q%")
        render view:'list', model:[userInstanceList:results, userInstanceTotal:total]
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
            def user = User.findByLoginOrEmail(params.login, params.login)
            if(user && user.login!='admin') {
                def newPassword = randomPass()
                user.password = DigestUtils.shaHex(newPassword)
                user.save()
                flash.message = "A password reminder was sent to your email address"
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
        def userId = SecurityUtils.subject.principals.oneByType(Number)
        def user = User.get(userId)
        def userInfo = UserInfo.findByUser(user)
        if (request.method == 'POST') {
            if (!userInfo) userInfo = new UserInfo(user: user)
            bindData userInfo, params, [include: ["email", "name", "emailSubscribed"]]
            userInfo.save()
            if (params.password) {
                user.password = DigestUtils.shaHex(params.password) 
                user.save()
            }

            flash.message = "Settings changed"
            redirect action: "profile"
        }
        else {
            return [user: user, userInfo: userInfo]
        }
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
        [login: cmd?.login ?: token.principal, email: cmd?.email, bean: cmd]
    }

    /**
     * Links an oauth account to an existing Shiro account if the submitted
     * credentials are correct. Otherwise it redirects back to the page that
     * asks the user for those credentials.
     */
    def linkAccount(LoginAccountCommand cmd) {
        if (!handleCommandForLinkingAccounts(cmd)) return

        try {
            def userId = userService.loginUser(cmd.login, cmd.password)
            forwardToShiroLinkAccount userId
        }
        catch (AuthenticationException ex) {
            cmd.errors.reject "auth.invalid.login", "Username or password is invalid"
            redirectToAskToLinkPage cmd
        }
    }

    /**
     * Creates a new Shiro account and links it to the OAuth token that's in
     * the current HTTP session.
     */
    def createAccount(NewAccountCommand cmd) {
        if (!handleCommandForLinkingAccounts(cmd)) return

        def user = userService.createUser(cmd.login, cmd.email)
        forwardToShiroLinkAccount user.id
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
            } catch(org.apache.shiro.authc.DisabledAccountException ex) {
                flash.message = "Your account has been disabled"
                redirect(action: 'login', params: [ username: params.username, targetUri:params.targetUri ])
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
            if (params.targetUri) session["targetUri"] = params.targetUri
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
     * Forward the current request to the ShiroOAuthController in order to
     * link the user with the given ID to the current OAuth principal. This
     * also ensures that the {@code targetUri} parameter is appropriately
     * set.
     */
    protected forwardToShiroLinkAccount(userId) {
        def newParams = [userId: userId]
        if (!session["targetUri"]) {
            newParams["targetUri"] = "/"
        }

        forward controller: "shiroOAuth", action: "linkAccount", params: newParams
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
        userService.updateUserPemissions(user, perms)
    }
}

@Validateable
class LoginAccountCommand {
    transient userService

    String login
    String password
    String email

    static constraints = {
        login nullable: false, blank: false
        password nullable: false, blank: false
    }
}

@Validateable
class NewAccountCommand {
    transient userService

    String login
    String email

    String password

    static constraints = {
        login nullable: false, blank: false, validator: { val, obj ->
            obj.userService.isLoginUnique(val) ? null : "user.login.unique"
        }
        email nullable: false, blank: false, validator: { val, obj ->
            obj.userService.isEmailUnique(val) ? null : "user.email.unique"
        }
    }
}
