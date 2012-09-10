

<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.create.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link>
        <g:link class="btn" action="create"><g:message code="default.create.label" args="[entityName]"/></g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${testimonialInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${testimonialInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:form method="post" class="form-horizontal" >

<g:hiddenField name="id" value="${testimonialInstance?.id}"/>
<g:hiddenField name="version" value="${testimonialInstance?.version}"/>



<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'title', 'error')}">
    <label class="control-label" for="title">
        <g:message code="testimonial.title.label" default="Title"/>
    </label>

    <div class="controls">
        <g:textField name="title" maxlength="50" required="" value="${testimonialInstance?.title}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'status', 'error')}">
    <label class="control-label" for="status">
        <g:message code="testimonial.status.label" default="Status"/>
    </label>

    <div class="controls">
        <g:select name="status" from="${org.grails.common.ApprovalStatus?.values()}" keys="${org.grails.common.ApprovalStatus.values()*.name()}" value="${testimonialInstance?.status?.name()}" noSelection="['': '']"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'submittedBy', 'error')}">
    <label class="control-label" for="submittedBy">
        <g:message code="testimonial.submittedBy.label" default="Submitted By"/>
    </label>

    <div class="controls">
        <g:select id="submittedBy" name="submittedBy.id" from="${org.grails.auth.User.list()}" optionKey="id" required="" value="${testimonialInstance?.submittedBy?.id}" class="many-to-one"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'body', 'error')}">
    <label class="control-label" for="body">
        <g:message code="testimonial.body.label" default="Body"/>
    </label>

    <div class="controls">
        <g:textField name="body" value="${testimonialInstance?.body}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'companyName', 'error')}">
    <label class="control-label" for="companyName">
        <g:message code="testimonial.companyName.label" default="Company Name"/>
    </label>

    <div class="controls">
        <g:textField name="companyName" value="${testimonialInstance?.companyName}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'featured', 'error')}">
    <label class="control-label" for="featured">
        <g:message code="testimonial.featured.label" default="Featured"/>
    </label>

    <div class="controls">
        <g:checkBox name="featured" value="${testimonialInstance?.featured}" />
    </div>
</div>


<div class="form-actions">
    <g:submitButton name="update" class="btn btn-primary"
                    value="${message(code: 'default.button.update.label', default: 'Update')}"/>
    <g:actionSubmit class="btn btn-danger" action="delete"
                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    <g:link class="btn" action="list">Cancel</g:link>
</div>

</g:form>

</body>
</html>
