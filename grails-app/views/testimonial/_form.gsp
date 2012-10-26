<%@ page import="org.grails.community.Testimonial" %>

<r:require modules="codeMirror, fancyBox, imageUpload" />

<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'title', 'error')} required">
        <label class="control-label" for="title">
            <g:message code="testimonial.title.label" default="Title" />
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <g:textField name="title" maxlength="50" required="" value="${testimonialInstance?.title}"/>
        </div>
    </div>


    <div class="control-group ${hasErrors(bean: testimonialInstance, field: 'companyName', 'error')} ">
        <label class="control-label" for="companyName">
            <g:message code="testimonial.companyName.label" default="Company Name" />
        </label>

        <div class="controls">
            <g:textField name="companyName" value="${testimonialInstance?.companyName}"/>
        </div>
    </div>

<wiki:uploadImages prefix="testimonial${Testimonial.count() + 1}" />

<p><g:message code="testimonial.body.description"/></p>

<g:textArea name="body" rows="40" cols="130" value="${testimonialInstance?.body}"/>

<r:script>
    $(function () {
        var editor = CodeMirror.fromTextArea(document.getElementById('body'), {
            lineNumbers: true,
            wordWrap: true,
            lineWrapping: true,
            gutter: true,
            fixedGutter: true,
            autofocus: true
        });

        // store editor so it can be accessed later by image upload
        $("#body").data(editor, "editor");

        $('.preview').click(function() {
            $.ajax({
                type    : "POST",
                cache   : false,
                url     : "/previewWikiPage",
                data    : { body: editor.getValue() },
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
