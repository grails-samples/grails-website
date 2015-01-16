package org.grails.content

public class GitHubPagesSyncServiceSpec extends spock.lang.Specification {

    def "should checkout GH pages"() {
        given:
        GitHubPagesSyncService ghSync = new GitHubPagesSyncService()
        ghSync.rootDir = File.createTempDir()
        when:
        ghSync.checkoutPages()
        then:
        new File(ghSync.rootDir, ".git").exists()==true
        new File(ghSync.rootDir, "index.html").exists()==true
    }
}
