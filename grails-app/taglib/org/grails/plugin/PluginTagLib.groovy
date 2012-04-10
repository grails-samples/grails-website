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
        //def note = attrs.averageRating
        def max = 5

        out << '<p class="rating">'
        out << '<span class="star-100"></span>'
        out << '<span class="star-100"></span>'
        out << '<span class="star-100"></span>'
        out << '<span class="star-50"></span>'
        out << '<span class="star-0"></span>'
        out << '<span class="note">' << attrs['total'] ?: 0 << '</span>'
        out << '</p>'
    }
}
