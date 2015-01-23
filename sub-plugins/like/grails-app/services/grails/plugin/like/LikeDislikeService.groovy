package grails.plugin.like

class LikeDislikeService {
    static transactional = true
    
    def like(String principal, long itemId, String itemType) {
        def itemClass = getClass().classLoader.loadClass(itemType)
        def item = itemClass.get(itemId)
        def currVote = getCurrentVoteForUser(item, principal)

        def state = item.popularity
        if (currVote) {
            if (currVote.vote == 1) {
                // Can't like an item once it's already been liked!
                return
            }
            else if (currVote.vote == -1) {
                state.disliked--
                state.netLiked++
            }
        }
        else {
            currVote = new LikeDislike(whoId: principal, whatId: itemId, whatType: itemType)
        }

        currVote.vote = 1
        state.liked++
        state.netLiked++
        currVote.save(failOnError: true)

        return item
    }

    def unlike(String principal, long itemId, String itemType) {
        def itemClass = getClass().classLoader.loadClass(itemType)
        def item = itemClass.get(itemId)
        def currVote = getCurrentVoteForUser(item, principal)

        // You can only unlike a liked item.
        def state = item.popularity
        if (currVote?.vote == 1) {
            state.liked--
            state.netLiked--
        }
        else if (currVote?.vote == -1) {
            state.disliked--
            state.netLiked++
        }

        if (currVote) {
            currVote.vote = 0
            currVote.save(failOnError: true)
        }

        return item
    }

    def dislike(String principal, long itemId, String itemType) {
        def itemClass = getClass().classLoader.loadClass(itemType)
        def item = itemClass.get(itemId)
        def currVote = getCurrentVoteForUser(item, principal)

        def state = item.popularity
        if (currVote) {
            if (currVote.vote == 1) {
                state.liked--
                state.netLiked--
            }
            else if (currVote.vote == -1) {
                // Can't dislike an item once it's already been disliked!
                return
            }
        }
        else {
            currVote = new LikeDislike(whoId: principal, whatId: itemId, whatType: itemType)
        }

        currVote.vote = -1
        state.disliked++
        state.netLiked--
        currVote.save(failOnError: true)

        return item
    }

    def getCurrentVoteForUser(item, String principal) {
        return LikeDislike.withCriteria(uniqueResult: true) {
            eq "whoId", principal
            eq "whatId", item.id
            eq "whatType", item.getClass().name
        }
    }
}
