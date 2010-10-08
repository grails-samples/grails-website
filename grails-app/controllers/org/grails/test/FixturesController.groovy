package org.grails.test

import org.grails.plugin.Plugin

class FixturesController {
    def fixtureLoader
    
    static allowedMethods = [ load: ['POST'] ]
    
    def load = {
        // Which fixture should I load?
        fixtureLoader.load(params.file)
        render "Fixtures loaded from ${params.file}.groovy"
    }

    def unload = {
        Plugin.list()*.delete()
        render "Fixture data cleared"
    }

    def pluginData = {
        render(contentType: "application/xml") {
            plugin(name: 'shiro', version: '1.1-SNAPSHOT', grailsVersion: '1.1 &gt; *') {
                author "Peter Ledbrook"
                title "Apache Shiro Integration for Grails"
                description "Enables Grails applications to take advantage of the Apache Shiro security layer."
                documentation "http://grails.org/Shiro+Plugin"
                resources {
                    resource "BuildConfig"
                    resource "ShiroFilters"
                    resource "spring.resources"
                    resource "org.apache.shiro.grails.ShiroAnnotationHandlerService"
                    resource "ShiroTagLib"
                }
                dependencies()
                behavior()
            }
        } 
    }

    def pomData = {
        render(contentType: "application/xml") {
            project(xmlns: 'http://maven.apache.org/POM/4.0.0', 'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance', 'xsi:schemaLocation': 'http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd') {
                modelVersion "4.0.0"
                groupId "org.grails.plugins"
                artifactId params.name
                packaging "zip"
                version "1.2"
                name "Apache Shiro Integration for Grails"
                description "Enables Grails applications to take advantage of the Apache Shiro security layer."
                url "http://grails.org/Shiro+Plugin"
                developers {
                    developer {
                        name "Peter Ledbrook"
                        email "dummy@nowhere.net"
                    }
                }
            }
        }
    }
}
