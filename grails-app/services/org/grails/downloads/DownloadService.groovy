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
     *           '1' : {
     *              download: null, binary: [], documentation: []
     *           }
     *       ]
     *   ]
     */
    def buildGroupedDownloads() {

        def groupedDownloads = [ beta: [:], stable: [:], latest: [:] ]
        for (download in Download.list()) {
            def downloadId = download.id
            def group = getAppropriateDownloadGroup(groupedDownloads, download)
            addDownloadToGroup(group, download)
        }
        println ">>> Grouped downloads: $groupedDownloads"
        return groupedDownloads
    }

    protected Map getAppropriateDownloadGroup(groups, download) {
        if (download.betaRelease) return groups["beta"]
        else if (download.latestRelease) return groups["latest"]
        else return groups["stable"]
    }

    protected Map addDownloadToGroup(group, download) {
        def downloadFilesMap = createInitialFilesMap()
        for (file in download.files) {
            if (!file) println ">> WTF?! ${download.files}"
            downloadFilesMap["download"] = download
            downloadFilesMap[getFileTypeGroupName(file.fileType)] << file
        }

        group[download.id] = downloadFilesMap
        return group
    }

    protected Map createInitialFilesMap() {
        return [download: null, binary: [], source: [], documentation: []]
    }

    protected String getFileTypeGroupName(FileType type) {
        switch (type) {
        case FileType.BINARY: return "binary"
        case FileType.SOURCE: return "source"
        case FileType.DOCUMENTATION: return "documentation"
        }
    }
}
