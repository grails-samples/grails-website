package org.grails.downloads

class Download implements Serializable{
    def grailsApplication
    
    Date releaseDate = new Date()                    
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
    }

    static mapping = {
        downloadCount column: '`count`'
    }
}
