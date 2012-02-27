package org.grails.maven

import grails.plugin.springcache.annotations.Cacheable

/**
 * 
 *
 * @author Graeme Rocher
 */
class RepositoryController {

    /**
     * Redirects Grails SVN-style repository to the Maven repository. Request to the path:
     *
     * /grails-heroku/tags/RELEASE_1_0_SNAPSHOT/grails-heroku-1.0-SNAPSHOT.zip
     *
     * Becomes
     *
     * http://repo.grails.org/grails/plugins/org/grails/plugins/heroku/1.0-SNAPSHOT/heroku-1.0-SNAPSHOT.zip
     *
     */
    def artifact(String fullName, String plugin, String version, String type) {       
        if(plugin && version && type)
            redirect url:"http://repo.grails.org/grails/plugins/org/grails/plugins/$plugin/$version/$plugin-${version}.$type"
        else
            render status:404
    }
    
    
    def list() {
        render "plugin list"
    }
}
