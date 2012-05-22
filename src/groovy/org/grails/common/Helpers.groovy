package org.grails.common

class Helpers {
    static String capitalizeWords(String sentence) {
        def words = sentence.split(" ")
        def newStr = []
        try {
            words.each { w ->
                def capW = [w[0].toUpperCase()]
                if (w.length() > 1) {
                    capW << w[1..-1].toLowerCase()
                }
                newStr << capW.join()
            }
            return newStr.join(' ')
        } catch(StringIndexOutOfBoundsException ex) {
            return ""
        }
    }
}
