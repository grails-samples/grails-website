package grails.plugin.mailgun

import groovyx.net.http.HTTPBuilder

import java.security.SecureRandom
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.SingleClientConnManager

/**
 * <p>A custom version of Groovy's HTTPBuilder that ignores invalid SSL certificates.
 * Use with care, since you will not know whether the server you're communicating
 * with is the one you think! It basically installs a TrustManager that trusts
 * everything. It also disables host name verification by default.</p>
 * <p>The original reason for this class was to deal with Mailgun via HTTPS because
 * for some reason its SSL certificate was completely invalid.</p>
 */
class InsecureHttpClient extends HTTPBuilder {
    InsecureHttpClient(baseUrl) {
        super(baseUrl)

        def oldParams = client.params
        def sslContext = SSLContext.getInstance("SSL")

        // Set up a TrustManager that trusts everything.
        sslContext.init(null, [ new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) { }

            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        } ] as TrustManager[], new SecureRandom())

        // Set up a socket factory that doesn't verify hostnames.
        def sf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
        def httpsScheme = new Scheme("https", sf, 443)
        def schemeRegistry = new SchemeRegistry()
        schemeRegistry.register httpsScheme

        def cm = new SingleClientConnManager(oldParams, schemeRegistry);
        this.client = new DefaultHttpClient(cm, oldParams)
    }
}
