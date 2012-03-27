package org.grails.website

import java.text.BreakIterator

class TextTagLib {
    static namespace = "text"

    /**
     * Summarises a block of text to a fixed length, using an ellipsis in place
     * of any text that is removed.
     * @attr length Specifies the maximum number of characters in the resulting
     * summarised text (default: 100).
     * @attr encodeAs The codec to use for encoding the summarised text (default: none).
     * Should match the corresponding <tt>encodeAs*()</tt> method, e.g. 'HTML'
     * for <tt>encodeAsHTML()</tt>.
     * @attr ellipsis The string to use for the cut text (default: '...').  
     */
    def summarize = { attrs, body ->
        int maxLen = (attrs["length"] ?: 100).toInteger()
        def codec = attrs.encodeAs
        def ellipsis = attrs.ellipsis ?: '...'
        def s = internalSummarize(body().toString(), maxLen, ellipsis)
        out << (codec ? s."encodeAs$codec"() : s )
    }

    def lineBreak = { attrs, body ->
        out << body().toString().trim().replaceAll(/\n+/, '<br />')
    }
    
    /**
     * Remove markup from HTML but leave escaped entities, so result can
     * be output with encodeAsHTML() or not as the case may be
     */
    def htmlToText = { attrs, body ->
        out << internalHtmlToText(body())
    }
    
    private internalSummarize(String s, maxLen, ellipsis) { 
        if (!s || (s.size() <= maxLen)) {
            return s;
        }

        // Make sure output always big enough for ellipsis
        if (maxLen < ellipsis.size() + 1) {
            throw new IllegalArgumentException(
                    "Cannot summarize to maxLen ${maxLen}, maxLen must be at least 1 longer than ellipsis length")
        }
        
        maxLen -= ellipsis.size()
        def bi = BreakIterator.getWordInstance()
        bi.setText(s)
        int breakpoint = bi.preceding(maxLen)
        def result = new StringBuilder()
        if (breakpoint > 0) {
            result << s[0..breakpoint-1]
        } else {
            result << s[0..maxLen-1]
        }
        result << ellipsis
        return result
    }

    /**
    * Remove markup from HTML but leave escaped entities, so result can
    * be output with encodeAsHTML() or not as the case may be
    */
    private internalHtmlToText(s) {
        if (s) {
            s.replaceAll(/\<.*?>/, '').decodeHTML()
        } else return s;
    }
}
