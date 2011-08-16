<%@ page import="org.grails.wiki.WikiPage" %>



<div class="fieldcontain ${hasErrors(bean: wikiPageInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="wikiPage.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" value="${wikiPageInstance?.title}" />
</div>

<div class="fieldcontain ${hasErrors(bean: wikiPageInstance, field: 'body', 'error')} ">
	<label for="body">
		<g:message code="wikiPage.body.label" default="Body" />
		
	</label>
	<g:textField name="body" value="${wikiPageInstance?.body}" />
</div>

<div class="fieldcontain ${hasErrors(bean: wikiPageInstance, field: 'locked', 'error')} ">
	<label for="locked">
		<g:message code="wikiPage.locked.label" default="Locked" />
		
	</label>
	<g:checkBox name="locked" value="${wikiPageInstance?.locked}" />
</div>

<div class="fieldcontain ${hasErrors(bean: wikiPageInstance, field: 'deprecatedUri', 'error')} ">
	<label for="deprecatedUri">
		<g:message code="wikiPage.deprecatedUri.label" default="Deprecated Uri" />
		
	</label>
	<g:textField name="deprecatedUri" value="${wikiPageInstance?.deprecatedUri}" />
</div>

<div class="fieldcontain ${hasErrors(bean: wikiPageInstance, field: 'deprecated', 'error')} ">
	<label for="deprecated">
		<g:message code="wikiPage.deprecated.label" default="Deprecated" />
		
	</label>
	<g:checkBox name="deprecated" value="${wikiPageInstance?.deprecated}" />
</div>

<div class="fieldcontain ${hasErrors(bean: wikiPageInstance, field: 'versions', 'error')} ">
	<label for="versions">
		<g:message code="wikiPage.versions.label" default="Versions" />
		
	</label>
	
<ul>
<g:each in="${wikiPageInstance?.versions?}" var="v">
    <li><g:link controller="version" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="version" action="create" params="['wikiPage.id': wikiPageInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'version.label', default: 'Version')])}</g:link>

</div>

