<%@ page import="org.grails.community.Testimonial" %>



<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="testimonial.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" maxlength="50" required="" value="${testimonialInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="testimonial.status.label" default="Status" />
		
	</label>
	<g:select name="status" from="${org.grails.common.ApprovalStatus?.values()}" keys="${org.grails.common.ApprovalStatus.values()*.name()}" value="${testimonialInstance?.status?.name()}" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'submittedBy', 'error')} required">
	<label for="submittedBy">
		<g:message code="testimonial.submittedBy.label" default="Submitted By" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="submittedBy" name="submittedBy.id" from="${org.grails.auth.User.list()}" optionKey="id" required="" value="${testimonialInstance?.submittedBy?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'body', 'error')} ">
	<label for="body">
		<g:message code="testimonial.body.label" default="Body" />
		
	</label>
	<g:textField name="body" value="${testimonialInstance?.body}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'companyName', 'error')} ">
	<label for="companyName">
		<g:message code="testimonial.companyName.label" default="Company Name" />
		
	</label>
	<g:textField name="companyName" value="${testimonialInstance?.companyName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'featured', 'error')} ">
	<label for="featured">
		<g:message code="testimonial.featured.label" default="Featured" />
		
	</label>
	<g:checkBox name="featured" value="${testimonialInstance?.featured}" />
</div>

