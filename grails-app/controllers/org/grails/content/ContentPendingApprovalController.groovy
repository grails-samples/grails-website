package org.grails.content

import org.grails.news.*
import org.grails.common.*

class ContentPendingApprovalController {

    def list() {
        def newsItems = org.grails.news.NewsItem.pending.list()
        [newsItems: newsItems]
    }
    
    def approve(Long id, String type) {
        setStatus(id, type, ApprovalStatus.APPROVED)
    }
    
    def reject(Long id, String type) {
        setStatus(id,type, ApprovalStatus.REJECTED)
    }
    
    private setStatus(Long id, String type, ApprovalStatus status) {
        if(id == null && type == null) {
            flash.message = "Can't find valid content to $status"
            redrect action:'list'
        }
        else {
            def content = getClass().classLoader.loadClass(type).get(id)
            
            if(content != null) {
                content.status = status
                content.save flush:true
                flash.message = "Content $status"
                redirect action: "list"
            }
            else {
                flash.message = "Can't find valid content to $status"                            
            }

        }        
    }
}
