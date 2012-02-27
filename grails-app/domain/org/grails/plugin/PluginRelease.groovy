package org.grails.plugin

import org.joda.time.DateTime

/**
 * Models a plugin release
 *
 * @author Graeme Rocher
 */
class PluginRelease {
    static belongsTo = [plugin:Plugin]
    DateTime releaseDate = new DateTime()
    String releaseVersion
    String downloadUrl
    
    static constraints = {
        releaseDate nullable:false
        releaseVersion blank:false
        downloadUrl blank:false
    }
    
    static mapping = {
        id generator:'foreign', params:[property:'plugin']
        version false        
    }
}