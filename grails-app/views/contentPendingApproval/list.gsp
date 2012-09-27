<%@page import="grails.util.GrailsNameUtils" %>
<html>
<head>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">Content Approval Queue</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

<h2>Pending Items</h2>
<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="title" title="Title"/>
        <g:sortableColumn property="status" title="Status"/>
        <g:sortableColumn property="class" title="Type"/>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
        <g:each in="${pendingItems}" var="item">
        <tr>
            <td><g:link controller="${GrailsNameUtils.getPropertyName(item.getClass())}" action="show" id="${item.id}">${item.title}</g:link></td>
            <td><common:approvalStatus status="${item.status}" type="badge" /></td>
            <td>${item.getClass().simpleName}</td>
            <td>
                   <g:link controller="contentPendingApproval" action="approve" id="${item.id}" params="[type: item.class.name]"
                            class="btn btn-primary"
                            value="Send Response to Submitter"
                            onclick="return confirm('Are you sure?');">Approve</g:link>
                            
                    <g:link controller="contentPendingApproval" action="reject" id="${item.id}" params="[type: item.class.name]"
                             class="btn btn-danger"
                             value="Send Response to Submitter"
                             onclick="return confirm('Are you sure?');">Reject</g:link>

                            
            </td>
        </tr>
        </g:each>
    </tbody>
</table
