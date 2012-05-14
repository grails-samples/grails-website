package org.grails.plugin

/**
 * Stores a plugin release in the database
 */ 
class PendingRelease {

    String pluginName
    String pluginVersion
    byte[] zip
    byte[] pom
    byte[] xml

    static constraints = {
        pluginName blank: false
        pluginVersion blank: false
        zip size:0..50000000 // ~50mb
        pom size:0..500000 // 500kb
        xml size:0..500000 // 500kb
    }

    String toString() { "$pluginName:$pluginVersion" }
}
