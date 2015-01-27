package org.grails.content

import spock.lang.Specification


public class StaticTemplateServiceSpec extends Specification {

    def "should parse static template"() {
        given:
        def staticTemplate='''<html><head><!-- some pre title --><title>Title goes here</title>After title in head
</head><body>Pre maincontent<!--MAINCONTENT-->post maincontent</body></html>'''
        CompiledTemplate template = new TemplateParser().parse(staticTemplate)
        StringWriter writer=new StringWriter()
        when:
        template.render([title: 'This is the title', head:'This is head', maincontent:'This is maincontent.', bodyend:'This is body end.'], writer)
        then:
        writer.toString()=='''<html><head><!-- some pre title --><title>This is the title</title>After title in head
This is head</head><body>Pre maincontentThis is maincontent.post maincontentThis is body end.</body></html>'''
    }

}
