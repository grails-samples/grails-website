import org.grails.taggable.Tag
import org.grails.taggable.TagLink
import org.grails.tutorials.Tutorial

fixture {
    build {
        tut1(Tutorial,
                title: "Getting Started with Grails part 1",
                description: """Introductory screencast that takes you from installing Grails \
to building a domain model to creating a CRUD user interface for it. All within 15 minutes.""",
                url: "http://grails.org/screencast/show/22")
        tut2(Tutorial,
                title: "Getting Started with Grails part 2",
                description: "Follow up screencast that fleshes out the view.",
                url: "http://grails.org/screencast/show/23")
        tut3(Tutorial,
                title: "InfoQ: Getting Started with Grails",
                description: "Free book by Jason Randolph and Scott Davis that introduces Grails and covers all the basics and a few extra advanced lessons.",
                url: "http://www.infoq.com/minibooks/grails-getting-started")
    }
}
