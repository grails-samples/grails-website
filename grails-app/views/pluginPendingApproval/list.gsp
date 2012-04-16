<html>
<head>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">Pending Plugin Approval List</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="name" title="Name"/>
        <th>SCM URL</th>
        <th>Username</th>
        <g:sortableColumn property="email" title="Email"/>
        <g:sortableColumn property="status" title="Status"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${pluginPendingApprovalList}" var="ppal">
        <tr>
            <td><g:link action="show" id="${ppal.id}">${ppal?.id}</g:link></td>
            <td>${fieldValue(bean: ppal, field: 'name')}</td>
            <td>${fieldValue(bean: ppal, field: 'scmUrl')}</td>
            <td>${ppal.user?.login}</td>
            <td>${fieldValue(bean: ppal, field: 'email')}</td>
            <td>${ppal.displayStatus()}</td>
        </tr>
    </g:each>
    </tbody>
</table