package org.grails.plugin

import org.joda.time.DateTime

/**
 * Models a plugin release
 *
 * @author Graeme Rocher
 */
class PluginRelease {
    DateTime releaseDate = new DateTime()
    String releaseVersion
    String downloadUrl
    boolean isSnapshot

    static belongsTo = [plugin:Plugin]
    
    static constraints = {
        releaseDate nullable:false
        releaseVersion blank:false
        downloadUrl blank:false
        isSnapshot()
    }

    static mapping = {
        version false        
    }
}
