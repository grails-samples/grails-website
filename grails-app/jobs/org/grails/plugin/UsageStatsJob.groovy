package org.grails.plugin

import grails.converters.JSON

/**
 * Once a day job that pulls the plugin usage stats report and updates
 * all the plugins with the values from that report. This gives an up
 * to date view on how well used each plugin is.
 */
class UsageStatsJob {
    static final String REPORT_URL = "https://uaa-reports.springsource.org/pub/grails-feature-usage-ranking-by-installation"
    static final int LOWER_THRESHOLD = 30
    static final int UPPER_THRESHOLD = 90

    static triggers = {
        // Daily at 4am.
        cron name: 'onceDaily', cronExpression: "0 0 4 * * ?"
    }
    
    def searchableService

    def execute() {
        // Start by retrieving the report and parsing the JSON.
        new URL(REPORT_URL).withInputStream { input ->
            try {
                def data = JSON.parse(input, "UTF-8")
                
                // Only update usage statistics if Tomcat or Hibernate
                // reach a threshold.
                def baseValue = Math.max(data.tomcat, data.hibernate)
                if (baseValue > LOWER_THRESHOLD) {
                    updatePlugins data, baseValue < UPPER_THRESHOLD ? baseValue : 100
                }
            }
            catch (Exception e) {
                log.error "Failed to read usage stats report: ${e.message} (${e.class})"
                log.debug "Full report", e
            }
        }
    }

    protected updatePlugins(data, baseValue) {
        Plugin.withTransaction {
            def plugins = Plugin.list()
            for (p in plugins) {
                def pluginUsage = data[p.name]
                if (pluginUsage != null) p.usage = pluginUsage / baseValue
            }
        }
    }
}
