<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Web Site List</title>
    <meta name="layout" content="admin"/>
</head>
<body>

<h1 class="page-header">
    Web Site List
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">Add Web Site</g:link>
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
        <g:sortableColumn property="dateCreated" title="Date Submitted"/>
        <g:sortableColumn property="featured" title="Is Featured?"/>
        <g:sortableColumn property="approvalStatus" title="Status"/>
    </tr>
    </thead>
    <tbody>
    <g:each in="${webSiteInstanceList}" status="i" var="webSiteInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${webSiteInstance.id}">${fieldValue(bean: webSiteInstance, field: "id")}</g:link></td>
            <td>
                <g:link controller="user" action="show" id="${webSiteInstance?.submittedBy?.id}">
                    <avatar:gravatar email="${webSiteInstance?.submittedBy?.email}"
                                     size="16"/> ${webSiteInstance?.submittedBy?.email}
                </g:link>
            </td>
            <td>${fieldValue(bean: webSiteInstance, field: "title")}</td>
            <td><g:link url="${fieldValue(bean: webSiteInstance, field: "url")}" target="_blank">
                ${fieldValue(bean: webSiteInstance, field: "url")}</g:link></td>
            <td>${fieldValue(bean: webSiteInstance, field: "dateCreated")}</td>
            <td><g:formatBoolean boolean="${webSiteInstance.featured}"/></td>
            <td><common:approvalStatus status="${webSiteInstance.status}" type="badge" /></td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${webSiteInstanceTotal}"/>
</div>

</body>
</html>