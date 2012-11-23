package org.grails.downloads

class DownloadFile implements Serializable{

    enum FileType { BINARY, SOURCE, DOCUMENTATION }

    String title
    List mirrors
    FileType fileType

    static belongsTo = [download: Download]
    static hasMany = [mirrors: Mirror]

    static constraints = {
        mirrors minSize: 1
    }

    static mapping = {
        fileType enumType: "ordinal"
        mirrors lazy: false
    }

    def getDisplayFileType() {
        return fileType.toString()
    }

    String toString() {
        "${download.toString()} - ${title}"
    }
}
