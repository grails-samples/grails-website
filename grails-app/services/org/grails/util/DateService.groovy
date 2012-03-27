package org.grails.util

import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import org.springframework.beans.factory.InitializingBean

class DateService implements InitializingBean {
    
    static MONTHS = [0:'Jan',1:'Feb',2:'March',3:'April',4:'May',5:'June',6:'July',7:'Aug',8:'Sept',9:'Oct',10:'Nov',11:'Dec']
    
    def grailsApplication
    def cal = new GregorianCalendar()
    def restDateFormatter

    void afterPropertiesSet() {
        restDateFormatter = ISODateTimeFormat.dateHourMinuteSecond()
    }

    def getRestDateTime(dateTime) {
        return restDateFormatter.print(dateTime.withZone(DateTimeZone.UTC))
    }

    def getMonthString(date) {
        cal.setTime(date)
        def intMonth = cal.get(Calendar.MONTH)
        MONTHS[intMonth]
    }
    
    def getDayOfMonth(date) {
        cal.setTime(date)
        cal.get(Calendar.DAY_OF_MONTH)
    }
    
}
