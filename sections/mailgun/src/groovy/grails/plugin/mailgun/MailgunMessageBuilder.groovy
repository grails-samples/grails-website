/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.mailgun

import org.apache.commons.logging.LogFactory
import org.springframework.util.Assert

/**
 * Provides a DSL style interface to mail message sending/generation.
 * 
 * If the builder is constructed without a MailMessageContentRenderer, it is incapable
 * of rendering GSP views into message content.
 */
class MailgunMessageBuilder {

    private static log = LogFactory.getLog(this)

    final mailSender
    final mailMessageContentRenderer

    private final overrideAddress
    private final message = [:]

    private locale
    private multipart = false

    MailgunMessageBuilder(mailSender, config, mailMessageContentRenderer = null) {
        this.mailSender = mailSender
        this.mailMessageContentRenderer = mailMessageContentRenderer

        this.overrideAddress = config.overrideAddress ?: null
        def defaultFrom = overrideAddress ?: (config.default.from ?: null)
        def defaultTo = overrideAddress ?: (config.default.to ?: null)

        if (defaultFrom) {
            message.from = defaultFrom
        }

        if (defaultTo) {
            message.to = defaultTo
        }
    }

    def sendMessage() {
        def message = finishMessage()

        if (log.traceEnabled) {
            log.trace("Sending mail ${getDescription(message)}} ...")
        }

        mailSender.send message

        if (log.traceEnabled) {
            log.trace("Sent mail ${getDescription(message)}} ...")
        }

        message
    }

    void multipart(boolean multipart) {
        this.multipart = multipart
    }

    void multipart(int multipartMode) {
        this.multipart = multipartMode
    }

    void to(Object[] args) {
        Assert.notEmpty(args, "to cannot be null or empty")
        Assert.noNullElements(args, "to cannot contain null elements") 

        message.to = toDestinationAddresses(args)
    }

    void to(List args) {
        Assert.notEmpty(args, "to cannot be null or empty")
        Assert.noNullElements(args.toArray(), "to cannot contain null elements")

        to(*args)
    }

    void bcc(Object[] args) {
        Assert.notEmpty(args, "bcc cannot be null or empty")
        Assert.noNullElements(args, "bcc cannot contain null elements") 

        message.bcc = toDestinationAddresses(args)
    }

    void bcc(List args) {
        Assert.notEmpty(args, "bcc cannot be null or empty")
        Assert.noNullElements(args.toArray(), "bcc cannot contain null elements") 

        bcc(*args)
    }

    void cc(Object[] args) {
        Assert.notEmpty(args, "cc cannot be null or empty")
        Assert.noNullElements(args, "cc cannot contain null elements") 

        message.cc = toDestinationAddresses(args)
    }

    void cc(List args) {
        Assert.notEmpty(args, "cc cannot be null or empty")
        Assert.noNullElements(args.toArray(), "cc cannot contain null elements") 

        cc(*args)
    }

    void replyTo(CharSequence replyTo) {
        Assert.hasText(replyTo, "replyTo cannot be null or 0 length")

        message.replyTo = replyTo.toString()
    }

    void from(CharSequence from) {
        Assert.hasText(from, "from cannot be null or 0 length")

        message.from = from.toString()
    }

    void title(CharSequence title) {
        Assert.notNull(title, "title cannot be null")

        subject(title)
    }

    void subject(CharSequence title) {
        Assert.notNull(title, "subject cannot be null")

        message.subject = title.toString()
    }

    void body(CharSequence body) {
        Assert.notNull(body, "body cannot be null")

        text(body)
    }

    void body(Map params) {
        Assert.notEmpty(params, "body cannot be null or empty")

        def render = doRender(params)

        if (render.html) {
            html(render.out.toString())
        }
        else {
            text(render.out.toString())
        }
    }

    void text(Map params) {
        Assert.notEmpty(params, "text cannot be null or empty")

        text(doRender(params).out.toString())
    }

    void text(CharSequence text) {
        Assert.notNull(text, "text cannot be null")

        message.text = text.toString()
    }

    void html(Map params) {
        Assert.notEmpty(params, "html cannot be null or empty")

        html(doRender(params).out.toString())
    }

    void html(CharSequence text) {
        Assert.notNull(text, "html cannot be null")

        if (mimeCapable) {
            message.html = text.toString()
        }
        else {
            throw new RuntimeException("mail sender is not mime capable, try configuring a JavaMailSender")
        }
    }

    void locale(String localeStr) {
        Assert.hasText(localeStr, "locale cannot be null or empty")

        locale(new Locale(*localeStr.split('_', 3)))
    }

    void locale(Locale locale) {
        Assert.notNull(locale, "locale cannot be null")

        this.locale = locale
    }

    boolean isMimeCapable() {
        return true
    }

    protected doRender(Map params) {
        if (mailMessageContentRenderer == null) {
            throw new RuntimeException("mail message builder was constructed without a message content render so cannot render views")
        }

        if (!params.view) {
            throw new RuntimeException("no view specified")
        }

        return mailMessageContentRenderer.render(new StringWriter(), params.view, params.model, locale, params.plugin)
    }

    protected toDestinationAddresses(addresses) {
        if (overrideAddress) {
            addresses = addresses.collect { overrideAddress }
        }

        return addresses.collect { it?.toString() }
    }

    protected getDescription(message) {
        return "[${message.subject}] from [${message.from}] to ${message.to}"
    }

    protected finishMessage() {
        if (!message.text && !message.html) {
            throw new RuntimeException("message has no content, use text(), html() or body() methods to set content")
        }

        message.sentDate = new Date()
        return message
    }

}
