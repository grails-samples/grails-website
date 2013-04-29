package org.grails.community

import org.grails.wiki.WikiPage

class CommunityController {

    def index() { }

    def websites() { }

    def contribute() { 
        forward controller:"content", action:"index", id:"Contribute"
    }

    def mailingList() { }

    def twitter() { }

}
