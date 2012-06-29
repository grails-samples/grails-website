package org.grails.learn

class LearnController {
    def downloadService

    def gettingStarted() {
        def (download, binaryFile) = downloadService.getLatestBinaryDownload()
        return [latestDownload: binaryFile, latestVersion: download.softwareVersion]
    }

    def ideSetup() { }

    def installation() { }

    def quickStart() { }

    def screencasts() { }

    def tutorials() {
    }

}
