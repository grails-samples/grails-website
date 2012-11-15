package org.grails.wiki

class WikiPageAdminController {

    static scaffold = WikiPage
    def index = {
        redirect(action: "list", params: params)
    }

    def search(String q) {
      def max = Math.min(params.max ? params.int('max') : 10, 100)
      def searchResults = WikiPage.search(q, [max: max, offset: params.int('offset', 0)])
      render view:"list", model:[wikiPageInstanceList: searchResults.results, wikiPageInstanceTotal:searchResults.total]        
    }    

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [wikiPageInstanceList: WikiPage.list(params), wikiPageInstanceTotal: WikiPage.count()]
    }

}
