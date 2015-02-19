package org.grails.learn.screencasts

class VideoHost {
    String name
    String embedTemplate

    static constraints = {
        name blank: false, unique: true
        embedTemplate blank: false, maxSize: 5000
    }

    static transients = ['dependentScreencasts']
    
    static mapping = {
        cache true
    }

    def List<Screencast> getDependentScreencasts() {
        Screencast.where { videoHost == this }.list()
    }
}