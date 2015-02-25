package org.grails.wiki

import org.springframework.beans.factory.FactoryBean
import org.radeox.engine.context.BaseInitialRenderContext
import org.grails.cache.CacheService
import org.springframework.beans.factory.InitializingBean


class GrailsWikiEngineFactoryBean implements FactoryBean, InitializingBean {

    BaseInitialRenderContext context = new BaseInitialRenderContext();

    CacheService cacheService
    String contextPath

    private engine

    public Object getObject() { engine }

    Class getObjectType() { GrailsWikiEngine }

    boolean isSingleton() { true }

    public void afterPropertiesSet() {
        if(context.getParameters()==null) {
            context.setParameters([:])
        }
        context.set(GrailsWikiEngine.CONTEXT_PATH, contextPath)
        context.set(GrailsWikiEngine.CACHE, cacheService)
        engine = new GrailsWikiEngine(context)
        context.setRenderEngine engine

    }
}