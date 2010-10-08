package org.grails.test.pages

import geb.Page

class LoginPage extends Page {
    static url = "login"

    static at = {
        assert title == "Grails - Login Page"
        assert $("#contentPane").find("h1").text() == "Site Login"
        true
    }
}
