<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Video Host</title>
    <meta name="layout" content="admin"/>
    <asset:stylesheet src="codeMirror"/>
    <asset:javascript src="codeMirror"/>
    <style type="text/css">
    .CodeMirror {
        border: 1px solid #eee;
    }
    .CodeMirror-scroll {
        height: auto;
        overflow-y: hidden;
        overflow-x: auto;
        width: 100%;
    }
    </style>
</head>

<body>

<h1 class="page-header">
    Edit Video Host
    <span class="pull-right">
        <g:link class="btn" action="list">Video Host List</g:link>
        <g:link class="btn btn-primary" action="create">Add Video Host</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${videoHostInstance}"/>

<g:form action="update" class="form-horizontal">
    <g:hiddenField name="id" value="${videoHostInstance?.id}" />
    <fieldset>

        <g:render template="form"/>

        <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
            <g:submitButton name="update" class="btn btn-primary" value="Update"/>
            <g:if test="${videoHostInstance.dependentScreencasts.size() == 0}">
                <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                                onclick="return confirm('Are you sure?');"/>
            </g:if>
            <g:link class="btn" action="list">Cancel</g:link>
        </div></div>

    </fieldset>
</g:form>

</body>
</html>