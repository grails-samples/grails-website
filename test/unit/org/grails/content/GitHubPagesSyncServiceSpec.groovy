package org.grails.content

import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.operation.ResetOp
import spock.lang.Ignore
import spock.lang.IgnoreIf

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

    @IgnoreIf({ !System.getProperty('GITHUB_API_TOKEN') })
    def "should get most recent sha from GH"() {
        given:
        GitHubPagesSyncService ghSync = new GitHubPagesSyncService()
        ghSync.githubApiReadOnlyToken = System.getProperty('GITHUB_API_TOKEN')

        when:
        def sha = ghSync.readRefFromGithub()
        then:
        sha
        sha.length() == 40
    }

    @IgnoreIf({ !System.getProperty('GITHUB_API_TOKEN') })
    def "should check most recent commit with GH API before fetching with git"() {
        given:
        int counter = 0
        GitHubPagesSyncService ghSync = new GitHubPagesSyncService() {
            protected void pullAndReset(Grgit grgit) {
                counter++
                super.pullAndReset(grgit)
            }
        }
        ghSync.githubApiReadOnlyToken = System.getProperty('GITHUB_API_TOKEN')
        ghSync.rootDir = File.createTempDir()
        ghSync.checkoutPages()

        when:
        ghSync.checkoutPages()

        then:
        counter==0
    }

    @IgnoreIf({ !System.getProperty('GITHUB_API_TOKEN') })
    def "should pull if there is more recent version available"() {
        given:
        int counter = 0
        GitHubPagesSyncService ghSync = new GitHubPagesSyncService() {
            protected void pullAndReset(Grgit grgit) {
                counter++
                super.pullAndReset(grgit)
            }
        }
        ghSync.githubApiReadOnlyToken = System.getProperty('GITHUB_API_TOKEN')

        ghSync.rootDir = File.createTempDir()
        ghSync.checkoutPages()
        Grgit grgit = ghSync.openGitRepo()
        grgit.reset(commit: grgit.resolveCommit("HEAD~1").getId(), mode: ResetOp.Mode.HARD) 
        grgit.close()

        when:
        ghSync.checkoutPages()

        then:
        counter==1
    }

}
