package org.grails

import grails.plugin.spock.UnitSpec

/*
 * author: Eric Berry
 */

class UnitSpecHelper extends UnitSpec {

    String word(len) {
        def word = ''
        len.times { word += 'a' }
        return word
    }

}
