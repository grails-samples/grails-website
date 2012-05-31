<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Screencast List</title>
    <meta name="layout" content="admin"/>
</head>
<body>

<h1 class="page-header">
    Screencast List
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">Add Screencast</g:link>
    </span>
</h1>

<flash:message flash="${flash}"/>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="submittedBy" title="Submitter"/>
        <g:sortableColumn property="title" title="Title"/>
        <th>Video Host</th>
        <g:sortableColumn property="dateCreated" title="Date Created"/>
        <g:sortableColumn property="status" title="Status"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${screencastInstanceList}" status="i" var="screencastInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${screencastInstance.id}">${fieldValue(bean: screencastInstance, field: "id")}</g:link></td>
            <td>
                <g:link controller="user" action="show" id="${screencastInstance?.submittedBy?.id}">
                    <avatar:gravatar email="${screencastInstance?.submittedBy?.email}"
                                     size="16"/> ${screencastInstance?.submittedBy?.email}
                </g:link>
            </td>
            <td>${fieldValue(bean: screencastInstance, field: "title")}</td>
            <td><g:link controller="videoHostAdmin" action="show" id="${screencastInstance?.videoHost?.id}">${screencastInstance?.videoHost?.name}</g:link></td>
            <td>${fieldValue(bean: screencastInstance, field: "dateCreated")}</td>
            <td><common:approvalStatus status="${screencastInstance.status}" type="badge" /></td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${screencastInstanceTotal}"/>
</div>

</body>
</html>