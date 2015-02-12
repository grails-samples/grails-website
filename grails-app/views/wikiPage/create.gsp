

<%@ page import="org.grails.wiki.WikiPage" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <asset:stylesheet src="codeMirror.css"/>
    <asset:javascript src="codeMirror.js"/>
    <asset:stylesheet src="fancyBox.css"/>
    <asset:javascript src="fancyBox.js"/>
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
        <label class="col-sm-2 control-label" for="title">
            <g:message code="wikiPage.title.label" default="Title"/>
        </label>

        <div class="col-sm-10">
            <g:textField class="form-control" name="title" pattern="${wikiPageInstance.constraints.title.matches}" required="" value="${wikiPageInstance?.title}"/>
        </div>
    </div>



    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'body', 'error')}">
        <label class="col-sm-2 control-label" for="body">
            <g:message code="wikiPage.body.label" default="Body"/>
        </label>

        <div class="col-sm-10">
                          <g:textArea class="form-control input-fullsize" cols="30" rows="20" id="wikiPageBody" name="body"
                            value="${wikiPageInstance?.body}" />
        </div>
    </div>



    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'locked', 'error')}">
        <label class="col-sm-2 control-label" for="locked">
            <g:message code="wikiPage.locked.label" default="Locked"/>
        </label>

        <div class="col-sm-10>
          <div class="checkbox">
              <label>
                <g:checkBox name="locked" value="${wikiPageInstance?.locked}" />
              </label>
          </div>
        </div>
    </div>

    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'deprecatedUri', 'error')}">
        <label class="col-sm-2 control-label" for="deprecatedUri">
            <g:message code="wikiPage.deprecatedUri.label" default="Deprecated Uri"/>
        </label>

        <div class="col-sm-10">
            <g:textField class="form-control" name="deprecatedUri" value="${wikiPageInstance?.deprecatedUri}"/>
        </div>
    </div>



    <div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'deprecated', 'error')}">
        <label class="col-sm-2 control-label" for="deprecated">
            <g:message code="wikiPage.deprecated.label" default="Deprecated"/>
        </label>

        <div class="col-sm-10><div class="checkbox">
            <g:checkBox name="deprecated" value="${wikiPageInstance?.deprecated}" />
        </div>
    </div>



    <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
        <g:submitButton name="create" class="btn btn-primary"
                        value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        <g:link class="btn" action="list">Cancel</g:link>
    </div></div>
</fieldset>
</g:form>
<g:render template="/content/wikiCodeMirrorJavaScript"></g:render>
</body>
</html>
