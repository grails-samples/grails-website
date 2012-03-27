<%@ page import="org.grails.plugin.PendingRelease" %>



<div class="fieldcontain ${hasErrors(bean: pendingReleaseInstance, field: 'zip', 'error')} required">
	<label for="zip">
		<g:message code="pendingRelease.zip.label" default="Zip" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="zip" name="zip" />
</div>

<div class="fieldcontain ${hasErrors(bean: pendingReleaseInstance, field: 'pom', 'error')} required">
	<label for="pom">
		<g:message code="pendingRelease.pom.label" default="Pom" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="pom" name="pom" />
</div>

<div class="fieldcontain ${hasErrors(bean: pendingReleaseInstance, field: 'xml', 'error')} required">
	<label for="xml">
		<g:message code="pendingRelease.xml.label" default="Xml" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="xml" name="xml" />
</div>

<div class="fieldcontain ${hasErrors(bean: pendingReleaseInstance, field: 'pluginName', 'error')} ">
	<label for="pluginName">
		<g:message code="pendingRelease.pluginName.label" default="Plugin Name" />
		
	</label>
	<g:textField name="pluginName" value="${pendingReleaseInstance?.pluginName}" />
</div>

<div class="fieldcontain ${hasErrors(bean: pendingReleaseInstance, field: 'pluginVersion', 'error')} ">
	<label for="pluginVersion">
		<g:message code="pendingRelease.pluginVersion.label" default="Plugin Version" />
		
	</label>
	<g:textField name="pluginVersion" value="${pendingReleaseInstance?.pluginVersion}" />
</div>

