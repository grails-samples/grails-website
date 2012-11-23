/* Copyright 2004-2005 Graeme Rocher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.auth

import grails.test.mixin.*
import grails.test.GrailsUnitTestCase
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.util.WebUtils
import org.grails.meta.UserInfo

import javax.servlet.http.HttpServletRequest


/**
 * @author Graeme Rocher
 * @since 1.0
 *        <p/>
 *        Created: Feb 28, 2008
 */
@TestFor(UserController)
@Mock([User, UserInfo, Role])
class UserControllerTests {

    void testRegisterGET() {
        params.more = "stuff"

        controller.register()

        assert view == "/user/register"
    }

    void testRegisterUserExists() {
        def user = new User(login: "fred").save(validate: false)
        params.login = user.login
        request.method = "POST"

        controller.register()

        assert view == "/user/register"
        assert model.message == "auth.user.already.exists"
    }

    void testRegisterWithNonMatchingPasswords() {
        params.login = "fred"
        params.password = "one"
        params.password2 = "two"
        request.method = "POST"

        controller.register()

        assert view == "/user/register"
    }

    void testRegisterWithFormErrors() {
        new Role(name: Role.ADMINISTRATOR).save(validate: false)
        new Role(name: Role.EDITOR).save(validate: false)
        new Role(name: Role.OBSERVER).save(validate: false)

        params.login = "fred"
        params.password = "one"
        params.password2 = "one"

        request.method = "POST"

        controller.register()

        assert view == "/user/register"
        assert model.user.login == "fred"
    }

    void testRegisterAndRedirectToOriginalPage() {
        def secUtil = mockFor(SecurityUtils)
        secUtil.demand.static.getSubject {-> [login: {authToken -> true}] }

        params.login = "dilbert"
        params.password = "one"
        params.password2 = "one"
        params.email =  "dilbert@nowhere.org"

        new Role(name: Role.ADMINISTRATOR).save(validate: false)
        new Role(name: Role.EDITOR).save(validate: false)
        new Role(name: Role.OBSERVER).save(validate: false)

        request.method = "POST"

        controller.register()


        secUtil.verify()
    }

    void testRedirectWithoutOriginalPage() {
        def secUtil = mockFor(SecurityUtils)
        secUtil.demand.static.getSubject {-> [login: {authToken -> true}] }

        params.login = "dilbert"
        params.password = "one"
        params.password2 = "one"
        params.email = "dilbert@nowhere.org"

        new Role(name: Role.ADMINISTRATOR).save(validate: false)
        new Role(name: Role.EDITOR).save(validate: false)
        new Role(name: Role.OBSERVER).save(validate: false)

        request.method = "POST"

        controller.register()

        assert response.redirectedUrl == "/"

        secUtil.verify()
    }

    void testLoginWithGET() {

        controller.login()

        assert view == "/user/login"
    }

    void testLoginFailureWithAjaxRequest() {
        def secUtil = mockFor(SecurityUtils)
        secUtil.demand.static.getSubject {-> [login: { authToken ->
            throw new AuthenticationException("incorrect password")
        }] }

        def webUtil = mockFor(WebUtils)
        webUtil.demand.static.getSavedRequest { HttpServletRequest request -> return null }

        views['/user/_loginForm.gsp'] = 'login form ${message} ${async}'

        params.username = "fred"
        params.password = "letmein"

        request.method = "POST"
        request.makeAjaxRequest()

        controller.login()

        assert response.text == "login form auth.invalid.login true"

        secUtil.verify()
        webUtil.verify()
    }

    void testLoginFailureWithRegularRequest() {
        def secUtil = mockFor(SecurityUtils)
        secUtil.demand.static.getSubject {-> [login: { authToken ->
            throw new AuthenticationException("incorrect password")
        }] }

        def webUtil = mockFor(WebUtils)
        webUtil.demand.static.getSavedRequest { HttpServletRequest request -> return null }

        params.username = "fred"
        params.password = "letmein"

        request.method = "POST"
        controller.login()

        def redirectUri = new URI(response.redirectedUrl)
        assert redirectUri.path == "/user/login"

        secUtil.verify()
        webUtil.verify()
    }

    void testLoginSuccessWithOriginalPage() {
        def secUtil = mockFor(SecurityUtils)
        secUtil.demand.static.getSubject {-> [login: { authToken -> true }] }

        def webUtil = mockFor(WebUtils)
        webUtil.demand.static.getSavedRequest { HttpServletRequest request -> return null }

        params.targetUri= "/foo/bar?queryString"
        params.username ="fred"
        params.password = "letmein"
        request.method = "POST"

        controller.login()

        assert response.redirectedUrl == "/foo/bar?queryString"

        secUtil.verify()
        webUtil.verify()
    }

    void testLoginSuccessWithoutOriginalPage() {
        def secUtil = mockFor(SecurityUtils)
        secUtil.demand.static.getSubject {-> [login: { authToken -> true }] }

        def webUtil = mockFor(WebUtils)
        webUtil.demand.static.getSavedRequest { HttpServletRequest request -> return null }

        params.username = "fred"
        params.password = "letmein"

        request.method = "POST"

        controller.login()

        assert response.redirectedUrl == "/"

        secUtil.verify()
        webUtil.verify()
    }
}
