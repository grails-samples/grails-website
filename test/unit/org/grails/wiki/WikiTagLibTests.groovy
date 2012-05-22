package org.grails.wiki

import grails.test.mixin.TestFor

@TestFor(WikiTagLib)
class WikiTagLibTests {

    void testShortenTag() {
        assert applyTemplate('<wiki:shorten text="This is a test" length="5" />') == 'This i...'
        assert applyTemplate('<wiki:shorten text="this is a test" length="8" camelCase="true" />') == 'This Is A...'
    }

}
