import grails.util.Environment
import grails.plugin.mailgun.MailgunService

import org.apache.shiro.authc.credential.Sha1CredentialsMatcher
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.auth.ShiroUserBean
import org.grails.content.notifications.ContentAlertStack
import org.grails.plugin.platform.events.registry.SpringIntegrationEventsRegistry
import org.grails.rabbitmq.AutoQueueMessageListenerContainer
import org.grails.wiki.GrailsWikiEngineFactoryBean
import org.radeox.engine.context.BaseInitialRenderContext
import org.springframework.cache.ehcache.EhCacheFactoryBean
import org.springframework.integration.amqp.AmqpHeaders

// Place your Spring DSL code here
beans = {
    currentUser(ShiroUserBean)

    if (Environment.current.name == "cloud") {
        mailService(MailgunService) { bean ->
            bean.autowire = true
        }
    }

    // Shiro defaults to SHA-256 for password hashing. We're going to
    // use SHA-1 for now.
    credentialMatcher(Sha1CredentialsMatcher) {
        storedCredentialsHexEncoded = true
    }

    wikiContext(BaseInitialRenderContext)
    wikiEngine(GrailsWikiEngineFactoryBean) {
        cacheService = ref('cacheService')
        def config = ConfigurationHolder.getConfig()
        contextPath = config.grails.serverURL ?: ""
        context = wikiContext
    }

//    if (Environment.current == Environment.PRODUCTION) {
        xmlns rabbit: "http://www.springframework.org/schema/rabbit"
        "eventBusQueue"(AutoQueueMessageListenerContainer) {
            acknowledgeMode = "NONE"
            channelTransacted = false
            connectionFactory = ref("rabbitMQConnectionFactory")
            exchangeBeanName = "grails.rabbit.exchange.website.eventbus"
        }

        xmlns si: 'http://www.springframework.org/schema/integration'

        // Debug logging
        si.'logging-channel-adapter' id: "loggingChannel", 'log-full-message': "true", level: "INFO"
        si.'channel-interceptor'(pattern: "grailsPipeline") {
            si.'wire-tap' channel: "loggingChannel"
        }
        si.'channel-interceptor'(pattern: "grailsGormPipeline") {
            si.'wire-tap' channel: "loggingChannel"
        }

        si.bridge 'input-channel': "grailsGormPipeline", 'output-channel': "nullChannel"

        // Forward non-GORM messages to a RabbitMQ broker so that they propagate
        // to other application nodes.
        si.channel id: "toRabbit"
        si.chain id: "outboundFilter", 'input-channel': "grailsPipeline", 'output-channel': "toRabbit", {
            // Exclude GORM events.
            si.filter expression: "!headers.containsKey('${SpringIntegrationEventsRegistry.GORM_EVENT_KEY}')"

            // Exclude events that have come via RabbitMQ from another node.
            si.filter expression: "!headers.containsKey('nodeId')"

            // Identify the source of this event and convert the payload to JSON.
            si.'header-enricher' {
                si.header name: AmqpHeaders.CONTENT_TYPE, value: "application/json"
                si.header(name: "nodeId", ref: "clusterService", method: "getNodeId")
            }
            si.'object-to-json-transformer'()
        }

        xmlns siAmqp: "http://www.springframework.org/schema/integration/amqp"
        siAmqp.'outbound-channel-adapter' channel: "toRabbit",
                'amqp-template': "rabbitTemplate",
                'exchange-name': "website.eventbus"

        // Fetch event bus messages from RabbitMQ and feed them into this node's
        // event bus.
        si.channel id: "fromRabbit"
        siAmqp.'inbound-channel-adapter' channel: "fromRabbit",
                'listener-container': "eventBusQueue"

        si.chain id: "inboundFilter", 'input-channel': "fromRabbit", 'output-channel': "grailsPipeline", {
            // Filter out messages that came from this node.
            si.filter expression: "headers.get('nodeId') != #{ clusterService.nodeId }"

            // From JSON back to a map instance.
            si.'json-to-object-transformer' type: "java.util.Map"
        }
}
