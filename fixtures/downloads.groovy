import org.grails.downloads.*
import org.joda.time.DateTime

fixture {
    download140m1(Download,
            latestRelease: false,
            softwareName: "Grails",
            softwareVersion: "1.4.0.M1",
            betaRelease: true,
            releaseDate: new DateTime(2011, 4, 30, 0, 0),
            files: [
                [ title: "Binary Zip", fileType: DownloadFile.TYPE_BINARY, mirrors: [
                    new Mirror(name: "grails.org", urlString: "http://grails.org/dist/grails-1.4.0.M1.zip"),
                    new Mirror(name: "Amazon S3", urlString: "http://s3.amazon.com/somebucket/grails-1.4.0.M1.zip") ]],
                [ title: "Documentation", fileType: DownloadFile.TYPE_DOCUMENTATION, mirrors: [
                    new Mirror(name: "grails.org", urlString: "http://grails.org/dist/grails-docs-1.4.0.M1.zip") ]]])

    download138(Download,
            latestRelease: false,
            softwareName: "Grails",
            softwareVersion: "1.3.8.BUILD-SNAPSHOT",
            betaRelease: true,
            releaseDate: new DateTime(2011, 2, 13, 0, 0),
            files: [
                [ title: "Binary Zip", fileType: DownloadFile.TYPE_BINARY, mirrors: [
                    new Mirror(name: "grails.org", urlString: "http://grails.org/dist/grails-1.3.8.BUILD-SNAPSHOT.zip"),
                    new Mirror(name: "Amazon S3", urlString: "http://s3.amazon.com/somebucket/grails-1.3.8.BUILD-SNAPSHOT.zip") ]],
                [ title: "Documentation", fileType: DownloadFile.TYPE_DOCUMENTATION, mirrors: [
                    new Mirror(name: "grails.org", urlString: "http://grails.org/dist/grails-docs-1.3.8.BUILD-SNAPSHOT.zip") ]]])

    download137(Download,
            latestRelease: true,
            softwareName: "Grails",
            softwareVersion: "1.3.7",
            releaseDate: new DateTime(2011, 1, 13, 0, 0),
            files: [
                [ title: "Binary Zip", fileType: DownloadFile.TYPE_BINARY, mirrors: [
                    new Mirror(name: "grails.org", urlString: "http://grails.org/dist/grails-1.3.7.zip"),
                    new Mirror(name: "Amazon S3", urlString: "http://s3.amazon.com/somebucket/grails-1.3.7.zip") ]],
                [ title: "Documentation", fileType: DownloadFile.TYPE_DOCUMENTATION, mirrors: [
                    new Mirror(name: "grails.org", urlString: "http://grails.org/dist/grails-docs-1.3.7.zip") ]]])

    download125(Download,
            latestRelease: false,
            softwareName: "Grails",
            softwareVersion: "1.2.5",
            releaseDate: new DateTime(2010, 10, 4, 0, 0),
            files: [
                [ title: "Binary Zip", fileType: DownloadFile.TYPE_BINARY, mirrors: [
                    new Mirror(name: "Codehaus", urlString: "http://dist.codehaus.org/grails/grails-1.2.5.zip") ]],
                [ title: "Documentation", fileType: DownloadFile.TYPE_DOCUMENTATION, mirrors: [
                    new Mirror(name: "Codehaus", urlString: "http://dist.codehaus.org/grails/grails-docs-1.2.5.zip"),
                    new Mirror(name: "Amazon S3", urlString: "http://s3.amazon.com/somebucket/grails-docs-1.2.5.zip") ]]])

    firstVersion(VersionOrder, baseVersion: "1.4 milestone", orderIndex: 1)
    secondVersion(VersionOrder, baseVersion: "1.3", orderIndex: 2)
    thirdVersion(VersionOrder, baseVersion: "1.2", orderIndex: 3)
}
