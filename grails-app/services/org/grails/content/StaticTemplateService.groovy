package org.grails.content

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import org.springframework.context.ResourceLoaderAware
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader

class StaticTemplateService implements ResourceLoaderAware {
    static transactional = false
    static final long CHECK_SOURCE_INTERVAL = 15000L
    ResourceLoader resourceLoader
    ConcurrentMap<String, CompiledTemplate> compiledTemplates = new ConcurrentHashMap<String, CompiledTemplate>()
    TemplateParser parser = new TemplateParser()
        
    void render(String templatePath, Map<String, Object> contentParts, Writer writer) {
        CompiledTemplate template = compiledTemplates.get(templatePath) 
        if (template == null || System.currentTimeMillis() - template.lastChecked > CHECK_SOURCE_INTERVAL) {
            if (template != null) {
                template.lastChecked = System.currentTimeMillis()
            }
            Resource resource = resourceLoader.getResource(templatePath)
            File file = resource.getFile()
            if(template == null || template.lastModified != file.lastModified()) {
                template = parser.parse(file.getText('UTF-8'), file.lastModified())
                compiledTemplates.put(templatePath, template)
            }
        }
        if (template == null) {
            throw new RuntimeException("template " + templatePath + " not found")
        }
        template.render(contentParts, writer)
    }
}

/**
 * Parses a static html file input string and adds content holders
 * for "title", "head" (just before </head>), "maincontent" (where the main content belongs, marked with <!--MAINCONTENT--> in the template) 
 * and "bodyend" (just before </body>)
 */
class TemplateParser {
    CompiledTemplate parse(String input, long lastModified=-1L) {
        List<TemplatePart> parts = []
        // before <title> (including it)
        int startpos = 0
        int stoppos = input.indexOf("<title>") + "<title>".length()
        parts.add(new StaticTemplatePart(content: input.substring(startpos, stoppos)))
        // add title from content
        parts.add(new ContentTemplatePart(contentKey: 'title'))
        // after </title> (including it) and before </head>
        startpos = input.indexOf("</title>", stoppos+1)
        stoppos = input.indexOf("</head>")
        parts.add(new StaticTemplatePart(content: input.substring(startpos, stoppos)))
        // add head from content
        parts.add(new ContentTemplatePart(contentKey: 'head'))
        // before <!--MAINCONTENT-->
        startpos = stoppos
        stoppos = input.indexOf("<!--MAINCONTENT-->", startpos)
        parts.add(new StaticTemplatePart(content: input.substring(startpos, stoppos)))
        // add main content
        parts.add(new ContentTemplatePart(contentKey: 'maincontent'))
        // after <!--MAINCONTENT-->, before </body>
        startpos = stoppos + "<!--MAINCONTENT-->".length()
        stoppos = input.indexOf("</body>", startpos)
        parts.add(new StaticTemplatePart(content: input.substring(startpos, stoppos)))
        // add bodyend from content
        parts.add(new ContentTemplatePart(contentKey: 'bodyend'))
        // add </body> and after it
        startpos = stoppos
        parts.add(new StaticTemplatePart(content: input.substring(startpos)))
        new CompiledTemplate(parts: parts, lastModified: lastModified)
    }
}

class CompiledTemplate {
    List<TemplatePart> parts
    long lastModified
    long lastChecked = System.currentTimeMillis()
    
    void render(Map<String, Object> contentParts, Writer writer) {
        for(TemplatePart part : parts) {
            part.render(contentParts, writer)
        }
    }
}

abstract class TemplatePart {
    abstract void render(Map<String, Object> contentParts, Writer writer);
}

class StaticTemplatePart extends TemplatePart {
    String content
    
    @Override
    public void render(Map<String, Object> contentParts, Writer writer) {
        writer.write(content)
    }
}

class ContentTemplatePart extends TemplatePart {
    String contentKey
    
    @Override
    public void render(Map<String, Object> contentParts, Writer writer) {
        Object content = contentParts.get(contentKey)
        if(content != null) {
            writer.write(content)
        }
    }
}