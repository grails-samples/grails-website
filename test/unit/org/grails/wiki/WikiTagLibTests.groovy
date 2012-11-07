package org.grails.wiki

import grails.test.mixin.TestFor

@TestFor(WikiTagLib)
class WikiTagLibTests {

    void testShortenTag() {
        assert applyTemplate('<wiki:shorten html="This is a test" length="5" />') == 'This ...'
        assert applyTemplate('<wiki:shorten html="this is a test" length="8" camelCase="true" />') == 'This Is ...'
    }

}
