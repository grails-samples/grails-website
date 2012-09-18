

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

<g:hiddenField name="id" value="${userInstance?.id}"/>
<g:hiddenField name="version" value="${userInstance?.version}"/>



<div class="control-group ${hasErrors(bean: userInstance, field: 'email', 'error')}">
    <label class="control-label" for="email">
        <g:message code="user.email.label" default="Email"/>
    </label>

    <div class="controls">
        <g:field type="email" name="email" required="" value="${userInstance?.email}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: userInstance, field: 'login', 'error')}">
    <label class="control-label" for="login">
        <g:message code="user.login.label" default="Login"/>
    </label>

    <div class="controls">
        <g:textField name="login" maxlength="15" required="" value="${userInstance?.login}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: userInstance, field: 'enabled', 'error')}">
    <label class="control-label" for="enabled">
        <g:message code="user.enabled.label" default="Enabled"/>
    </label>

    <div class="controls">
        <g:checkBox name="enabled" value="${userInstance?.enabled}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'permissions', 'error')}">
    <label class="control-label" for="permissions">
        <g:message code="user.permissions.label" default="Permissions"/>
    </label>

    <div class="controls">
        <g:textArea name="permissions" cols="40" rows="7" value="${userInstance?.permissions.join('\n')}" /> (Separated by new lines or semi-colons)
        
    </div>
</div>


<div class="control-group ${hasErrors(bean: userInstance, field: 'roles', 'error')}">
    <label class="control-label" for="roles">
        <g:message code="user.roles.label" default="Roles"/>
    </label>

    <div class="controls">
        <g:select name="roles" from="${org.grails.auth.Role.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.roles*.id}" class="many-to-many"/>
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
