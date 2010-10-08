package org.grails.util

import spock.lang.Specification

class DateServiceSpec extends Specification {
    def dateService = new DateService()
    
    def "Test calendar months"() {
        expect:
        dateService.getMonthString(new Date().updated(month: month)) == expected
        
        where:
        month | expected
        0     | "Jan"
        5     | "June"
    }
}
