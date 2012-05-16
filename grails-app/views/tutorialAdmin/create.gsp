<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Tutorial</title>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Add Tutorial
    <span class="pull-right">
        <g:link class="btn" action="list">Tutorial List</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${tutorialInstance}"/>

<g:hasErrors bean="${tutorialInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${tutorialInstance}" as="list"/>
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