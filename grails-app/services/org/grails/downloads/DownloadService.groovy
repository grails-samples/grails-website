package org.grails.downloads

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
        def downloadListInstance = Download.list()
        downloadListInstance.eachWithIndex { value, idx ->
            Download download = value
            download.files.each { file ->

                if (download.betaRelease) {
                    if (!groupedDownloads['beta'][download.id.toString()]) {
                        groupedDownloads['beta'][download.id.toString()] = [download: null, binary: [], documentation: []]
                    }
                    groupedDownloads['beta'][download.id.toString()]['download'] = download
                    if (file.fileType == DownloadFile.TYPE_BINARY) {
                        groupedDownloads['beta'][download.id.toString()]['binary'].push(file)
                    } else {
                        groupedDownloads['beta'][download.id.toString()]['documentation'].push(file)
                    }

                } else if (download.latestRelease) {
                    if (!groupedDownloads['latest'][download.id.toString()]) {
                        groupedDownloads['latest'][download.id.toString()] = [download: null, binary: [], documentation: []]
                    }
                    groupedDownloads['latest'][download.id.toString()]['download'] = download
                    if (file.fileType == DownloadFile.TYPE_BINARY) {
                        groupedDownloads['latest'][download.id.toString()]['binary'].push(file)
                    } else {
                        groupedDownloads['latest'][download.id.toString()]['documentation'].push(file)
                    }

                } else {
                    if (!groupedDownloads['stable'][download.id.toString()]) {
                        groupedDownloads['stable'][download.id.toString()] = [download: null, binary: [], documentation: []]
                    }
                    groupedDownloads['stable'][download.id.toString()]['download'] = download
                    if (file.fileType == DownloadFile.TYPE_BINARY) {
                        groupedDownloads['stable'][download.id.toString()]['binary'].push(file)
                    } else {
                        groupedDownloads['stable'][download.id.toString()]['documentation'].push(file)
                    }
                }
            }
        }
        return groupedDownloads
    }
}
