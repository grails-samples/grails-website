
<%@ page import="org.grails.wiki.WikiPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
		<div class="top"></div>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'wikiPage.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'wikiPage.title.label', default: 'Title')}" />
                        
                            <g:sortableColumn property="body" title="${message(code: 'wikiPage.body.label', default: 'Body')}" />
                        
                            <g:sortableColumn property="locked" title="${message(code: 'wikiPage.locked.label', default: 'Locked')}" />
                        
                            <g:sortableColumn property="deprecatedUri" title="${message(code: 'wikiPage.deprecatedUri.label', default: 'Deprecated Uri')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'wikiPage.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${wikiPageInstanceList}" status="i" var="wikiPageInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${wikiPageInstance.id}">${fieldValue(bean: wikiPageInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: wikiPageInstance, field: "title")}</td>
                        
                            <td>${fieldValue(bean: wikiPageInstance, field: "body")}</td>
                        
                            <td><g:formatBoolean boolean="${wikiPageInstance.locked}" /></td>
                        
                            <td>${fieldValue(bean: wikiPageInstance, field: "deprecatedUri")}</td>
                        
                            <td><g:formatDate date="${wikiPageInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${wikiPageInstanceTotal}" />
            </div>
        </div>
		<div class="bottom"></div>
    </body>
</html>
