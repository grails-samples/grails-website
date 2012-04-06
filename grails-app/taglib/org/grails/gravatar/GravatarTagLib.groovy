package org.grails.gravatar

class GravatarTagLib {
    static namespace = "gravatar"

    def img = { attrs, body ->
        if (!attrs.email) throw new IllegalStateException("Property [email] must be set!")
        def email = attrs.remove('email').toString()
        def hash = email.encodeAsMD5()
        def size = attrs.remove('size').toString() ?: 20
        def align = attrs.remove('align').toString() ?: ''
        def gravatarBaseUrl	= request.isSecure() ? "https://secure.gravatar.com/avatar/" : "http://gravatar.com/avatar/"
        String gravatarUrl	= "$gravatarBaseUrl$hash"
        gravatarUrl += "&s=${size}"
        out << """<img height="$size" width="$size" src="$gravatarUrl" align="$align" />"""
    }

}
