

<%@ page import="org.grails.auth.User" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.create.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link>
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

<g:form action="save" class="form-horizontal" >
<fieldset>

    

    <div class="control-group ${hasErrors(bean: userInstance, field: 'email', 'error')}">
        <label class="col-sm-2 control-label" for="email">
            <g:message code="user.email.label" default="Email"/>
        </label>

        <div class="col-sm-10">
            <g:field type="email" name="email" required="" value="${userInstance?.email}"/>
        </div>
    </div>

    

    <div class="control-group ${hasErrors(bean: userInstance, field: 'login', 'error')}">
        <label class="col-sm-2 control-label" for="login">
            <g:message code="user.login.label" default="Login"/>
        </label>

        <div class="col-sm-10">
            <g:textField name="login" maxlength="15" required="" value="${userInstance?.login}"/>
        </div>
    </div>

    

    <div class="form-actions">
        <g:submitButton name="create" class="btn btn-primary"
                        value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        <g:link class="btn" action="list">Cancel</g:link>
    </div>
</fieldset>
</g:form>

</body>
</html>
