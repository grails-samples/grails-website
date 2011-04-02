package org.grails.downloads

class Mirror implements Serializable{
    String name
    URL url
    static belongsTo = [ file: DownloadFile ]

    static constraints = {
        url nullable:false
        name blank:false
    }
}
