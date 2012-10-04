<%@ page import="org.grails.community.Testimonial" %>

<r:require modules="codeMirror, fancyBox, imageUpload" />


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


<p><g:message code="testimonial.body.description"/></p>
<p>
<div id="images">

</div>
<a id="upload-image" class="btn">Add image</a>
</p>

<g:textArea name="body" rows="40" cols="130" value="${testimonialInstance?.body}"/>

</fieldset>


<r:script>
    $(function () {
        var myCodeMirror = CodeMirror.fromTextArea(document.getElementById('body'), {
            lineNumbers: true,
            wordWrap: true,
            lineWrapping: true,
            gutter: true,
            fixedGutter: true,
            autofocus: true
        });

        $('.preview').click(function() {
            $.ajax({
                type    : "POST",
                cache   : false,
                url     : "/previewWikiPage",
                data    : { body: myCodeMirror.getValue() },
                success : function(data) {
                    $.fancybox(data, {
                        maxWidth    : 710,
                        maxHeight   : 600,
                        fitToView   : false,
                        width       : '70%',
                        height      : '70%',
                        autoSize    : false,
                        closeClick  : false,
                        openEffect  : 'fade',
                        closeEffect : 'fade'
                    });
                }
            });
            return false;
        });

    });
</r:script>

