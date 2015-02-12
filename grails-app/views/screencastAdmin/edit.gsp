<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Screencast</title>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Edit Screencast
    <span class="pull-right">
        <g:link class="btn" action="list">Screencast List</g:link>
        <g:link class="btn btn-primary" action="create">Add Screencast</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${screencastInstance}"/>

<g:form action="update" class="form-horizontal">
    <g:hiddenField name="id" value="${screencastInstance?.id}" />
    <fieldset>

        <g:render template="form"/>

        <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
            <g:submitButton name="update" class="btn btn-primary" value="Update"/>
            <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                            onclick="return confirm('Are you sure?');"/>
            <g:link class="btn" action="list">Cancel</g:link>
        </div></div>

    </fieldset>
</g:form>

</body>
</html>