package org.grails.sections

import grails.util.GrailsNameUtils

abstract class AbstractSectionController {
    private Class _domainClass
    private String _propName

    protected AbstractSectionController(Class domainClass) {
        _domainClass = domainClass
        _propName = GrailsNameUtils.getPropertyName(_domainClass.simpleName)
    }

    def datastoreImageService
    def grailsApplication
    def taggableService

    def list = {
        [ artifacts: domainClass.list(offset: params.offset?: 0,max:10, cache:true, sort:"dateCreated", order:"desc"),
          total: domainClass.count() ]
    }

    def feed = {
        def items = domainClass.list( offset: params.offset?: 0,max:10, cache:true, sort:"dateCreated", order:"desc" )
        def feedOutput = {
            title = g.message(code:"${propertyName}.rss.feed.title", 'default':'${propertyName.capitalize()} List')
            link = g.createLink(absolute:true, action:'feed', params:[format:request.format])
            description = g.message(code:"${propertyName}.rss.feed.description", 'default':'Latest ${propName.capitalize()}s')
            
            for(s in items) {
                entry(s.title) {
                    link = g.createLink(absolute:true, controller:propertyName, action:"show", id:s.id)
                    s.description
                }
            }
        }

        withFormat {
            rss {
                render(feedType:"rss", feedOutput)
            }
            atom {
                render(feedType:"atom", feedOutput)
            }
            html {
                render(feedType:"rss", feedOutput)
            }
        }
    }

    def search = {

        def result 
        if(params.tag) {
            def tagged = domainClass.findAllByTag(params.tag)
            result = [artifacts:tagged, total:tagged.size()]
        }
        else {
            try {
                def searchResult = domainClass.search(params.q)
                def found = searchResult.results.unique { it.id }
                def results = []
                if (found) {
                    results = domainClass.withCriteria {
                        inList 'id', found.collect { it.id }
                        cache true
                    }
                }
                result = [artifacts:results, total:results.size()]
            }
            catch(Exception) {
                result = [total:0]
            }
        }
        render view:"list", model: result
    }

    /**
     * Displays a cloud of all the tags attached to the artifacts.
     */
    def browseTags = {
        // Get hold of all the tags for this artifact. The service method returns
        // a map of tag names to counts, i.e. how many artifacts have been tagged
        // with each tag.
        def allTags = taggableService.getTagCounts(propertyName).sort()
        [tags: allTags]
    }

    protected getDomainClass() { return _domainClass }
    protected getPropertyName() { return _propName }
}
