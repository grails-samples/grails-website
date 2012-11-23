<html>
<head>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Downloadable File List
    <span class="pull-right">
        <g:link class="btn" action="create">Create Downloadable File</g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="title" title="Title"/>
        <g:sortableColumn property="fileType" title="File Type"/>
        <th>Download</th>
        <th># Mirrors</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${downloadFileInstanceList}" status="i" var="downloadFileInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show"
                        id="${downloadFileInstance.id}">${fieldValue(bean: downloadFileInstance, field: "id")}</g:link></td>

            <td>${fieldValue(bean: downloadFileInstance, field: "title")}</td>

            <td>${downloadFileInstance.getDisplayFileType()}</td>

            <td><g:link controller="downloadAdmin" action="show" id="${downloadFileInstance.id}">${downloadFileInstance.download}</g:link></td>

            <td>${downloadFileInstance.mirrors.size()}</td>


        </tr>
    </g:each>
    </tbody>
</table>

<div class="paginateButtons">
    <g:paginate total="${downloadInstanceTotal}"/>
</div>

</body>
</html>