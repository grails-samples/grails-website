package org.grails.downloads

class DownloadFile implements Serializable{

    enum FileType { BINARY, SOURCE, DOCUMENTATION }

    String title
    List mirrors
    FileType fileType

    static belongsTo = [download: Download]
    static hasMany = [mirrors: Mirror]
    static fetchMode = [mirrors: 'eager']

    static constraints = {
        mirrors minSize: 1
    }

    static mapping = {
        fileType enumType: "ordinal"
    }

    def getDisplayFileType() {
        return fileType.toString()
    }

    String toString() {
        "${download.toString()} - ${title}"
    }
}
