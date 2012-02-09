package org.grails.websites

import grails.plugin.like.Popularity
import org.grails.taggable.Taggable
import org.joda.time.DateTime
import pl.burningice.plugins.image.ast.DBImageContainer

@DBImageContainer(field = "preview")
class WebSite implements Taggable {
    String title
    String description
    String url
    boolean featured
    Popularity popularity = new Popularity()
    DateTime dateCreated

    static embedded = ["popularity"]

    static constraints = {
        title blank: false
        description blank: false, maxSize: 5000
        url url: true
    }

    static searchable = [only: ["title", "description"]]

    static namedQueries = {
        allQueryNoSort {
        }

        allQuery {
            allQueryNoSort()
            order "title", "asc"
        }

        featuredQueryNoSort {
            eq "featured", true
        }

        featuredQuery {
            featuredQueryNoSort()
            order "dateCreated", "desc"
        }

        newestQueryNoSort {
        }

        newestQuery {
            newestQueryNoSort()
            order "dateCreated", "desc"
        }

        popularQueryNoSort {
        }

        popularQuery {
            popularQueryNoSort()
            order "dateCreated", "desc"
        }
    }
}
