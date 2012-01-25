package org.grails.content

import pl.burningice.plugins.image.ast.DBImageContainer

@DBImageContainer()
class WikiImage {
    String name

    static constraints = {
        name blank: false, unique: true
    }

    static mapping = {
        name index: true
        image fetch: "join"
    }
}
