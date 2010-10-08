package org.grails.test.pages

import geb.Page

class PluginShowPage extends Page {
    static url = "/plugin"
    
    static at = {
        assert title.startsWith("Grails - Plugin -")
        assert name.size() > 0
        return true
    }

    static content = {
        name { $("#pluginBoxTitle") }
        version { $("div.pluginDetail").find("th", text: "Current Release:").next() }
        authors { $("div.pluginDetail").find("th", text: "Author(s):").next() }
    }
}
