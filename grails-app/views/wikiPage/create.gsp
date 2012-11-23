

<%@ page import="org.grails.wiki.WikiPage" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <r:require modules="codeMirror, fancyBox"/>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.create.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${wikiPageInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${wikiPageInstance}" as="list"/>
    </div>
</g:hasErrors>

<g:form action="save" class="form-horizontal" >
<fieldset>

    

    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'title', 'error')}">
        <label class="control-label" for="title">
            <g:message code="wikiPage.title.label" default="Title"/>
        </label>

        <div class="controls">
            <g:textField name="title" pattern="${wikiPageInstance.constraints.title.matches}" required="" value="${wikiPageInstance?.title}"/>
        </div>
    </div>

    

    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'body', 'error')}">
        <label class="control-label" for="body">
            <g:message code="wikiPage.body.label" default="Body"/>
        </label>

        <div class="controls">
                          <g:textArea cols="30" rows="20" id="wikiPageBody" name="body"
                            value="${wikiPageInstance?.body}" class="input-fullsize"/>
        </div>
    </div>

    

    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'locked', 'error')}">
        <label class="control-label" for="locked">
            <g:message code="wikiPage.locked.label" default="Locked"/>
        </label>

        <div class="controls">
            <g:checkBox name="locked" value="${wikiPageInstance?.locked}" />
        </div>
    </div>

    

    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'deprecatedUri', 'error')}">
        <label class="control-label" for="deprecatedUri">
            <g:message code="wikiPage.deprecatedUri.label" default="Deprecated Uri"/>
        </label>

        <div class="controls">
            <g:textField name="deprecatedUri" value="${wikiPageInstance?.deprecatedUri}"/>
        </div>
    </div>

    

    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'deprecated', 'error')}">
        <label class="control-label" for="deprecated">
            <g:message code="wikiPage.deprecated.label" default="Deprecated"/>
        </label>

        <div class="controls">
            <g:checkBox name="deprecated" value="${wikiPageInstance?.deprecated}" />
        </div>
    </div>

    

    <div class="form-actions">
        <g:submitButton name="create" class="btn btn-primary"
                        value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        <g:link class="btn" action="list">Cancel</g:link>
    </div>
</fieldset>
</g:form>
<g:render template="/content/wikiCodeMirrorJavaScript"></g:render>
</body>
</html>
