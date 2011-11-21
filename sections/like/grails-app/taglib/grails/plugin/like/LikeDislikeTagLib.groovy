package grails.plugin.like

class LikeDislikeTagLib {
    static namespace = "like"

    def currentUser
    def likeDislikeService

    /**
     * @attr item REQUIRED The object that can be voted up or down. It must have a
     * <tt>popularity</tt> property of type <tt>Popularity</tt>.
     */
    def vote = { attrs, body ->
        def item = attrs.item
        if (!item) throwTagError "Tag [vote] is missing required attribute [item]"

        // Has the user already voted?
        def itemType = attrs.item.getClass().name
        def currVote = likeDislikeService.getCurrentVoteForUser(item, currentUser.principal)

        if (currentUser.principal) {
            if (currVote && currVote.vote > 0) {
                out << "<span class=\"vote like\">+1</span>"
            }
            else {
                out << g.remoteLink(
                        class: "vote like",
                        controller: "likeDislike",
                        action: "like",
                        params: [whatId: item.id, whatType: itemType, js: attrs.js],
                        update: "like-${item.id}") { "+1" }
            }
            out << "/"
            if (currVote && currVote.vote < 0) {
                out << "<span class=\"vote dislike\">-1</span>"
            }
            else {
                out << g.remoteLink(
                        class: "vote dislike",
                        controller: "likeDislike",
                        action: "dislike",
                        params: [whatId: item.id, whatType: itemType, js: attrs.js],
                        update: "like-${item.id}") { "-1" }
            }
        }
    }

    /**
     * @attr item REQUIRED The object that can be voted up. It must have a
     * <tt>popularity</tt> property of type <tt>Popularity</tt>.
     */
    def voteUp = { attrs, body ->
        def item = attrs.item
        if (!item) throwTagError "Tag [vote] is missing required attribute [item]"

        // Has the user already voted?
        def itemType = attrs.item.getClass().name
        def currVote = likeDislikeService.getCurrentVoteForUser(item, currentUser.principal)

        if (currentUser.principal) {
            if (currVote && currVote.vote > 0) {
                out << "<span class=\"vote like\">+1</span>"
            }
            else {
                out << g.remoteLink(
                        class: "vote like",
                        controller: "likeDislike",
                        action: "like",
                        params: [whatId: item.id, whatType: itemType, js: attrs.js],
                        update: "like-${item.id}") { "+1" }
            }
        }
    }
}
