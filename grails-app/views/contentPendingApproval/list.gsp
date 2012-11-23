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
<g:render template="pendingApprovals" />
