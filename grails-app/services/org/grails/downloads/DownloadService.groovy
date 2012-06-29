package org.grails.downloads

import org.grails.downloads.DownloadFile.FileType

class DownloadService {

    def markAsLatest(Download download) {
        if (!download.latestRelease) {
            Download.executeUpdate("update Download d set d.latestRelease = false")
            download.latestRelease = true
            download.save(flush: true)
        }
    }

    /**
     *   [
     *       'beta': [
     *           [download: (Download), binary: [(DownloadFile)*], documentation: [(DownloadFile)*] ]
     *       ]
     *   ]
     */
    def buildGroupedDownloads() {

        def groupedDownloads = [ beta: [], stable: [], latest: [] ]
        for (download in Download.list(fetch: [files: "join"])) {
            def downloadId = download.id
            def group = getAppropriateDownloadGroup(groupedDownloads, download)
            addDownloadToGroup(group, download)
        }
        return groupedDownloads
    }

    def getLatestBinaryDownload() {
        def download = getLatestDownload()
        return [download, download.files.find { it.fileType == FileType.BINARY }]
    }

    protected List getAppropriateDownloadGroup(groups, download) {
        if (download.betaRelease) return groups["beta"]
        else if (download.latestRelease) return groups["latest"]
        else return groups["stable"]
    }

    protected Map addDownloadToGroup(group, download) {
        def downloadFilesMap = createInitialFilesMap()
        for (file in download.files) {
            downloadFilesMap["download"] = download
            downloadFilesMap[getFileTypeGroupName(file.fileType)] << file
        }

        group << downloadFilesMap
        return downloadFilesMap
    }

    protected Map createInitialFilesMap() {
        return [download: null, binary: [], source: [], documentation: []]
    }

    protected getLatestDownload() {
        return Download.where { latestRelease == true }.get()
    }

    protected String getFileTypeGroupName(FileType type) {
        switch (type) {
        case FileType.BINARY: return "binary"
        case FileType.SOURCE: return "source"
        case FileType.DOCUMENTATION: return "documentation"
        }
    }
}
