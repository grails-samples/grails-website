<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Video Host</title>
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
    Add Video Host
    <span class="pull-right">
        <g:link class="btn" action="list">Video Host List</g:link>
    </span>
</h1>

<flash:message flash="${flash}" bean="${videoHostInstance}"/>

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