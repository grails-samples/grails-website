<html>
<head>
    <meta content="admin" name="layout"/>
</head>

<body>

<h1 class="page-header">
    Edit Download
    <span class="pull-right">
        <g:link class="btn" action="list">Download List</g:link>
        <g:link class="btn" action="create">Create Download</g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${download}">
    <div class="alert alert-error">
        <g:renderErrors bean="${downloadInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:form action="save" class="form-horizontal">

    <g:hiddenField name="id" value="${downloadInstance?.id}"/>
    <g:hiddenField name="version" value="${downloadInstance?.version}"/>

    <fieldset>

        <g:render template="form" bean="${downloadInstance}" />

        <div class="form-actions">
            <g:submitButton name="update" class="btn btn-primary" value="Update"/>
            <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                            onclick="return confirm('Are you sure?');"/>
            <g:link class="btn" action="list">Cancel</g:link>
        </div>

    </fieldset>
</g:form>

</body>
</html>