package org.grails.downloads

class VersionOrder {
    String baseVersion
    int    orderIndex

    static constraints = {
        baseVersion blank: false
        orderIndex unique: true
    }
}
