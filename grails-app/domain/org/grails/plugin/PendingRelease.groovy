package org.grails.plugin

/**
 * Stores a plugin release in the database
 */ 
class PendingRelease {

    String name
    String version
	byte[] zip
	byte[] pom
	byte[] xml

	static mapping = {
		zip size:0..10000000 // 10mb
		pom size:0..500000 // 500kb
		xml size:0..500000 // 500kb
	}

}
