<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Screencast</title>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Add Tutorial
    <span class="pull-right">
        <g:link class="btn" action="list">Screencast List</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${screencastInstance}"/>

<g:hasErrors bean="${screencastInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${screencastInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:form action="save" class="form-horizontal">
    <fieldset>

        <g:render template="form" />

        <div class="form-actions">
            <g:submitButton name="create" class="btn btn-primary" value="Create" />
            <g:link class="btn" action="list">Cancel</g:link>
        </div>

    </fieldset>
</g:form>

</body>
</html>