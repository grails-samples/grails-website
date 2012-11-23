package org.grails.news

import org.grails.content.Content
import org.grails.auth.User
import org.grails.common.ApprovalStatus

/**
* @author Graeme Rocher
* @since 1.0
*
* Created: Feb 27, 2008
*/
class NewsItem extends Content {
    User author
    ApprovalStatus status = ApprovalStatus.PENDING    

    static mapping = {
        cache true
    }

    static searchable = {
        only = ['body', 'title']
        title boost: 2.0
    }
    
    static namedQueries = {
        approved {
            eq "status", ApprovalStatus.APPROVED
        }
        pending {
            or {
                eq 'status', ApprovalStatus.PENDING                
                eq 'status', ApprovalStatus.REJECTED
            }

        }
        all {
            order "dateCreated", "desc"
        }
        allApproved {
            approved()
            all()
        }
        
    }

}
