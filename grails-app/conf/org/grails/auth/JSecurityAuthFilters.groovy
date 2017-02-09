package org.grails.auth

import org.apache.shiro.SecurityUtils

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 26, 2008
*/
class JSecurityAuthFilters {
    def userService
    
    /**
     * Called when an unauthenticated user tries to access a secured
     * page.
     */
    def onNotAuthenticated(subject, d) {
        def targetUri = d.request.forwardURI 
        if (d.request.queryString) {
            targetUri = "${targetUri}?${d.request.queryString}"
        }

        if (d.request.xhr) {
            d.render(template:"/user/loginForm", model:[targetUri:targetUri,
                                                        formData:d.params,
                                                        async:true,
                                                        update:d.params._ul,
                                                        message:"auth.not.logged.in"])
        } else if(d.response.format == 'text') {
            d.render status:401, text:"Permission Denied"
        
        }else {
            // Redirect to login page.
            d.session["targetUri"] = targetUri 
            d.redirect(
                    controller: 'user',
                    action: 'login',
                    params:[targetUri: targetUri])
        }

        // Don't execute the default behaviour.
        return false
    }    

    /**
     * Called when an authenticated user tries to access a page that they don't
     * have the rights for.
     */
    def onUnauthorized(subject, d) {
        if (d.request.xhr) {
            d.render "You do not have permission to access this page."
        } else if (d.response.format == 'text') {
            d.render status: 403, text: "Permission denied"
        } else {
            // Redirect to the 'unauthorized' page.
            d.redirect controller: 'user', action: 'unauthorized'
        }
    }    

    static filters = {

        def requiresPermissions = [
                pluginTab: ["editWikiPage"],
                screencast: ["create", "edit", "save", "update"] as Set,
                likeDislike: ["like", "dislike"] as Set
        ]
        withPermissions(controller: "*", action: "*") {
            before = {
                if (actionName in requiresPermissions[controllerName]) {
                    accessControl()
                }
                else {
                    return true
                }
            }
        }

        newsViewing(controller:'newsItem', action:"create") {
            before = {
                accessControl {
                    role("Editor") || role("Administrator")                    
                }
            }
        }
        newsViewing(controller:'newsItem', action:"edit") {
            before = {
                accessControl {
                    role("Administrator") || permission("news:edit:${params.id}")                  
                }
            }
        }        
        // Ensure that all controllers and actions require an authenticated user,
        
        // Creating, modifying, or deleting a book requires the "Administrator"
        // role.
        wikiEditing(controller: "(content|news)", action: "(editNews|createNews|markupWikiPage|editWikiPage|createWikiPage|saveWikiPage|editPlugin|updatePlugin|createPlugin|addTag|removeTag)") {
            before = {
                accessControl {
                    role("Editor") || role("Administrator")
                }
            }
        }

        wikiEditing(controller: "plugin", action: "(editPlugin|updatePlugin)") {
            before = {
                accessControl {
                    role("Administrator") || permission("plugin:edit:${params.id}")
                }
            }
        }

        // used by wiki pages
        wikiImageShow(controller: "content", action: "showImage") {
            before = {
               return true
            }
        }
        wikiImageUpload(controller: "content", action: "(uploadImage|addImage)") {
            before = {
                accessControl { true }
            }
        }

        jobPosting(controller:"(job|paypal)", action:"(delete|edit|update|editJobs|save|create|buy|success|cancel)") {
            before = {
                accessControl {
                    role("Editor") || role("Administrator")
                }
            }
        }
        wikiManagement(controller:"content", action:"rollbackWikiVersion") {
            before = {
                accessControl {
                    role("Administrator")
                }
            }
        }
        userProfile(controller:"user", action:"profile") {
            before = {
                accessControl {
                    role("Editor") || role("Administrator")
                }
            }
        }
        comments(controller:"commentable", action:"add") {
            before = {
                accessControl { true }
            }
        }
        pluginSubmitting(controller:"plugin", action: "submitPlugin") {
            before = {
                accessControl { true }
            }
        }
        pluginPublishing(controller:"repository", action:"publish") {
            before = {
                accessControl { permission "plugin:publish:${params.plugin}" }
            }
        }
        screencasts(controller:"screencast", action:"(edit|create|save|update)") {
            before = {
                accessControl { true }
            }
        }
        blogPosting(controller:"blog", action:"(createEntry|editEntry)") {
            before = {
                accessControl { true }
            }
        }
        blogDeletion(controller:"blog", action:"delete") {
            before = {
                accessControl {
                    role("Administrator")
                }
            }
        }

        pluginDeletion(controller:"plugin", action:"deletePlugin") {
            before = {
                accessControl {
                    role("Administrator")
                }
            }
        }

        pluginActivities(controller:"(tag|plugin|rateable)", action:"(update|postComment|rate|addTag|removeTag)") {
            before = {
                accessControl {
                    role("Editor") || role("Administrator")
                }
            }
        }

        adminArea(uri:"/admin/**") {
            before = {
                if (controllerName == "error") return true

                accessControl {
                    role("Administrator")
                }
            }
        }

        userInRequest(controller:"*", action:"*") {
            before = {
                if (controllerName == "error") return true

                def subject = SecurityUtils.getSubject()
                if (subject?.principal) {
                    request.user = userService.getUserFromPrincipal(subject.principal)
                }
            }
        }


    }
}
