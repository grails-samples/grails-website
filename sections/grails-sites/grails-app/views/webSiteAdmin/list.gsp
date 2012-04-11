<html>
<head>
    <meta content="admin" name="layout"/>
</head>

<body>

<h1 class="page-header">
    WebSite List
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">
            Create WebSite
        </g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Thumbnail</th>
        <th>Title</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${webSites}" var="webSite" status="idx">
        <tr>
            <td><g:link action="show" id="${webSite.id}">${webSite.id}</g:link></td>
            <td>
                <bi:hasImage bean="${webSite}">
                    <bi:img size="large" bean="${webSite}" />
                </bi:hasImage>
            </td>
            <td><g:link url="${webSite.url}" target="_blank">${webSite.title}</g:link></td>
            <td>${webSite.description}</td>
        </tr>
    </g:each>
    </tbody>
</table>

</body>
</html>