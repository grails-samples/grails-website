package org.grails.websites

import grails.plugin.like.Popularity
import org.grails.taggable.Taggable
import pl.burningice.plugins.image.ast.DBImageContainer

@DBImageContainer(field = "preview")
class WebSite implements Taggable {
    String title
    String description
    String url
    Popularity popularity = new Popularity()
    Date dateCreated

    static embedded = ["popularity"]

    static constraints = {
        title blank: false
        description blank: false, maxSize: 5000
        url url: true
    }

    static searchable = [only: ["title", "description"]]
}
