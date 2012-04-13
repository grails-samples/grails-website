package org.grails.downloads

import org.joda.time.DateTime

class Download implements Serializable{
    def grailsApplication
    
    DateTime releaseDate = new DateTime()                    
    String softwareName
    String softwareVersion
    int downloadCount // This is no longer needed.
    Boolean betaRelease = false
    Boolean latestRelease = false

    List files
    static hasMany = [files:DownloadFile]   

    static transients = ['releaseNotes']

    def getReleaseNotes() {
        return "/${softwareVersion}+Release+Notes"
    }

    static constraints = {
        softwareName blank:false
        softwareVersion blank:false
        downloadCount nullable: true
        releaseDate blank:false
        latestRelease nullable:true
    }

    static mapping = {
        downloadCount column: '`count`'
    }

    def String toString() {
        "${softwareName} ${softwareVersion}"
    }
}
