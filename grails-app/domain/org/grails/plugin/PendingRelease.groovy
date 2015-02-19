package org.grails.plugin

import org.joda.time.DateTime

/**
 * Stores a plugin release in the database
 */ 
class PendingRelease {

    String pluginName
    String pluginVersion
    byte[] zip
    byte[] pom
    byte[] xml
    DateTime dateCreated
    ReleaseStatus status = ReleaseStatus.PENDING

    static constraints = {
        pluginName blank: false
        pluginVersion blank: false
        zip nullable: true, size:0..50000000 // ~50mb
        pom nullable: true, size:0..500000 // 500kb
        xml nullable: true, size:0..500000 // 500kb
    }
    
    static mapping = {
        cache false
    }

    String toString() { "$pluginName:$pluginVersion" }
}

enum ReleaseStatus {
    // Don't change the toString() representations of these as the public API
    // implemented by PendingReleaseController uses them.
    PENDING, COMPLETED, FAILED
}
