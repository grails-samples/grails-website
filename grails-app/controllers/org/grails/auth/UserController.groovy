package org.grails.auth

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

    def scaffold = User

    def mailService

	String randomPass() {
		UUID uuid = UUID.randomUUID()
		uuid.toString()[0..7]
	}

    def update(Long id) {
        def user = User.get(id)

        if(user) {
            user.properties = params
            user.permissions.clear()
            user.permissions.addAll(params.list('permissions'))
            if(params.newPermission) {
                user.permissions.add(params.newPermission)
            }
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
        def renderParams = [ model:[originalURI:params.originalURI,
                      formData:params,
                      async:request.xhr] ]
        
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

                        if(params.originalURI) {

                            redirect(url:params.originalURI, params:params)
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
            def targetUri = params.originalURI ?: "/"
            
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
                    render(template:"loginForm", model:[originalURI:params.remove('originalURI'),
                                                        update: params._ul,
                                                        formData:params,
                                                        async:true,
                                                        message:"auth.invalid.login"])
                } else {
                    flash.message = "Invalid username and/or password"

                    redirect(action: 'login', params: [ username: params.username, originalURI:params.originalURI ])
                }
            }
        } else {            
            render(view:"login", model: [originalURI:params.originalURI])
        }
    }

    def unauthorized()  {}
}
