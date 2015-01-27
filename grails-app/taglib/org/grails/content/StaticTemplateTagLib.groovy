package org.grails.content

class StaticTemplateTagLib {
    StaticTemplateService staticTemplateService
    static namespace = 'stemplate'
    
    static encodeAsForTags = [render: 'raw']
    
    Closure render = { attrs, body ->
        staticTemplateService.render(attrs.template, attrs.parts, out)
    }
}
