package org.grails.logging;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import grails.plugin.cloudfoundry.AppCloudEnvironment;
import grails.plugin.cloudfoundry.RabbitServiceInfo;

import org.apache.log4j.Logger;
//import org.cloudfoundry.runtime.env.CloudEnvironment;
//import org.cloudfoundry.runtime.env.RabbitServiceInfo;
import org.springframework.amqp.rabbit.log4j.AmqpAppender;
import org.springframework.web.util.Log4jWebConfigurer;

/**
 * Dynamic configuration of Log4j with an AMQP appender.
 */
public class Log4jConfigListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        Log4jWebConfigurer.initLogging(event.getServletContext());

//        CloudEnvironment cloudEnvironment = new CloudEnvironment();
//        if (cloudEnvironment.isCloudFoundry()) {
        AppCloudEnvironment cloudEnvironment = new AppCloudEnvironment();
        if (cloudEnvironment.isAvailable()) {
            initCloudFoundryAppender(cloudEnvironment);
        }
        else {
            initLocalRabbitAppender();
        }
    }
 
    public void contextDestroyed(ServletContextEvent event) {
        Log4jWebConfigurer.shutdownLogging(event.getServletContext());
    }
    
//    void initCloudFoundryAppender(CloudEnvironment env) {
    void initCloudFoundryAppender(AppCloudEnvironment env) {
        try {
            /*
            List<RabbitServiceInfo> rabbitServiceInfoList = env.getServiceInfos(RabbitServiceInfo.class);
            if (rabbitServiceInfoList.size() == 0) return;

            RabbitServiceInfo rabbitServiceInfo = rabbitServiceInfoList.get(0);
            */
            RabbitServiceInfo rabbitServiceInfo = (RabbitServiceInfo) env.getServiceByVendor("rabbitmq");
            AmqpAppender amqpAppender = getAmqpAppender();
            if (amqpAppender != null) {
                amqpAppender.close();                                 // Required for changes to take effect
                amqpAppender.setHost(rabbitServiceInfo.getHost());
                amqpAppender.setPort(rabbitServiceInfo.getPort());
                amqpAppender.setVirtualHost(rabbitServiceInfo.getVirtualHost());
                amqpAppender.setUsername(rabbitServiceInfo.getUserName());
                amqpAppender.setPassword(rabbitServiceInfo.getPassword());
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    void initLocalRabbitAppender() {
        try {
            AmqpAppender amqpAppender = getAmqpAppender();
            amqpAppender.close();                                 // Required for changes to take effect
            amqpAppender.setHost("localhost");
//            amqpAppender.setPort(rabbitServiceInfo.getPort());
            amqpAppender.setVirtualHost("/");
            amqpAppender.setUsername("guest");
            amqpAppender.setPassword("guest");
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    AmqpAppender getAmqpAppender() {
        return (AmqpAppender) Logger.getRootLogger().getAppender("amqp");
    }
}
