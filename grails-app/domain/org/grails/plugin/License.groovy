package org.grails.plugin

/**
 * Stores details of software licenses.
 */
class License {
	String name
	String url

	static constraints = {
		name blank: false
		url url: true
	}
}
