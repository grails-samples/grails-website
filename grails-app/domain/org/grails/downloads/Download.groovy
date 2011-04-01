package org.grails.downloads

class Download implements Serializable{
    def grailsApplication
    
    Date releaseDate = new Date()                    
    String softwareName
    String softwareVersion
    int count
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
        count min:0
    }

    static mapping = {
        count column: '`count`'
    }
}
