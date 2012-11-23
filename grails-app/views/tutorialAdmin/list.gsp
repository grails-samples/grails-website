<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Web Site List</title>
    <meta name="layout" content="admin"/>
</head>
<body>

<h1 class="page-header">
    Tutorial List
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">Add Tutorial</g:link>
    </span>
</h1>

<flash:message flash="${flash}"/>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="submittedBy" title="Submitter"/>
        <g:sortableColumn property="title" title="Title"/>
        <g:sortableColumn property="url" title="URL"/>
        <g:sortableColumn property="dateCreated" title="Date Created"/>
        <g:sortableColumn property="status" title="Status"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${tutorialInstanceList}" status="i" var="tutorialInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${tutorialInstance.id}">${fieldValue(bean: tutorialInstance, field: "id")}</g:link></td>
            <td>
                <g:link controller="user" action="show" id="${tutorialInstance?.submittedBy?.id}">
                    <avatar:gravatar email="${tutorialInstance?.submittedBy?.email}"
                                     size="16"/> ${tutorialInstance?.submittedBy?.email}
                </g:link>
            </td>
            <td>${fieldValue(bean: tutorialInstance, field: "title")}</td>
            <td><g:link url="${fieldValue(bean: tutorialInstance, field: "url")}" target="_blank">
                ${fieldValue(bean: tutorialInstance, field: "url")}</g:link></td>
            <td>${fieldValue(bean: tutorialInstance, field: "dateCreated")}</td>
            <td><common:approvalStatus status="${tutorialInstance.status}" type="badge" /></td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${tutorialInstanceTotal}"/>
</div>

</body>
</html>