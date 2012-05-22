package org.grails.downloads

class Mirror implements Serializable{
    String name
    String urlString

    static belongsTo = [ file: DownloadFile ]

    static constraints = {
        urlString blank: false
        name blank:false
    }

    String toString() {
        "${name} (${urlString})"
    }
}
