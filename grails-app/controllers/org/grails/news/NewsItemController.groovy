package org.grails.news

import org.grails.wiki.WikiPage
import org.grails.common.*

class NewsItemController {

    def index() { 
        render view:'list', model:[newsItems: NewsItem.approved.list(max:10, sort:'dateCreated', order:'desc')]
    }
    
    def create() {
        if(request.method == "GET") {
            def template =  flash.newsItem ?: new NewsItem( body: WikiPage.findByTitle('Default Create Wiki Template')?.body )
            return [newsItem: template]
        }
        else {
            def newsItem = new NewsItem( params['title', 'body', 'status'] )
            newsItem.author = request.user
            if(!newsItem.validate()) {
                flash.newsItem = newsItem
                redirect action:'create'
            }
            else {
                if(request.user?.roles?.any { it.name == org.grails.auth.Role.ADMINISTRATOR }) {
                    newsItem.status = ApprovalStatus.APPROVED
                }
                else {
                    flash.message = "Your news post has been submitted to the administrators for approval"                    
                }

                newsItem.save(flush:true)
                redirect action:"show", id:newsItem.id
            }

        }
    }
    
    def show(Long id) {
        def newsItem =  NewsItem.get(id)
        if(newsItem != null) {
            if(newsItem.status == ApprovalStatus.APPROVED || (request.user != null && newsItem.author == request.user)) {
                return [newsItem: newsItem]                            
            }
            else {
                render status: 404
            }

        }
        else {
            render status:404
        }
    }
    
    def edit( Long id) {
        def newsItem = show(id)
        
        if(request.author != null && newsItem?.author == request.author) {
            render view:"edit", model: newsItem
        }
        else {
           render status:403 
        }
    }
}
