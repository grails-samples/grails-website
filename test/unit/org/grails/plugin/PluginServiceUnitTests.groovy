package org.grails.plugin

import grails.util.Holders
import org.grails.wiki.WikiPage
import org.grails.content.Version
import org.grails.auth.User
import grails.test.mixin.*

import static org.junit.Assert.*
@TestFor(PluginService)
@Mock([User, Version, WikiPage, Plugin, PluginTab])
class PluginServiceUnitTests {

    void setUp() {
        new User(login:'admin').save(validate: false)

    }

    void testCompareVersions() {
        assertEquals "1.0.3 should be == 1.0.3", 0, service.compareVersions('1.0.3', '1.0.3')
        assertEquals "1.0.3 should be > 1.0.2", 1, service.compareVersions('1.0.3', '1.0.2')
        assertEquals "1.0.3 should be < 1.1", -1, service.compareVersions('1.0.3', '1.1')
        assertEquals "1.3 should be > 1.0.1", 1, service.compareVersions('1.3', '1.0.1')
        assertEquals "1.0.3 should be > 1.1-SNAPSHOT",          1, service.compareVersions('1.0.3', '1.1-SNAPSHOT')
        assertEquals "1.1-SNAPSHOT should be == 1.1-SNAPSHOT",  0, service.compareVersions('1.1-SNAPSHOT', '1.1-SNAPSHOT')
        assertEquals "1.1 should be > 1.0.4", 1, service.compareVersions('1.1', '1.0.4')
        assertEquals "1.1 should be < 1.1.1", -1, service.compareVersions('1.1', '1.1.1')
        assertEquals "1.1 should be > 1.1.1-SNAPSHOT", 1, service.compareVersions('1.1', '1.1.1-SNAPSHOT')
        assertEquals "1.0.4 should be < 1.1", -1, service.compareVersions('1.0.4', '1.1')
        assertEquals "1.1.1 should be > 1.1", 1, service.compareVersions('1.1.1', '1.1')
        assertEquals "1.1.1-SNAPSHOT should be < 1.1", -1, service.compareVersions('1.1.1-SNAPSHOT', '1.1')
        assertEquals "0.1 should be == 0.1", 0, service.compareVersions('0.1', '0.1')
    }
    
   
}
