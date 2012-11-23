package org.grails.plugin

import grails.plugin.spock.IntegrationSpec
import grails.test.mixin.*
import spock.lang.Ignore
import spock.lang.Specification
import org.grails.auth.*
import org.grails.content.*

@TestFor(PluginService)
@Mock([Plugin, PluginRelease, PluginTab, User, Version])
class PluginServiceSpec extends Specification {
    
    @Ignore // no longer used functionality, disabled but keeping test suite for additional methods
    void "Test populate plugin data from masters for new data"() {
   	setup:
		service.metaClass.readPluginList = {->
			def url = PluginServiceSpec.getResource("test-plugin-list.xml")
			url.withInputStream { is ->
				new XmlSlurper().parse(is)
			}
		}     

	when:"The plugin list is read"
		def pluginList = service.readPluginList()

	then:"The test data is returned"
		pluginList.plugin.size() == 3

	when:"The translate master list job is run"
		service.runMasterUpdate()
		def allPlugins = Plugin.list()

	then:"The data is populated correctly"
		allPlugins.size() == 3
		allPlugins[0].name == 'acegi'
		allPlugins[0].releases.size() == 10
		allPlugins[1].name == 'activemq'
		allPlugins[2].name == 'activiti'
    }
}
