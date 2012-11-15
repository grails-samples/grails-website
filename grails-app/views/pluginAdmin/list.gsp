<html>
<head>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">Plugin List</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>
<g:form url="[controller:'pluginAdmin', action:'search']">
   <input name="q"></input>
   <button class="btn btn-primary">Search</button>
</g:form>
<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="title" title="Title"/>
        <th>Dependency</th>
        <th>Tags</th>
        <g:sortableColumn property="author" title="Author"/>
        <th>Links</th>
    </tr>
</thead>
    <tbody>
    <g:each in="${pluginList}" var="plugin">
        <tr>
            <td><g:link action="show" id="${plugin.id}">${plugin?.id}</g:link></td>
            <td>${plugin?.title}</td>
            <td><code>${plugin?.defaultDependencyScope} ${plugin?.dependencyDeclaration}</code></td>
            <td><g:each in="${plugin.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="#">${tag}</a></g:each></td>
            <td><a href="mailto:${plugin?.authorEmail}">${plugin?.author}</a></td>
            <td>
                <g:if test="${plugin.scmUrl}">
                    <a href="${plugin.scmUrl}" class="btn btn-mini" target="_blank">Source</a>
                </g:if>
                <g:if test="${plugin.documentationUrl}">
                    <a href="${plugin.documentationUrl}" class="btn btn-mini" target="_blank">Documentation</a>
                </g:if>
                <g:if test="${plugin.issuesUrl}">
                    <a href="${plugin.issuesUrl}" class="btn btn-mini" target="_blank">Issues</a>
                </g:if>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
<div class="pagination">
    <g:paginate total="${pluginTotal}"/>
</div>

</body>