package grails.plugin.like

import grails.converters.JSON

class LikeDislikeController {
    def currentUser
    def likeDislikeService
    def searchableService

    def like = {
        if (!params.whatId || !params.whatType) {
            render text: "Missing [whatId] and/or [whatType] parameter", status: 400
        }
        else if (!currentUser.principal) {
            render text: "No current user", status: 400
        }
        else {
            try {
                searchableService?.stopMirroring()
                def tmpl = params.respType ?: "likeDislike"
                def item = likeDislikeService.like(currentUser.principal, params.whatId.toLong(), params.whatType)
                render template: tmpl, model: [
                        item: item,
                        principal: currentUser.principal,
                        jsLibrary: params.js]
            }
            finally {
                searchableService?.startMirroring()
            }
        }
    }

    def unlike = {
        if (!params.whatId || !params.whatType) {
            render text: "Missing [whatId] and/or [whatType] parameter", status: 400
        }
        else if (!currentUser.principal) {
            render text: "No current user", status: 400
        }
        else {
            try {
                searchableService?.stopMirroring()
                def tmpl = params.respType ?: "likeDislike"
                def item = likeDislikeService.unlike(currentUser.principal, params.whatId.toLong(), params.whatType)
                render template: tmpl, model: [ item: item, jsLibrary: params.js]
            }
            finally {
                searchableService?.startMirroring()
            }
        }
    }

    def dislike = {
        if (!params.whatId || !params.whatType) {
            render text: "Missing [whatId] and/or [whatType] parameter", status: 400
        }
        else if (!currentUser.principal) {
            render text: "No current user", status: 400
        }
        else {
            def item = likeDislikeService.dislike(currentUser.principal, params.whatId.toLong(), params.whatType)
            render template: "likeDislike", plugin: "like", model: [
                    item: item,
                    principal: currentUser.principal,
                    jsLibrary: params.js]
        }
    }
}
