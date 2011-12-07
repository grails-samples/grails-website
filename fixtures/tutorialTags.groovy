import org.grails.taggable.*

fixture {
    build {
        introduction(Tag, name: "introduction")
        gswg(Tag, name: "gswg")
        book(Tag, name: "book")
        screencast(Tag, name: "screencast")
        
        tut1i(TagLink, tag: introduction, tagRef: tut1.id, type: "tutorial")
        tut1s(TagLink, tag: screencast, tagRef: tut1.id, type: "tutorial")
        tut1g(TagLink, tag: gswg, tagRef: tut1.id, type: "tutorial")

        tut2i(TagLink, tag: introduction, tagRef: tut2.id, type: "tutorial")
        tut2g(TagLink, tag: gswg, tagRef: tut2.id, type: "tutorial")
        tut2s(TagLink, tag: screencast, tagRef: tut2.id, type: "tutorial")

        tut3b(TagLink, tag: book, tagRef: tut3.id, type: "tutorial")
        tut3i(TagLink, tag: introduction, tagRef: tut3.id, type: "tutorial")
    }
}
