

<%@ page import="org.grails.wiki.WikiPage" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'wikiPage.label', default: 'WikiPage')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
    <asset:stylesheet src="codeMirror.css"/>
    <asset:javascript src="codeMirror.js"/>
    <asset:stylesheet src="fancyBox.css"/>
    <asset:javascript src="fancyBox.js"/>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.edit.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link>
        <g:link class="btn" action="create"><g:message code="default.create.label" args="[entityName]"/></g:link>
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

<g:form method="post" class="form-horizontal" url="[controller:'wikiPage', action:'update', id:wikiPageInstance.id]" >

<g:hiddenField name="id" value="${wikiPageInstance?.id}"/>
<g:hiddenField name="version" value="${wikiPageInstance?.version}"/>



<div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'title', 'error')}">
    <label class="col-sm-2 control-label" for="title">
        <g:message code="wikiPage.title.label" default="Title"/>
    </label>

    <div class="col-sm-10">
        <g:textField class="form-control form-control"  name="title" pattern="${wikiPageInstance.constraints.title.matches}" required="" value="${wikiPageInstance?.title}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'body', 'error')}">
    <label class="col-sm-2 control-label" for="body">
        <g:message code="wikiPage.body.label" default="Body"/>
    </label>

    <div class="col-sm-10">
               <g:textArea class="form-control form-control input-fullsize" cols="30" rows="20" id="wikiPageBody" name="body"
                            value="${wikiPageInstance?.body}" />
    </div>
</div>


<div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'locked', 'error')}">
    <label class="col-sm-2 control-label" for="locked">
        <g:message code="wikiPage.locked.label" default="Locked"/>
    </label>

    <div class="col-sm-10">
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

    <div class="col-sm-10">
        <div class="checkbox">
          <label>
            <g:checkBox name="deprecated" value="${wikiPageInstance?.deprecated}" />
          </label>
        </div>
    </div>
</div>


<div class="control-group ${hasErrors(bean: wikiPageInstance, field: 'versions', 'error')}">
    <label class="col-sm-2 control-label" for="versions">
        <g:message code="wikiPage.versions.label" default="Versions"/>
    </label>

    <div class="col-sm-10">

<ul class="one-to-many">
<g:each in="${wikiPageInstance?.versions?}" var="v">
    <li><g:link controller="version" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="version" action="create" params="['wikiPage.id': wikiPageInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'version.label', default: 'Version')])}</g:link>
</li>
</ul>

    </div>
</div>


<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <g:submitButton name="update" class="btn btn-primary"
                      value="${message(code: 'default.button.update.label', default: 'Update')}"/>
      <g:actionSubmit class="btn btn-danger" action="delete"
                      value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                      onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
      <g:link class="btn" action="list">Cancel</g:link>
    </div>
</div>

</g:form>
<g:render template="/content/wikiCodeMirrorJavaScript"></g:render>
</body>
</html>
