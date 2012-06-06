package org.grails.wiki

class WikiPageAdminController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [wikiPageInstanceList: WikiPage.list(params), wikiPageInstanceTotal: WikiPage.count()]
    }

}
