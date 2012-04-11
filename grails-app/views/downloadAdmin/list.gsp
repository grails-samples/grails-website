<html>
<head>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Download List
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">Create Download</g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="softwareName" title="Software Name"/>
        <g:sortableColumn property="softwareVersion" title="Software Version"/>
        <g:sortableColumn property="downloadCount" title="Download Count"/>
        <g:sortableColumn property="releaseDate" title="Release Date"/>
        <g:sortableColumn property="betaRelease" title="Beta Release?"/>
        <g:sortableColumn property="latestRelease" title="Latest Release?"/>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${downloadInstanceList}" status="i" var="downloadInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${downloadInstance.id}">${fieldValue(bean: downloadInstance, field: "id")}</g:link></td>
            <td>${fieldValue(bean: downloadInstance, field: "softwareName")}</td>
            <td>${fieldValue(bean: downloadInstance, field: "softwareVersion")}</td>
            <td>${fieldValue(bean: downloadInstance, field: "downloadCount")}</td>
            <td>${fieldValue(bean: downloadInstance, field: "releaseDate")}</td>
            <td>
                <g:if test="${downloadInstance.betaRelease}">
                    <span class="label">Beta</span>
                </g:if>
                <g:else>
                    <span class="label label-success">Production</span>
                </g:else>
            </td>
            <td>
                <g:if test="${downloadInstance.latestRelease}">
                    <span class="label label-success">Yes</span>
                </g:if>
                <g:elseif test="${!downloadInstance.latestRelease && !downloadInstance.betaRelease}">
                    <span class="label label-inverse">No</span>
                </g:elseif>
                <g:else>
                    <span class="label">N/A</span>
                </g:else>
            </td>
            <td>
                <g:if test="${!downloadInstance.latestRelease && !downloadInstance.betaRelease}">
                    <g:form action="markAsLatestRelease" id="${downloadInstance.id}">
                        <g:submitButton name="submit" value="Mark as Latest" class="btn btn-small btn-warning" />
                    </g:form>
                </g:if>
                <g:else>
                    &nbsp;
                </g:else>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="paginateButtons">
    <g:paginate total="${downloadInstanceTotal}"/>
</div>

</body>
</html>
