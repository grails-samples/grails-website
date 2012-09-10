<%@ page import="org.grails.community.Testimonial" %>

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

    <div class="control-group ${hasErrors(bean: testimonialInstance, field: 'content', 'error')} ">
        <div class="controls">
            <g:textArea name="body" rows="40" cols="130" value="${testimonialInstance?.body}"/>
        </div>
    </div>
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
