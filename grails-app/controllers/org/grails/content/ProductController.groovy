package org.grails.content

class ProductController {

    def legacyggts() { redirect action: "ggts", permanent: true }
    def ggts() {}
    def ggtsWelcome() {}
}
