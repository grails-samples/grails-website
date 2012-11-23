<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Video Host List</title>
    <meta name="layout" content="admin"/>
</head>
<body>

<h1 class="page-header">
    Video Host List
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">Add Video Host</g:link>
    </span>
</h1>

<flash:message flash="${flash}"/>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="name" title="Name"/>
        <th>Embedded Template</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${videoHostInstanceList}" status="i" var="videoHostInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td nowrap="nowrap"><g:link action="show" id="${videoHostInstance.id}">${fieldValue(bean: videoHostInstance, field: "id")}</g:link></td>
            <td nowrap="nowrap">${fieldValue(bean: videoHostInstance, field: "name")}</td>
            <td>
                <pre><code>${fieldValue(bean: videoHostInstance, field: "embedTemplate")}</code></pre>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>

</body>
</html>