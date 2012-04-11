<html>
<head>
    <meta content="admin" name="layout"/>
</head>

<body>

<h1 class="page-header">
    WebSite <small>${webSite.title}</small>
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="${webSite.id}"/>
            <g:link class="btn" action="list">WebSite List</g:link>
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
        <td class="show-label">ID</td>
        <td class="show-value">${webSite.id}</td>
    </tr>
    <tr>
        <td class="show-label">Title</td>
        <td class="show-value">${webSite.title}</td>
    </tr>
    <tr>
        <td class="show-label">Description</td>
        <td class="show-value">${webSite.description}</td>
    </tr>
    <tr>
        <td class="show-label">URL</td>
        <td class="show-value">${webSite.url}</td>
    </tr>
    <tr>
        <td class="show-label">Featured?</td>
        <td class="show-value">${webSite.featured ? 'Yes' : 'No'}</td>
    </tr>
    <tr>
        <td class="show-label">Popularity</td>
        <td class="show-value">
            ${webSite.popularity?.liked} likes,
            ${webSite.popularity?.disliked} dislikes,
            ${webSite.popularity?.netLiked} net linked
        </td>
    </tr>
    <tr>
        <td class="show-label">Thumbnail</td>
        <td class="show-value">
            <bi:hasImage bean="${webSite}">
                <bi:img size="large" bean="${webSite}"/>
            </bi:hasImage>
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>