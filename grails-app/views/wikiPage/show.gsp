<%@ page import="org.grails.wiki.WikiPage" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.show.label" args="[entityName]"/>
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="${wikiPageInstance?.id}"/>
            <g:link class="btn" action="list">
                <g:message code="default.list.label" args="[entityName]"/>
            </g:link>
            <g:link controller="content" id="${wikiPageInstance.title}" class="btn btn-success">View Page</g:link>
            <g:actionSubmit class="btn btn-info" action="edit"
                            value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            <g:actionSubmit class="btn btn-danger" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </g:form>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

<table class="table table-bordered table-striped">
    <tbody>

    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.id.label" default="ID"/></td>
        <td class="show-value">${fieldValue(bean: wikiPageInstance, field: "id")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.title.label" default="Title"/></td>
        <td class="show-value">${fieldValue(bean: wikiPageInstance, field: "title")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.body.label" default="Body"/></td>
        <td class="show-value"><pre style="height: 200px; overflow-y: auto;"><code>${fieldValue(bean: wikiPageInstance, field: "body")}</code></pre></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.locked.label" default="Locked"/></td>
        <td class="show-value"><g:formatBoolean boolean="${wikiPageInstance?.locked}"/></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.deprecatedUri.label" default="Deprecated URI"/></td>
        <td class="show-value">${fieldValue(bean: wikiPageInstance, field: "deprecatedUri")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.deprecated.label" default="Deprecated"/></td>
        <td class="show-value"><g:formatBoolean boolean="${wikiPageInstance?.deprecated}"/></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.dateCreated.label" default="Date Created"/></td>
        <td class="show-value"><g:formatDate date="${wikiPageInstance?.dateCreated?.toDate()}"/></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.lastUpdated.label" default="Last Updated"/></td>
        <td class="show-value"><g:formatDate date="${wikiPageInstance?.lastUpdated?.toDate()}"/></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap"><g:message code="wikiPage.versions.label" default="Versions"/></td>
        <td class="show-value">
            <ul>
                <g:each in="${wikiPageInstance.versions}" var="v">
                    <li><g:link controller="version" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
                </g:each>
            </ul>
        </td>
    </tr>

    </tbody>
</table>

</body>
</html>
