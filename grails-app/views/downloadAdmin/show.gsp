<html>
<head>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Show Download
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="${downloadInstance?.id}"/>
            <g:link class="btn" action="list">Download List</g:link>
            <g:actionSubmit class="btn btn-info" action="edit" value="Edit"/>
            <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                            onclick="return confirm('Are you sure?');"/>
        </g:form>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<table class="table table-bordered table-striped">
    <tbody>

    <tr>
        <td class="show-label" nowrap="nowrap" style="width: 150px;">ID</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "id")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Software Name</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "softwareName")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Software Version</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "softwareVersion")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Download Count</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "downloadCount")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Release Date</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "releaseDate")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Latest Release?</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "latestRelease") == 'true' ? 'Yes' : 'No'}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Beta Release?</td>
        <td class="show-value">${fieldValue(bean: downloadInstance, field: "betaRelease") == 'true' ? 'Yes' : 'No'}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Files</td>
        <td class="show-value">
            <ul>
                <g:each in="${downloadInstance.files}" var="file">
                    <li><g:link controller="downloadFile" action="show" id="${file.id}">${file.title}</g:link></li>
                </g:each>
            </ul>
        </td>
    </tr>

    </tbody>
</table>

</body>
</html>