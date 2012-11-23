<html>
<head>
    <meta content="admin" name="layout"/>
</head>

<body>

<h1 class="page-header">
    Create Download
    <span class="pull-right">
        <g:link class="btn" action="list">Download List</g:link>
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
    <fieldset>

        <g:render template="form" bean="${downloadInstance}" />

        <div class="form-actions">
            <g:submitButton name="create" class="btn btn-primary" value="Create" />
            <g:link class="btn" action="list">Cancel</g:link>
        </div>

    </fieldset>
</g:form>

</body>
</html>