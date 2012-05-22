/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.mailgun

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient

import org.apache.commons.logging.LogFactory
import org.apache.http.HttpHost
import org.apache.http.client.protocol.ClientContext
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.client.BasicAuthCache

import static groovyx.net.http.Method.POST
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC

class MailgunSender {
    private static final LOG = LogFactory.getLog(this)

    def targetUrl
    def username
    def password

    def send(message) {
        def http = new InsecureHttpClient(targetUrl)
        def targetUri = http.uri.toURI()

        http.auth.basic username, password
        http.request(POST, JSON) { req ->
            uri.path = "messages"
            requestContentType = URLENC
            body = message

            // Enable pre-emptive authentication.
            def authCache = new BasicAuthCache()
            authCache.put(new HttpHost(targetUri.host, targetUri.port, targetUri.scheme), new BasicScheme())
            context.setAttribute(ClientContext.AUTH_CACHE, authCache)

            response.success = { resp, reader ->
                LOG.trace "Successfully sent email '${message.subject}'"
            }

            // called only for a 404 (not found) status code:
            response.'404' = { resp ->
                LOG.error "Failed to send email: 404 for URI ${targetUrl}/messages"
            }
        }
                
        return message
    }
}
