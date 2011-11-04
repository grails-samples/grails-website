package grails.plugin.like

class LikeDislike {
    String whoId
    Long whatId
    String whatType
    int vote

    static constraints = {
        whoId blank: false
        whatType blank: false
        vote range: -1..1
    }
}
