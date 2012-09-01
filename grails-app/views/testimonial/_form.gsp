<%@ page import="org.grails.community.Testimonial" %>



<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="testimonial.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" maxlength="50" required="" value="${testimonialInstance?.title}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'companyName', 'error')} ">
	<label for="companyName">
		<g:message code="testimonial.companyName.label" default="Company Name" />
		
	</label>
	<g:textField name="companyName" value="${testimonialInstance?.companyName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testimonialInstance, field: 'content', 'error')} ">
	<label for="content">
		<g:message code="testimonial.content.label" default="Content" />
		
	</label>
	<g:textField name="content" value="${testimonialInstance?.content}"/>
</div>

