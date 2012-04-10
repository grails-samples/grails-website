package org.grails.learn

import org.grails.tutorials.Tutorial

class LearnController {

    def gettingStarted() { }

    def ideSetup() { }

    def installation() { }

    def quickStart() { }

    def screencasts() { }

    def tutorials() {
        def tutorials = Tutorial.list()
        [tutorials: tutorials]
    }

}
