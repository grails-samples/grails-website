package org.grails.screencasts

import groovy.text.SimpleTemplateEngine
import org.codehaus.groovy.grails.plugins.codecs.HTMLCodec

class ScreencastsTagLib {
    static namespace = "casts"

    def embedPlayer = { attrs ->
        def screencast = attrs.screencast
        def width = attrs.width ?: 640
        def height = attrs.height ?: 385
        if (!screencast) throwTagError("Tag [embedPlayer] is missing required attribute [screencast]")
        if (!screencast.videoHost) throwTagError("This screencast cannot be embedded - it has no video host (e.g. YouTube)")

        def tmpl = new SimpleTemplateEngine().createTemplate(screencast.videoHost.embedTemplate)
        out << tmpl.make(width: width, height: height, videoId: screencast.videoId?.encodeAsHTML())
    }

    def processText = { attrs, body ->
        def txt = body()
        txt?.eachLine { line ->
            if (line) {
                out << "<p>" << linkify(encodeIfRequired(line)) << "</p>"
            }
        }
    }

    protected linkify(str) {
        return str?.replaceAll('([\\S\\+]+://\\S+)', '<a href="$1">$1</a>')
    }

    protected encodeIfRequired(str) {
        return HTMLCodec.shouldEncode() ? str?.encodeAsHTML() : str
    }
}
