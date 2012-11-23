package org.grails.wiki

import spock.lang.*

class HtmlShortenerSpec extends Specification {

    @Unroll("Shortening #text")
    def "Text that does not require shortening"() {
        given: "An HTML text shortener"
        def service = new HtmlShortener()

        when: "I shorten text that does not exceed a max char limit of 15"
        def result = service.shorten(text, 15)

        then: "The shortened text is the same as the input"
        result == text

        where:
        text << [
            "",
            "Hello world",
            "one two three--",
            "<b>Hello</b>",
            "<a href=\"#myanchor\">one two three--</a>",
            "<p>one <em>two</em> three--</p>" ]
    }

    def "Plain text that requires shortening"() {
        given: "An HTML text shortener and some sample plain text"
        def service = new HtmlShortener()
        def text = "Lorem ipsum do what I say, not as I do"

        when: "I shorten that text to a max char limit of 15"
        def result = service.shorten(text, 15)

        then: "The text is shortened to 15 chars + 3 dots"
        result == "Lorem ipsum do ..."
    }

    def "Simple HTML text that requires shortening"() {
        given: "An HTML text shortener and some sample HTML text"
        def service = new HtmlShortener()
        def text = "Lorem <strong>ipsum</strong> do what I say, not as I do"

        when: "I shorten that text to a max char limit of 15"
        def result = service.shorten(text, 15)

        then: "The text is shortened to 15 chars + 3 dots"
        result == "Lorem <strong>ipsum</strong> do ..."

    }

    def "Simple HTML text that requires shortening at end of element content"() {
        given: "An HTML text shortener and some sample HTML text"
        def service = new HtmlShortener()
        def text = "Lorem <strong>ipsum two</strong> do what I say, not as I do"

        when: "I shorten that text to a max char limit of 15"
        def result = service.shorten(text, 15)

        then: "The text is shortened to 15 chars + 3 dots"
        result == "Lorem <strong>ipsum two</strong>..."

    }

    def "HTML text where required break occurs inside nested element"() {
        given: "An HTML text shortener and some sample HTML text"
        def service = new HtmlShortener()
        def text = """\
Lorem:
<ul>
  <li>One</li>
  <li>- <strong>Three Four</strong> Five</li>
  <li>Six</li>
</ul>
"""
        def expected = """\
Lorem:
<ul>
  <li>One</li>
  <li>- <strong>Three ...</strong></li></ul>"""

        when: "I shorten that text to a max char limit of 20"
        def result = service.shorten(text, 20)

        then: "The text is shortened to 20 chars + 3 dots"
        result == expected
    }
}
