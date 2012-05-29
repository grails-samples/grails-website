package org.grails.website.amqp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.ErrorHandler

/**
 * @file
 * @author  Stephane Maldini <smaldini@doc4web.com>
 * @version 1.0
 * @date 29/05/12
 
 * @section DESCRIPTION
 *
 * [Does stuff]
 */
 class ErrorsHandler implements ErrorHandler{

     final static Logger logger = LoggerFactory.getLogger(ErrorsHandler)

         /**
          * Handle the given error, possibly rethrowing it as a fatal exception
          *
          * @param t
          */
         public void handleError(Throwable t) {
             logger.error("unexpected messaging error", t)
         }
}
