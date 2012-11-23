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
        <g:sortableColumn property="submittedBy" title="Submitter"/>
        <g:sortableColumn property="name" title="Title"/>
        <g:sortableColumn property="SCM" title="URL"/>
        <g:sortableColumn property="dateCreated" title="Date Submitted"/>
        <g:sortableColumn property="status" title="Status"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${pluginPendingApprovalList}" status="i" var="pendingPluginApproval">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${pendingPluginApproval.id}">${fieldValue(bean: pendingPluginApproval, field: "id")}</g:link></td>
            <td>
                <g:link controller="user" action="show" id="${pendingPluginApproval?.submittedBy?.id}">
                    <avatar:gravatar email="${pendingPluginApproval?.submittedBy?.email}"
                                     size="16"/> ${pendingPluginApproval?.submittedBy?.email}
                </g:link>
            </td>
            <td>${fieldValue(bean: pendingPluginApproval, field: "name")} (version ${fieldValue(bean: pendingPluginApproval, field: "versionNumber")})</td>
            <td><g:link url="${fieldValue(bean: pendingPluginApproval, field: "scmUrl")}" target="_blank">
                ${fieldValue(bean: pendingPluginApproval, field: "scmUrl")}</g:link></td>
            <td>${fieldValue(bean: pendingPluginApproval, field: "dateCreated")}</td>
            <td><common:approvalStatus status="${pendingPluginApproval.status}" type="badge" /></td>
        </tr>
    </g:each>
    </tbody>
</table>