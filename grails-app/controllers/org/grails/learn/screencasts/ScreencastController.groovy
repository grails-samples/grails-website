package org.grails.learn.screencasts

import org.grails.common.ApprovalStatus

class ScreencastController {
    def searchableService
    def cacheService
    def contentSearchService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def feed() {
        boolean renderFeed = true
        plugin.isNotAvailable(name:"feeds") {
            renderFeed = false
        }
        
        if(renderFeed) {
            def screencasts = Screencast.list( offset: params.offset?: 0,max:10, cache:true, sort:"dateCreated", order:"desc" )
            def feedOutput = {
                title = g.message(code:"screencasts.rss.feed.title", 'default':'Screencasts List')
                link = g.createLink(absolute:true, controller:"screencast", action:'feed', params:[format:request.format])
                description = g.message(code:"screencasts.rss.feed.description", 'default':'Latest Screencasts')
                
                for(s in screencasts) {
                    entry(s.title) {
                        link = g.createLink(absolute:true, controller:"screencast", action:"show", id:s.id)
                        s.description
                    }
                }
            }

            withFormat {
                rss {
                    render(feedType:"rss", feedOutput)          
                }
                atom {
                    render(feedType:"atom", feedOutput)                 
                }
                html {
                    render(feedType:"rss", feedOutput)                  
                }
            }
                
        }
        else {
            response.sendError 404
        }
    }    

    def tagged(String tag) {
        def maxResults = params.int("max",10)
        def offset = params.int("offset", 0)        
        render view:"list", model:[screencastInstanceList: Screencast.findAllByTag(tag, [max: maxResults, offset: offset]), screencastTotal: Screencast.countByTag(tag)]
    }
    def search(String q, int offset) {
        render view:"list", model:[screencastInstanceList: contentSearchService.search(Screencast, q, offset)]
    }
    def edit(Long id) {
        def s = Screencast.get(id)
        if(s) {
            render view:"create", model:[screencastInstance: s]
        }
        else {
            render status:404
        }
    }

    def list() {
        def maxResults = params.int("max",10)
        def offset = params.int("offset", 0)
        def screencastInstanceList = Screencast.allQuery.list(max: maxResults, offset: offset)
        [screencastInstanceList: screencastInstanceList, screencastTotal: Screencast.allQuery.count()]
    }

    def show() {
        def screencastInstance = Screencast.approved.get(params.id)
        if (!screencastInstance) {
            redirect(action: "list")
        }
        else {
            [screencastInstance: screencastInstance]
        }
    }

    def create() {
        def screencastInstance = new Screencast()
        bindData(screencastInstance, params)
        return [screencastInstance: screencastInstance]
    }

    def save() {
        def screencast= params.id ? Screencast.get(params.id) : new Screencast()
        if(screencast == null) screencast = new Screencast()
        bindData(screencast, params)
        screencast.status = ApprovalStatus.PENDING
        screencast.submittedBy = request.user

        try {
            searchableService.stopMirroring()
            if (!screencast.hasErrors() && screencast.save()) {
                processTags screencast, params.tags
                def key = "screencast_${screencast.id}".toString()
                cacheService?.removeWikiText(key)
                cacheService?.removeShortenedWikiText(key)
  
                screencast.save flush: true
                flash.message = "Your submission was successful. We will let you know when it is approved."
                searchableService.index(screencast)
                
                redirect(action: "list")
            }
            else {
                render(view: "create", model: [screencastInstance: screencast])
            }
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
        finally {
            searchableService.startMirroring()
        }
    }

    protected processTags(domainInstance, tagString) {
        def tags = tagString.split(/[,;]/)*.trim()
        domainInstance.tags = tags
    }
}
