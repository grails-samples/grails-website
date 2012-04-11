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
    <tbody>

    <tr>
        <td class="show-label" nowrap="nowrap" style="width: 150px;">ID</td>
        <td class="show-value">${fieldValue(bean: downloadFileInstance, field: "id")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Title</td>
        <td class="show-value">${fieldValue(bean: downloadFileInstance, field: "title")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">File Type</td>
        <td class="show-value">${downloadFileInstance.getDisplayFileType()}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Download</td>
        <td class="show-value"><g:link controller="downloadAdmin" action="show" id="${downloadFileInstance.id}">${downloadFileInstance.download}</g:link></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Mirrors</td>
        <td class="show-value">
            <ul>
                <g:each in="${downloadFileInstance.mirrors}" var="mirror">
                    <li><g:link controller="mirror" action="show" id="${mirror.id}">${mirror}</g:link></li>
                </g:each>
            </ul>
        </td>
    </tr>

    </tbody>
</table>

</body>
</html>