<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Tutorial</title>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Edit Tutorial
    <span class="pull-right">
        <g:link class="btn" action="list">Tutorial List</g:link>
        <g:link class="btn" action="create">Add Tutorial</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${tutorialInstance}"/>

<g:hasErrors bean="${tutorialInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${tutorialInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:uploadForm action="update" class="form-horizontal">
    <g:hiddenField name="id" value="${tutorialInstance?.id}"/>
    <g:hiddenField name="version" value="${tutorialInstance?.version}"/>

    <fieldset>

        <g:render template="form"/>

        <div class="form-group"><div class="col-sm-offset-2 col-sm-10">

            <g:submitButton name="update" class="btn btn-primary" value="Update"/>
            <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                            onclick="return confirm('Are you sure?');"/>
            <g:link class="btn" action="list">Cancel</g:link>
        </div></div>

    </fieldset>

</g:uploadForm>

</body>
</html>