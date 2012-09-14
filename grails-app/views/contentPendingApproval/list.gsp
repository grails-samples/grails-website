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

<h2>News Items</h2>
<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="title" title="Title"/>
        <g:sortableColumn property="status" title="Status"/>
        <th>Action</th>
    </tr>
</thead>
    <tbody>
    <g:each in="${newsItems}" var="newsItem">
        <tr>
            <td><g:link controller="newsItem" action="show" id="${newsItem.id}">${newsItem.title}</g:link></td>
            <td><common:approvalStatus status="${newsItem.status}" type="badge" /></td>
            <td>
                   <g:link controller="contentPendingApproval" action="approve" id="${newsItem.id}" params="[type: newsItem.class.name]"
                            class="btn btn-primary"
                            value="Send Response to Submitter"
                            onclick="return confirm('Are you sure?');">Approve</g:link>
                            
                    <g:link controller="contentPendingApproval" action="reject" id="${newsItem.id}" params="[type: newsItem.class.name]"
                             class="btn btn-danger"
                             value="Send Response to Submitter"
                             onclick="return confirm('Are you sure?');">Reject</g:link>

                            
            </td>
        </tr>
    </g:each>
    </tbody>
</table