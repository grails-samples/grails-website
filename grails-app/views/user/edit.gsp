

<%@ page import="org.grails.auth.User" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.edit.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link>
        <g:link class="btn" action="create"><g:message code="default.create.label" args="[entityName]"/></g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${userInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${userInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:form method="post" class="form-horizontal" >

<g:render template="form"/>

<div class="form-actions">
    <g:actionSubmit name="update" class="btn btn-primary"
                    value="${message(code: 'default.button.update.label', default: 'Update')}"/>
    <g:actionSubmit class="btn btn-danger" action="delete"
                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    <g:link class="btn" action="list">Cancel</g:link>
</div>

</g:form>

</body>
</html>
