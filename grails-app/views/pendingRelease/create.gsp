

<%@ page import="org.grails.plugin.PendingRelease" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'pendingRelease.label', default: 'PendingRelease')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
		<div class="top"></div>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pendingReleaseInstance}">
            <div class="errors">
                <g:renderErrors bean="${pendingReleaseInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save"  enctype="multipart/form-data">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="zip"><g:message code="pendingRelease.zip.label" default="Zip" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pendingReleaseInstance, field: 'zip', 'errors')}">
                                    <input type="file" id="zip" name="zip" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pom"><g:message code="pendingRelease.pom.label" default="Pom" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pendingReleaseInstance, field: 'pom', 'errors')}">
                                    <input type="file" id="pom" name="pom" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="xml"><g:message code="pendingRelease.xml.label" default="Xml" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pendingReleaseInstance, field: 'xml', 'errors')}">
                                    <input type="file" id="xml" name="xml" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pluginName"><g:message code="pendingRelease.pluginName.label" default="Plugin Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pendingReleaseInstance, field: 'pluginName', 'errors')}">
                                    <g:textField name="pluginName" value="${pendingReleaseInstance?.pluginName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pluginVersion"><g:message code="pendingRelease.pluginVersion.label" default="Plugin Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pendingReleaseInstance, field: 'pluginVersion', 'errors')}">
                                    <g:textField name="pluginVersion" value="${pendingReleaseInstance?.pluginVersion}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
		<div class="bottom"></div>
    </body>
</html>
