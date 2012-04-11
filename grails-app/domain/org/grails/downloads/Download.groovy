package org.grails.downloads

import org.joda.time.DateTime

class Download implements Serializable{
    def grailsApplication
    
    DateTime releaseDate = new DateTime()                    
    String softwareName
    String softwareVersion
    int downloadCount
    Boolean betaRelease = false

    List files
    static hasMany = [files:DownloadFile]   

    static transients = ['releaseNotes']

    def getReleaseNotes() {
        return "/${softwareVersion}+Release+Notes"
    }

    static constraints = {
        softwareName blank:false
        softwareVersion blank:false
        downloadCount min:0
        releaseDate nullable:true, widget:'datePicker'
    }

    static mapping = {
        downloadCount column: '`count`'
    }
}
