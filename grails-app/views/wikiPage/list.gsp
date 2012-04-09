<%@ page import="org.grails.wiki.WikiPage" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.list.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create"><g:message code="default.create.label"
                                                                   args="[entityName]"/></g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="${message(code: 'wikiPage.id.label', default: 'Id')}"/>
        <g:sortableColumn property="title" title="${message(code: 'wikiPage.title.label', default: 'Title')}"/>
        <g:sortableColumn property="locked" title="${message(code: 'wikiPage.locked.label', default: 'Locked')}"/>
        <g:sortableColumn property="deprecatedUri"
                          title="${message(code: 'wikiPage.deprecatedUri.label', default: 'Deprecated Uri')}"/>
        <g:sortableColumn property="dateCreated"
                          title="${message(code: 'wikiPage.dateCreated.label', default: 'Date Created')}"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${wikiPageInstanceList}" status="i" var="wikiPageInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show"
                        id="${wikiPageInstance.id}">${fieldValue(bean: wikiPageInstance, field: "id")}</g:link></td>

            <td>${fieldValue(bean: wikiPageInstance, field: "title")}</td>

            <td><g:formatBoolean boolean="${wikiPageInstance.locked}"/></td>

            <td>${fieldValue(bean: wikiPageInstance, field: "deprecatedUri")}</td>

            <td><g:formatDate date="${wikiPageInstance.dateCreated?.toDate()}"/></td>

        </tr>
    </g:each>
    </tbody>
</table>

<div class="paginateButtons">
    <g:paginate total="${wikiPageInstanceTotal}"/>
</div>

</body>
</html>
