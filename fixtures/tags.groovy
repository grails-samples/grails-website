import org.grails.taggable.*

fixture {
    build {
        caching(Tag, name: "caching")
        ui(Tag, name: "ui")
        security(Tag, name: "security")
        springsource(Tag, name: "springsource")
        
        shiroSec(TagLink, tag: security, tagRef: shiro.id, type: "plugin")
        gwtUi(TagLink, tag: ui, tagRef: gwt.id, type: "plugin")
        gemfireCaching(TagLink, tag: caching, tagRef: gemfire.id, type: "plugin")
        gemfireSs(TagLink, tag: springsource, tagRef: gemfire.id, type: "plugin")
        hibernateSs(TagLink, tag: springsource, tagRef: hibernate.id, type: "plugin")
        tomcatSs(TagLink, tag: springsource, tagRef: tomcat.id, type: "plugin")
    }
}
