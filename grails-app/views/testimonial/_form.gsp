<%@ page import="org.grails.community.Testimonial" %>

<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'title', 'error')} required">
        <label class="col-sm-2 control-label" for="title">
            <g:message code="testimonial.title.label" default="Title" />
            <span class="required-indicator">*</span>
        </label>

        <div class="col-sm-10">
            <g:textField name="title" maxlength="50" required="" value="${testimonialInstance?.title}"/>
        </div>
    </div>


    <div class="control-group ${hasErrors(bean: testimonialInstance, field: 'companyName', 'error')} ">
        <label class="col-sm-2 control-label" for="companyName">
            <g:message code="testimonial.companyName.label" default="Company Name" />
        </label>

        <div class="col-sm-10">
            <g:textField name="companyName" value="${testimonialInstance?.companyName}"/>
        </div>
    </div>

<wiki:uploadImages prefix="testimonial${Testimonial.count() + 1}" />

<p><g:message code="testimonial.body.description"/></p>

<g:textArea name="body" class="wiki" rows="40" cols="130" value="${testimonialInstance?.body}"/>