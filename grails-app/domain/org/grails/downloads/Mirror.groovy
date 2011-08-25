package org.grails.downloads

class Mirror implements Serializable{
    String name
    String urlString
    URL url

    static belongsTo = [ file: DownloadFile ]

    static constraints = {
        url nullable: true
        urlString blank: false
        name blank:false
    }
}
