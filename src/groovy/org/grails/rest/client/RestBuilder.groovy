package org.grails.rest.client

import org.springframework.web.client.RestTemplate
import org.springframework.http.*
import org.springframework.util.*
import static org.springframework.http.MediaType.*
import grails.converters.*
import org.springframework.http.client.*

class RestBuilder {

    RestTemplate restTemplate

    RestBuilder() {
        restTemplate = new RestTemplate()
    }

    RestBuilder(Map settings) {
        this()
        restTemplate = new RestTemplate()
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory(settings))
    }

    /**
     * Issues a GET request and returns the response in the most appropriate type. For application/json a grails.converters.JSON is returned, for application/xml a GPathResult 
     * returned. Otherwise the raw string is returned.
     */
    def get(String url) {
        def httpEntity = new HttpEntity(new HttpHeaders())
        def responseEntity = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String)
        handleResponse(responseEntity)
    }

    
    /**
     * Issues a GET request and returns the response in the most appropriate type
     * @param url The URL
     * @param url The closure customizer used to customize request attributes
     */
    def get(String url, Closure customizer) {

        def requestCustomizer = new RequestCustomizer()

        customizer.delegate = requestCustomizer
        customizer.call()
        def responseEntity = restTemplate.exchange(url, HttpMethod.GET,customizer.createEntity(),String)
        handleResponse(responseEntity)

    }

    protected handleResponse(ResponseEntity responseEntity) {
        def contentType = responseEntity.headers.getContentType()

        switch("${contentType.type}/${contentType.subtype}".toString()) {
            case TEXT_PLAIN_VALUE:
                return responseEntity.body
            case APPLICATION_JSON_VALUE:
                return JSON.parse(responseEntity.body)
            case APPLICATION_XML_VALUE:
                return XML.parse(responseEntity.body)
            case APPLICATION_XHTML_XML_VALUE:
                return XML.parse(responseEntity.body)
            case TEXT_XML_VALUE:
                return XML.parse(responseEntity.body)
            default:
                return responseEntity.body

        }
    }
}
class RequestCustomizer {
    @Delegate HttpHeaders headers = new HttpHeaders()
    def body

    RequestCustomizer contentType(String contentType) {
        headers.setContentType(MediaType.valueOf(contentType))
    }

    RequestCustomizer accept(String...contentTypes) {
        def list = contentTypes.collect { MediaType.valueOf(it) }
        headers.setAccept(list)
    }

    RequestCustomizer header(String name, String value) {
        headers[name] = value
    }

    HttpEntity createEntity() {
        return new HttpEntity(body, headers)
    }
}
