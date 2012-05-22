package org.grails.downloads

class DownloadFile implements Serializable{

    static final int TYPE_BINARY = 1
    static final int TYPE_DOCUMENTATION = 2

    String title
    List mirrors
    int fileType

    static belongsTo = [download: Download]
    static hasMany = [mirrors:Mirror]
    static fetchMode = [mirrors:'eager']

    static constraints = {
        blank:false
        fileType(blank: false, inList: [TYPE_BINARY, TYPE_DOCUMENTATION])
        mirrors minSize:1, lazy:false
    }

    def getDisplayFileType() {
        switch (fileType) {
            case TYPE_BINARY:
                'Binary Zip'
                break
            case TYPE_DOCUMENTATION:
                'Documentation'
                break
        }
    }

    String toString() {
        "${download.toString()} - ${title}"
    }
}
