package org.grails.websites

import grails.plugin.like.Popularity
import org.grails.taggable.Tag
import org.grails.taggable.Taggable
import org.grails.taggable.TagLink
import org.joda.time.DateTime
import pl.burningice.plugins.image.ast.DBImageContainer

@DBImageContainer(field = "preview")
class WebSiteOld implements Taggable {
    String title
    String description
    String url
    boolean featured
    Popularity popularity = new Popularity()
    DateTime dateCreated

    def taggableService

    static embedded = ["popularity"]

    static constraints = {
        title blank: false, maxSize: 50
        description blank: false, maxSize: 5000
        url blank: false
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
            gt "popularity.netLiked", 0
        }

        popularQuery {
            popularQueryNoSort()
            order "popularity.netLiked", "desc"
        }
    }

    Collection<Tag> getTags() {
        if (!id) {
            return []
        }
        else {
            return TagLink.findAllByTagRefAndTypeInList(
                    id,
                    taggableService.domainClassFamilies[this.class.name],
                    [cache:true]).tag
        }
    }
}
