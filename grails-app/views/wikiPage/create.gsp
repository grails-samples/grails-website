

<%@ page import="org.grails.wiki.WikiPage" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}" />
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
            <g:hasErrors bean="${wikiPageInstance}">
            <div class="errors">
                <g:renderErrors bean="${wikiPageInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="wikiPage.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: wikiPageInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${wikiPageInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="body"><g:message code="wikiPage.body.label" default="Body" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: wikiPageInstance, field: 'body', 'errors')}">
                                    <g:textField name="body" value="${wikiPageInstance?.body}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locked"><g:message code="wikiPage.locked.label" default="Locked" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: wikiPageInstance, field: 'locked', 'errors')}">
                                    <g:checkBox name="locked" value="${wikiPageInstance?.locked}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deprecatedUri"><g:message code="wikiPage.deprecatedUri.label" default="Deprecated Uri" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: wikiPageInstance, field: 'deprecatedUri', 'errors')}">
                                    <g:textField name="deprecatedUri" value="${wikiPageInstance?.deprecatedUri}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deprecated"><g:message code="wikiPage.deprecated.label" default="Deprecated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: wikiPageInstance, field: 'deprecated', 'errors')}">
                                    <g:checkBox name="deprecated" value="${wikiPageInstance?.deprecated}" />
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
