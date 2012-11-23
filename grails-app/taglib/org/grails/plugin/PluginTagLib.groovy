package org.grails.plugin
/*
 * TODO: This should be replaced either by a partial template or something like BeanFields. (PAL)
 * author: Matthew Taylor
 */
class PluginTagLib {
    static namespace = 'plugin'

    def input = { attrs, body ->
        out << """
            <tr class="prop">
                <th></th>
                <td class='description'>${attrs?.description}</td>
            </tr>
            <tr class="prop">
                <th valign="top" class="name">
                    <label for="name">${attrs?.name}</label>
                </th>
                <td valign="top" class="value ${hasErrors(bean:attrs?.plugin, field:'name','errors')}">
                    ${body()}
                </td>
            </tr>
        """
    }

    def rating = { attrs, body ->
        // This gives an integer value from 0-10, which represents the
        // number of stars for this plugin to the nearest half star.
        def value = Math.round(attrs.averageRating * 2)
        def max = 5

        out << '<p class="rating">'
        for (i in [2, 4, 6, 8, 10]) {
            // Calculate whether this is an empty star, a half one, or
            // a full one. Using the relative star value compared to the
            // current star makes this calculation fairly trivial.
            def diff = value - i
            def n = diff == -1 ? "50" : (diff < 0 ? "0" : "100")
            out << "<span class=\"star-$n\"></span>"
        }
        out << '<span class="note">' << attrs['total'] ?: 0 << '</span>'
        out << '</p>'
    }
}
