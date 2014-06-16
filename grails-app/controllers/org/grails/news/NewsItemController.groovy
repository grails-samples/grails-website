package org.grails.news

import org.grails.auth.User
import org.grails.common.*
import org.grails.wiki.WikiPage

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
            def newsItem = new NewsItem()
            newsItem.properties['title', 'body', 'status'] = params
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
                request.user.addToPermissions("news:edit:$newsItem.id")
                request.user.save()
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

    def legacyShow(String author, String title) {
        def a = User.findByLogin(author)
        def newsItem = NewsItem.where { author == a && title == title }.get()

        if (newsItem) {
            redirect action: "show", id: newsItem.id, permanent: true
        }
        else {
            response.sendError 404
        }
    }
    
    def edit( Long id) {
        if(request.method == 'GET') {
            def newsItem = show(id)
            
            if(newsItem) {
                render view:"edit", model: newsItem
            } 
        }
        else {
            def n = NewsItem.get(id)

            n.properties =  params['title', 'body', 'status'] 
            if(n.save()) {
                redirect action:'show', id:id
            }
            else {
                render view:"edit", model: newsItem
            }

        }
               
    }
}
