package org.grails

import spock.lang.Specification


/*
 * author: Eric Berry
 */

class UnitSpecHelper extends Specification {

    String word(len) {
        def word = ''
        len.times { word += 'a' }
        return word
    }

}
