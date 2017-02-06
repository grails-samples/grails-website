package org.grails.content

import grails.plugins.rest.client.RestBuilder

import java.nio.file.Files
import java.util.concurrent.atomic.AtomicBoolean

import javax.servlet.ServletContext

import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.operation.BranchAddOp
import org.ajoberstar.grgit.operation.ResetOp
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ApplicationContextEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.ContextStoppedEvent

class GitHubPagesSyncService implements ApplicationListener<ApplicationContextEvent> {
    static transactional=false

    
    @Value('${githubApiReadOnlyToken:}')
    String githubApiReadOnlyToken
    
    String baseUrl = "https://github.com/"
    String repoSlug = "grails/grails-static-website"
    String originBranch = "gh-pages"
    String apiBaseUrl = "https://api.github.com/"
    
    ServletContext servletContext
    File rootDir
    boolean loopRunning = false
    AtomicBoolean updateRequested = new AtomicBoolean(false)
    long pollIntervalMillis = 25000L
    long updateLoopSleepMillis = 5000L
    
    synchronized void requestCheckoutPages() {
        updateRequested.set(true)
    }
    
    void checkoutPages() {
        String originUrl = baseUrl + repoSlug
        log.info "Checking out $originUrl to $rootDir"
        if(!rootDir.exists()) {
            rootDir.mkdirs()
        }
        File gitDir = new File(rootDir, ".git")
        boolean firstCheckout=false
        if(!gitDir.exists()) {
            File rootCloneDir = Files.createTempDirectory("gh-pages-clone").resolve("checkout-dir").toFile()
            Grgit.clone(dir: rootCloneDir, uri: originUrl, checkout: false).close()
            Files.move(new File(rootCloneDir, ".git").toPath(), gitDir.toPath())
            firstCheckout=true
        }
        Grgit grgit = openGitRepo()
        String startPoint = "origin/${originBranch}".toString()
        if(firstCheckout) {
            grgit.branch.add(name: originBranch, startPoint: startPoint, mode: BranchAddOp.Mode.TRACK)
            grgit.reset(commit: startPoint, mode: ResetOp.Mode.HARD)
            grgit.checkout(branch: originBranch)
        } else {
            if(isNewerOnGitHub(grgit)) {
                pullAndReset(grgit)
            }
        }
        grgit.close()
    }

    protected void pullAndReset(Grgit grgit) {
        grgit.pull(rebase: true)
        String startPoint = "origin/${originBranch}".toString()
        log.info("Pulling from Github and resetting to $startPoint")
        grgit.reset(commit: startPoint, mode: ResetOp.Mode.HARD)
    }
    
    private Grgit openGitRepo() {
        Grgit.open(rootDir)
    }
    
    boolean isNewerOnGitHub(Grgit grgit) {
        try {
            return (grgit.head().getId() != readRefFromGithub())
        } catch (Exception e) {
            log.warn("Unable to check most recent commit from GH", e)
            return false
        }
    }
    
    String readRefFromGithub() {
        String apiUrl = "${apiBaseUrl}repos/${repoSlug}/git/refs/heads/${originBranch}"
        /*
{
  "ref": "refs/heads/gh-pages",
  "url": "https://api.github.com/repos/grails/grails-static-website/git/refs/heads/gh-pages",
  "object": {
    "sha": "8eb578c6c247522fc53558e9199371812a589569",
    "type": "commit",
    "url": "https://api.github.com/repos/grails/grails-static-website/git/commits/8eb578c6c247522fc53558e9199371812a589569"
  }
}
         */
        RestBuilder rest = new RestBuilder()
        def resp = rest.get(apiUrl) {
            if(githubApiReadOnlyToken) {
                header 'Authorization', "token $githubApiReadOnlyToken"
            }
        }
        resp.json.object.sha
    }
    
    private synchronized void updateLoop() {
        loopRunning = true
        long lastPollMillis = 0
        while(loopRunning && !Thread.currentThread().isInterrupted()) {
            if(updateRequested.get() || (pollIntervalMillis > 0 && System.currentTimeMillis() - lastPollMillis > pollIntervalMillis)) {
                updateRequested.set(false)
                try {
                    checkoutPages()
                } catch (Exception e) {
                    log.error("Error in checking out pages", e)
                }
                lastPollMillis = System.currentTimeMillis()
            }
            wait(updateLoopSleepMillis)
        }
    }
    
    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if(event instanceof ContextRefreshedEvent && !loopRunning) {
            if(rootDir==null) {
                rootDir=new File(System.getProperty("user.home"), "app/assets")
            }
            requestCheckoutPages()
            Thread.startDaemon("GitHubPagesSyncService-Thread") {
                updateLoop()
            }
        }
        if(event instanceof ContextStoppedEvent) {
            synchronized(this) {
                loopRunning=false
                notifyAll()
            }
        }
    }
}
