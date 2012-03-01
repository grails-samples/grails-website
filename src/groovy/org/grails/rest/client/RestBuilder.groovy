package org.grails.rest.client

import org.springframework.web.client.RestTemplate
import org.springframework.http.*
import org.springframework.util.*
import static org.springframework.http.MediaType.*
import grails.converters.*
import grails.web.*
import org.springframework.http.client.*
import org.codehaus.groovy.grails.plugins.codecs.Base64Codec
import groovy.util.slurpersupport.*
import org.codehaus.groovy.grails.web.json.*
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
     * Issues a GET request and returns the response in the most appropriate type
     * @param url The URL
     * @param url The closure customizer used to customize request attributes
     */
    def get(String url, Closure customizer=null) {
        doRequestInternal( url, customizer, HttpMethod.GET)
    }

    /**
     * Issues a PUT request and returns the response in the most appropriate type
     *
     * @param url The URL
     * @param customizer The clouser customizer
     */
    def put(String url, Closure customizer = null) {
        doRequestInternal( url, customizer, HttpMethod.PUT)
    }

    /**
     * Issues a POST request and returns the response
     * @param url The URL
     * @param customizer (optional) The closure customizer
     */
    def post(String url, Closure customizer = null) {
        doRequestInternal( url, customizer, HttpMethod.POST)
    }

    /**
     * Issues DELETE a request and returns the response

     * @param url The URL
     * @param customizer (optional) The closure customizer
     */
    def delete(String url, Closure customizer = null) {
        doRequestInternal( url, customizer, HttpMethod.DELETE)
    }

    protected doRequestInternal(String url, Closure customizer, HttpMethod method) {

        def requestCustomizer = new RequestCustomizer()
        if(customizer != null) {
            customizer.delegate = requestCustomizer
            customizer.call()
        }
        def responseEntity = restTemplate.exchange(url, method,requestCustomizer.createEntity(),String)
        handleResponse(responseEntity)
    }
    protected handleResponse(ResponseEntity responseEntity) {
        return new RestResponse(responseEntity: responseEntity)
    }
}
class RestResponse {
    @Delegate ResponseEntity responseEntity
    @Lazy JSONElement json = {
        def body = responseEntity.body
        if(body) {
            return JSON.parse(body)
        }
    }()
    @Lazy GPathResult xml = {
        def body = responseEntity.body
        if(body) {
            return XML.parse(body)
        }
    }()

    @Lazy String text = {
        def body = responseEntity.body
        if(body) {
            return body.toString()
        }
        else {
            responseEntity.statusCode.reasonPhrase
        }
    }()

    int getStatus() {
        responseEntity?.statusCode?.value() ?: 200
    }

}
class RequestCustomizer {
    HttpHeaders headers = new HttpHeaders()
    def body
    MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>()

    // configures basic author
    RequestCustomizer auth(String username, String password) {
        String authStr = "$username:$password"
        String encoded = Base64Codec.encode(authStr)
        headers["Authorization"] = "Basic $encoded".toString()
        return this
    }

    RequestCustomizer contentType(String contentType) {
        headers.setContentType(MediaType.valueOf(contentType))
        return this
    }

    RequestCustomizer accept(String...contentTypes) {
        def list = contentTypes.collect { MediaType.valueOf(it) }
        headers.setAccept(list)
        return this
    }

    RequestCustomizer header(String name, String value) {
        headers[name] = value
        return this
    }

    RequestCustomizer json(Closure callable) {
        def builder = new JSONBuilder()
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        JSON json = builder.build(callable) 

        body = json.toString()
        return this
    }

    RequestCustomizer xml(Closure closure) {
        def b = new groovy.xml.StreamingMarkupBuilder()
        def markup = b.bind(closure)
        def StringWriter sw = new StringWriter()
        markup.writeTo(sw)
        this.body = sw.toString()
    }

    RequestCustomizer body(content) {
        this.body = content
    }

    HttpEntity createEntity() {
        if(mvm) {
            return new HttpEntity(mvm, headers)
        }
        else {
            return new HttpEntity(body, headers)
        }
    }

    void setProperty(String name, value) {
        mvm[name] = value
    }
}
