package org.grails.content

import pl.burningice.plugins.image.ast.DBImageContainer

@DBImageContainer(field='image')
class WikiImage {
    String name

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
        cache true
        name index: true
        image fetch: "join"
    }
}
