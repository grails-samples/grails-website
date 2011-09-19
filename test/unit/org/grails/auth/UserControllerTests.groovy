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

import grails.test.GrailsUnitTestCase
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.web.util.WebUtils
import org.gmock.WithGMock
import org.grails.meta.UserInfo


/**
 * @author Graeme Rocher
 * @since 1.0
 *        <p/>
 *        Created: Feb 28, 2008
 */
@TestFor(UserController)
@Mock([User, UserInfo, Role])
@WithGMock
class UserControllerTests {

    void testRegisterGET() {
        params.originalURI = "/foo/bar"
        params.more = "stuff"

        controller.register()

        assert view == "/user/register"
        assert model.originalURI == "/foo/bar"
        assert model.formData == params

    }

    void testRegisterUserExists() {
        def user = new User(login: "fred").save(validate: false)
        params.originalURI = "/foo/bar"
        params.login = user.login
        request.method = "POST"

        controller.register()

        assert view == "/user/register"
        assert model.message == "auth.user.already.exists"
        assert model.originalURI == "/foo/bar"
        assert model.formData == params 
         
    }

    void testRegisterWithNonMatchingPasswords() {
        params.originalURI = "/foo/bar"
        params.login = "fred"
        params.password = "one"
        params.password2 = "two"
        request.method = "POST"

        controller.register()

        assert view == "/user/register"
        assert model.originalURI == "/foo/bar"
        assert model.formData == params
    }

    void testRegisterWithFormErrors() {
        new Role(name: Role.ADMINISTRATOR).save(validate: false)
        new Role(name: Role.EDITOR).save(validate: false)
        new Role(name: Role.OBSERVER).save(validate: false)

        params.originalURI = "/foo/bar"
        params.login = "fred"
        params.password = "one"
        params.password2 = "one"

        request.method = "POST"

        controller.register()

        assert view == "/user/register"
        assert model.user
        
        assert model.originalURI == "/foo/bar"
        assert model.formData == params
    }

    void testRegisterAndRedirectToOriginalPage() {
        def secUtil = mock(SecurityUtils)
        secUtil.static.subject.returns( [login: { authToken -> true }] )

        params.originalURI = "/foo/bar"
        params.login = "dilbert"
        params.password = "one"
        params.password2 = "one"
        params.email =  "dilbert@nowhere.org"

        new Role(name: Role.ADMINISTRATOR).save(validate: false)
        new Role(name: Role.EDITOR).save(validate: false)
        new Role(name: Role.OBSERVER).save(validate: false)

        request.method = "POST"

        play {
            controller.register()

            assert response.redirectedUrl == params.originalURI
        }
    }

    void testRedirectWithoutOriginalPage() {
        def secUtil = mock(SecurityUtils)
        secUtil.static.subject.returns( [login: { authToken -> true }] )

        params.login = "dilbert"
        params.password = "one"
        params.password2 = "one"
        params.email = "dilbert@nowhere.org"

        new Role(name: Role.ADMINISTRATOR).save(validate: false)
        new Role(name: Role.EDITOR).save(validate: false)
        new Role(name: Role.OBSERVER).save(validate: false)

        request.method = "POST"

        play {
            controller.register()

            assert response.redirectedUrl == "/"
        }
    }

    void testLoginWithGET() {

        controller.login()

        assert view == "/user/login"
    }

    void testLoginFailureWithAjaxRequest() {
        def secUtil = mock(SecurityUtils)
        secUtil.static.subject.returns( [login: { authToken ->
            throw new AuthenticationException("incorrect password")
        }] )

        def webUtil = mock(WebUtils)
        webUtil.static.getSavedRequest(request).returns(null)

        views['/user/_loginForm.gsp'] = 'login form ${message} ${originalURI} ${async}'

        params.originalURI = "/foo/bar"
        params.username = "fred"
        params.password = "letmein"

        request.method = "POST"
        request.makeAjaxRequest()

        play {
            controller.login()

            assert response.text == "login form auth.invalid.login /foo/bar true"
        }
    }

    void testLoginFailureWithRegularRequest() {
        def secUtil = mock(SecurityUtils)
        secUtil.static.subject.returns( [login: { authToken ->
            throw new AuthenticationException("incorrect password")
        }] )

        def webUtil = mock(WebUtils)
        webUtil.static.getSavedRequest(request).returns(null)

        params.originalURI = "/foo/bar"
        params.username = "fred"
        params.password = "letmein"

        request.method = "POST"

        play {
            controller.login()

            def redirectUri = new URI(response.redirectedUrl)
            assert redirectUri.path == "/user/login"
            assert redirectUri.query == "username=fred&originalURI=${params.originalURI}"
        }
    }

    void testLoginSuccessWithOriginalPage() {
        def secUtil = mock(SecurityUtils)
        secUtil.static.subject.returns( [login: { authToken -> true }] )

        def webUtil = mock(WebUtils)
        webUtil.static.getSavedRequest(request).returns(null)

        params.originalURI = "/foo/bar?queryString"
        params.username ="fred"
        params.password = "letmein"
        request.method = "POST"

        play {
            controller.login()

            assert response.redirectedUrl == "/foo/bar?queryString"
        }
    }

    void testLoginSuccessWithoutOriginalPage() {
        def secUtil = mock(SecurityUtils)
        secUtil.static.subject.returns( [login: { authToken -> true }] )

        def webUtil = mock(WebUtils)
        webUtil.static.getSavedRequest(request).returns(null)

        params.username = "fred"
        params.password = "letmein"

        request.method = "POST"

        play {
            controller.login()

            assert response.redirectedUrl == "/"
        }
    }
}
