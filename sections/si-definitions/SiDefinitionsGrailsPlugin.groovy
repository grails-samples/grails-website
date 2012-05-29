import org.grails.plugin.platform.events.registry.SpringIntegrationEventsRegistry

import static org.grails.plugin.platform.events.publisher.EventsPublisherGateway.EVENT_OBJECT_KEY

import org.grails.rabbitmq.AutoQueueMessageListenerContainer
import org.grails.website.amqp.ErrorsHandler
import org.springframework.amqp.support.converter.JsonMessageConverter
import org.springframework.integration.amqp.AmqpHeaders

class SiDefinitionsGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "2.0 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Application SI Definitions" // Headline display name of the plugin
    def author = "Peter Ledbrook"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    def loadAfter = ["*"]

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/si-definitions"
    def doWithSpring = {
        xmlns rabbit: "http://www.springframework.org/schema/rabbit"

        amqpErrorHandler(ErrorsHandler)

        "eventBusQueue"(AutoQueueMessageListenerContainer) {
            acknowledgeMode = "NONE"
            channelTransacted = false
            errorHandler = ref('amqpErrorHandler')
            connectionFactory = ref("rabbitMQConnectionFactory")
            exchangeBeanName = "grails.rabbit.exchange.website.eventbus"
        }

        xmlns si: 'http://www.springframework.org/schema/integration'

        // Debug logging
        si.'logging-channel-adapter' id: "loggingChannel", 'log-full-message': "true", level: "INFO"
        si.'channel-interceptor'(pattern: "grailsPipeline") {
            si.'wire-tap' channel: "loggingChannel"
        }

        // Forward non-GORM messages to a RabbitMQ broker so that they propagate
        // to other application nodes.
        si.channel id: "toRabbit"
        si.chain id: "outboundFilter", 'input-channel': "grailsPipeline", 'output-channel': "toRabbit", {
            // Exclude GORM events.
            si.filter expression: "headers['${EVENT_OBJECT_KEY}'].scope != 'gorm'"

            // Exclude events that have come via RabbitMQ from another node.
            si.filter expression: "!headers.containsKey('nodeId')"

            // Identify the source of this event and convert the payload to JSON.
            si.'header-enricher' {
                si.header(name: "nodeId", ref: "clusterService", method: "getNodeId")
            }
            si.'object-to-json-transformer'()
        }

        xmlns siAmqp: "http://www.springframework.org/schema/integration/amqp"
        siAmqp.'outbound-channel-adapter' channel: "toRabbit",
                'amqp-template': "rabbitTemplate",
                'exchange-name': "website.eventbus", 'mapped-request-headers':"*", 'mapped-reply-headers':"*"

        // Fetch event bus messages from RabbitMQ and feed them into this node's
        // event bus.
        si.channel id: "fromRabbit"
        siAmqp.'inbound-channel-adapter' channel: "fromRabbit",
                'listener-container': "eventBusQueue", 'mapped-request-headers':"*", 'mapped-reply-headers':"*"

        si.chain id: "inboundFilter", 'input-channel': "fromRabbit", 'output-channel': "grailsPipeline", {
            // Filter out messages that came from this node.
            si.filter expression: "headers['nodeId'] != @clusterService.nodeId"

            // From JSON back to a map instance.
            si.'json-to-object-transformer' type: "java.util.Map"
        }
    }
}
