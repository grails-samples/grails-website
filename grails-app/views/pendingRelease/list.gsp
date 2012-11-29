
<%@ page import="org.grails.plugin.PendingRelease" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'pendingRelease.label', default: 'PendingRelease')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
		<div class="top"></div>

        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table class="table table-bordered table-striped"> 
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'pendingRelease.id.label', default: 'Id')}" />
                        
                        
                            <g:sortableColumn property="pluginName" title="${message(code: 'pendingRelease.pluginName.label', default: 'Plugin Name')}" />
                        
                            <g:sortableColumn property="pluginVersion" title="${message(code: 'pendingRelease.pluginVersion.label', default: 'Plugin Version')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pendingReleaseInstanceList}" status="i" var="pendingReleaseInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${pendingReleaseInstance.id}">${fieldValue(bean: pendingReleaseInstance, field: "id")}</g:link></td>
                        
                        
                            <td>${fieldValue(bean: pendingReleaseInstance, field: "pluginName")}</td>
                        
                            <td>${fieldValue(bean: pendingReleaseInstance, field: "pluginVersion")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pendingReleaseInstanceTotal}" />
            </div>
        </div>
		<div class="bottom"></div>
    </body>
</html>
