package org.grails.common

import grails.test.GrailsUnitTestCase

class HelperTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testCapitalizeWords() {
        assert Helpers.capitalizeWords("FOO BAR") == "Foo Bar"
        assert Helpers.capitalizeWords("foo bar") == "Foo Bar"
        assert Helpers.capitalizeWords("FOO") == "Foo"
        assert Helpers.capitalizeWords("foo") == "Foo"
        assert Helpers.capitalizeWords("") == ""
    }
}