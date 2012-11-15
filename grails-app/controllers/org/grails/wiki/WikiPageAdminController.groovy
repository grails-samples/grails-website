package org.grails.wiki

class WikiPageAdminController {

    static scaffold = WikiPage
    def index = {
        redirect(action: "list", params: params)
    }

    def search(String q) {
      def max = Math.min(params.max ? params.int('max') : 10, 100)
      def query = WikiPage.where {
        title =~ "%$q%"
      }
      def searchResults = query.max(max).offset(params.int('offset',0)).list()
      def total = query.count()
      render view:"list", model:[wikiPageInstanceList: searchResults, wikiPageInstanceTotal:total]        
    }    

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [wikiPageInstanceList: WikiPage.list(params), wikiPageInstanceTotal: WikiPage.count()]
    }

}
