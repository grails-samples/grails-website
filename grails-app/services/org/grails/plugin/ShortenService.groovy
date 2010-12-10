package org.grails.plugin

/**
 * Service for shortening URLs.
 */
class ShortenService {
    static transactional = false

	/**
	 * Currently sends a request to TinyURL to shorten the given URL.
	 * @param url The string URL to shorten.
	 * @return The short URL provided by the TinyURL service as a string.
	 */
    def shortenUrl(url) {
        return new URL("http://tinyurl.com/api-create.php?url=${url.encodeAsURL()}").text
    }
}
