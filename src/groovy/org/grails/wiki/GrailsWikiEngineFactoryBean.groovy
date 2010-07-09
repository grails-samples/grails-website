package org.grails.wiki

import org.springframework.beans.factory.FactoryBean
import org.radeox.engine.context.BaseInitialRenderContext
import org.grails.cache.CacheService
import org.springframework.beans.factory.InitializingBean


class GrailsWikiEngineFactoryBean implements FactoryBean {

    def context
    String contextPath
    CacheService cacheService

    public Object getObject() {
        context.set(GrailsWikiEngine.CONTEXT_PATH, contextPath)
        context.set(GrailsWikiEngine.CACHE, cacheService)

        def engine = new GrailsWikiEngine(context)
        context.setRenderEngine engine
        return engine
    }

    Class getObjectType() { GrailsWikiEngine }

    boolean isSingleton() { false }
}
