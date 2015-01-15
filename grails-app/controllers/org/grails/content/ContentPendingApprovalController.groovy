package org.grails.content

import org.grails.common.*
import org.grails.community.*
import org.grails.learn.screencasts.Screencast
import org.grails.learn.tutorials.Tutorial
import org.grails.news.NewsItem

class ContentPendingApprovalController {
    def searchableService

    def list() {
        def pendingItems = NewsItem.pending.list()
        pendingItems.addAll(Tutorial.pending.list())
        pendingItems.addAll(Screencast.pending.list())
        pendingItems.addAll(WebSite.pending.list())
        pendingItems.addAll(Testimonial.pending.list())
        pendingItems = pendingItems.sort(false) { it.dateCreated }
        def pendingPlugins = org.grails.plugin.PluginPendingApproval.pending.list()
        [pendingItems: pendingItems, pendingPlugins:pendingPlugins]
    }
    
    def approve(Long id, String type) {
        setStatus(id, type, ApprovalStatus.APPROVED)
    }
    
    def reject(Long id, String type) {
        setStatus(id,type, ApprovalStatus.REJECTED)
    }
    
    protected setStatus(Long id, String type, ApprovalStatus status) {
        if (id == null && type == null) {
            flash.message = "Can't find valid content to $status"
            redrect action: "list"
        }
        else {
            def content = getClass().classLoader.loadClass(type).get(id)
            
            if(content != null) {
                saveStatus content, status
                flash.message = "You have ${status.toString().toLowerCase()} ${content.getClass().simpleName.toLowerCase()} '${content.title}'"
                redirect action: "list"
            }
            else {
                flash.message = "Can't find valid content to $status"                            
            }

        }        
    }

    protected saveStatus(item, status) {
        try {
            searchableService.stopMirroring()
            item.status = status
            item.save flush:true
        }
        finally {
            searchableService.startMirroring()
        }
    }
}
