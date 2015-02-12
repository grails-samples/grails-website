<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Web Site</title>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Edit Web Site
    <span class="pull-right">
        <g:link class="btn" action="list">Web Site List</g:link>
        <g:link class="btn" action="create">Add Web Site</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${webSiteInstance}"/>

<g:hasErrors bean="${webSiteInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${webSiteInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:uploadForm action="update" class="form-horizontal">
    <g:hiddenField name="id" value="${webSiteInstance?.id}"/>
    <g:hiddenField name="version" value="${webSiteInstance?.version}"/>

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