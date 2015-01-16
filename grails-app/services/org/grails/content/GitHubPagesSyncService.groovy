package org.grails.content

import java.nio.file.Files
import java.util.concurrent.atomic.AtomicBoolean

import javax.servlet.ServletContext

import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.operation.BranchAddOp
import org.ajoberstar.grgit.operation.ResetOp
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ApplicationContextEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.ContextStoppedEvent
import org.springframework.web.context.ServletContextAware

class GitHubPagesSyncService implements ApplicationListener<ApplicationContextEvent>, ServletContextAware {
    static transactional=false
    
    String originUrl = "https://github.com/grails/grails-static-website"
    String originBranch = "gh-pages"
    ServletContext servletContext
    File rootDir
    boolean loopRunning = false
    AtomicBoolean updateRequested = new AtomicBoolean(false)
    
    synchronized void requestCheckoutPages() {
        updateRequested.set(true)
    }
    
    void checkoutPages() {
        log.info "Checking out $originUrl to $rootDir"
        File gitDir = new File(rootDir, ".git")
        boolean firstCheckout=false
        if(!gitDir.exists()) {
            File rootCloneDir = Files.createTempDirectory("gh-pages-clone").resolve("checkout-dir").toFile()
            Grgit.clone(dir: rootCloneDir, uri: originUrl, checkout: false)
            Files.move(new File(rootCloneDir, ".git").toPath(), gitDir.toPath())
            firstCheckout=true
        }
        Grgit grgit = Grgit.open(rootDir)
        String startPoint = "origin/${originBranch}".toString()
        if(firstCheckout) {
            grgit.branch.add(name: originBranch, startPoint: startPoint, mode: BranchAddOp.Mode.TRACK)
            grgit.reset(commit: startPoint, mode: ResetOp.Mode.HARD)
            grgit.checkout(branch: originBranch)
        } else {
            grgit.pull(rebase: true)
            grgit.reset(commit: startPoint, mode: ResetOp.Mode.HARD)
        }
    }
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext
        def rootPath = servletContext.getRealPath("/")
        rootDir = new File(rootPath)
        
    }
    
    private synchronized void updateLoop() {
        loopRunning = true
        while(loopRunning && !Thread.currentThread().isInterrupted()) {
            if(updateRequested.get()) {
                updateRequested.set(false)
                try {
                    checkoutPages()
                } catch (Exception e) {
                    log.error("Error in checking out pages", e)
                }
            }
            wait(5000L)
        }
    }
    
    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if(event instanceof ContextRefreshedEvent && !loopRunning) {
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
