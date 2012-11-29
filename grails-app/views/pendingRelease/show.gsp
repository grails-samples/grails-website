
<%@ page import="org.grails.plugin.PendingRelease" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'pendingRelease.label', default: 'PendingRelease')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
		<div class="top"></div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table class="table table-bordered table-striped">
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pendingRelease.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pendingReleaseInstance, field: "id")}</td>
                            
                        </tr>

                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pendingRelease.pluginName.label" default="Plugin Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pendingReleaseInstance, field: "pluginName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="pendingRelease.pluginVersion.label" default="Plugin Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: pendingReleaseInstance, field: "pluginVersion")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${pendingReleaseInstance?.id}" />
                    <span class="button"><g:actionSubmit class="btn edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="btn deploy" action="deploy" value="Deploy" /></span>
                    <span class="button"><g:actionSubmit class="btn btn-alert delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
		<div class="bottom"></div>
    </body>
</html>
