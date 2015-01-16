package org.grails.content

class GithubPagesWebhookController {
    GitHubPagesSyncService gitHubPagesSyncService
    
    def publishEvent() {
        def payload = request.JSON
        if(payload?.repository?.html_url == gitHubPagesSyncServiceSpec.originUrl) {
            if(payload?.ref == 'refs/heads/gh-pages') {
                gitHubPagesSyncService.requestCheckoutPages()
            }
        } else {
            log.error "Unknown repository url ${payload?.repository?.html_url}"
        }
    }
}
