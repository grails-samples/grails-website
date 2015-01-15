package org.grails.downloads

import grails.plugin.cache.Cacheable
import org.codehaus.groovy.grails.plugins.VersionComparator
import org.grails.downloads.DownloadFile.FileType

class DownloadService {
    private static final MAJOR_VERSION_PATTERN = ~/(\d+\.\d+).*/
    private static final SOFTWARE_VERSION_COMPARATOR = new VersionComparator()
    private static final REVERSE_VERSION_COMPARATOR = { a, b -> SOFTWARE_VERSION_COMPARATOR.compare(b, a) } as Comparator

    /**
     * <p>This comparator extends SOFTWARE_VERSION_COMPARATOR by ensuring that
     * non-beta releases appear before beta releases. The 'highest' version
     * comes first.</p>
     * <p>Note that this comparator only works with objects that have a {@code
     * download} property whose value in turn has a {@code betaRelease} boolean
     * and a string {@code sofwareVersion}. In other words, it's not very general
     * purpose!</p>
     */
    private static final BETA_AWARE_VERSION_COMPARATOR = { a, b ->
        def aD = a.download
        def bD = b.download
        if (aD.betaRelease == bD.betaRelease) {
            if (aD.betaRelease) return bD.softwareVersion <=> aD.softwareVersion
            else return REVERSE_VERSION_COMPARATOR.compare(aD.softwareVersion, bD.softwareVersion)
        }
        else return aD.betaRelease <=> bD.betaRelease
    }

    def markAsLatest(Download download) {
        if (!download.latestRelease) {
            Download.executeUpdate("update Download d set d.latestRelease = false")
            download.latestRelease = true
            download.save(flush: true)
        }
    }

    def listMajorVersions(versions = null) {
        if (!versions) versions = Download.where { softwareName == "Grails" }.property("softwareVersion").list()

        def majorVersions = new HashSet(versions.size())
        for (v in versions) {
            def m = MAJOR_VERSION_PATTERN.matcher(v)
            if (m.matches()) majorVersions << m[0][1]
        }

        return majorVersions.sort(false, new VersionComparator()).reverse()
    }

    def listMajorVersionDownloads() {
        def versions = Download.where { softwareName == "Grails" }.list()

        def majorVersions = new HashSet(versions.size())
        for (v in versions) {
            def m = MAJOR_VERSION_PATTERN.matcher(v.softwareVersion)
            if (m.matches()) majorVersions << v
        }
        def comparator = new VersionComparator()

        return majorVersions.sort(false) { a, b-> comparator.compare(a.softwareVersion, b.softwareVersion) }.reverse()
    }

    def sortVersions(versions) {
        versions.sort(false, new VersionComparator())
    }

    /**
     * Fetches all the download information in the database grouped by the
     * Grails major version number ("1.1", "2.0", etc.). Each value is a
     * map of the form documented by the {@link #groupDownloadFilesAsMap(Object)}
     * method.
     */
    @Cacheable(value="downloads", key="'byVersion'")
    def getDownloadsByMajorVersion() {
        // The single quotes on the cache key are important! Without them,
        // SpEL treats the name as a property.

        // Get all downloads from the database and then collate the information
        // by major version.
        def downloads = Download.where { softwareName == "Grails" }
                .list()
                .collect { d -> groupDownloadFilesAsMap(d) }
                .groupBy { map -> extractMajorVersion(map.download.softwareVersion) }

        for (entry in downloads) {
            entry.value.sort true, BETA_AWARE_VERSION_COMPARATOR
        }
        return downloads
    }

    /**
     * Returns a tuple containing the latest stable (non-milestone) release
     * of Grails. The first element is the {@code Download} instance and the
     * second is the corresponding {@code DownloadFile} instance for the binary
     * download.
     */
    def getLatestBinaryDownload() {
        def download = getLatestDownload()
        return [download, download.files.find { it.fileType == FileType.BINARY }]
    }

    /**
     * Returns a map of information for a given {@code Download} instance.
     * This map takes the form:
     * <pre>
     * [ download: &lt;Download instance>,
     *   binary: &lt;List of DownloadFile instances>,
     *   source: &lt;List of DownloadFile instances>,
     *   documentation: &lt;List of DownloadFile instances>
     * ]
     * </pre>
     * The key advantage of this method is that you get the download files
     * grouped by their type, i.e. binary, source, or documentation.
     */
    protected Map groupDownloadFilesAsMap(download) {
        def downloadFilesMap = createInitialFilesMap()
        for (file in download.files) {
            downloadFilesMap["download"] = download
            downloadFilesMap[getFileTypeGroupName(file.fileType)] << file
        }

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

    /**
     * Given a version number, like "2.0.1" or "2.1.0.RC1", this method
     * returns the major version, i.e. "2.0" and "2.1" for the previous
     * examples.
     */
    protected extractMajorVersion(String softwareVersion) {
        def m = MAJOR_VERSION_PATTERN.matcher(softwareVersion)
        if (m.matches()) return m[0][1]
        else return "none"
    }
}
