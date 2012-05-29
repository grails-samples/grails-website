package org.grails.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class AmqpErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(AmqpErrorHandler.class);

    public void handleError(Throwable t) {
        logger.error("Unexpected messaging error", t);
    }
}
