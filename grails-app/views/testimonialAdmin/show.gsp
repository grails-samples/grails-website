<%@ page import="org.grails.community.Testimonial" %>
<%@ page import="org.grails.common.ApprovalStatus"  %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
    <asset:stylesheet src="fancyBox"/>
    <asset:javascript src="fancyBox"/>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.show.label" args="[entityName]"/>
    <span class="pull-right">
        <g:form controller="testimonialAdmin">
            <g:hiddenField name="id" value="${testimonialInstance?.id}"/>
            <g:link class="btn" action="list">
                <g:message code="default.list.label" args="[entityName]"/>
            </g:link>
            <g:actionSubmit class="btn btn-info" action="edit"
                            value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            <g:actionSubmit class="btn btn-danger" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </g:form>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

<table class="table table-bordered table-striped">
    <tbody>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.id.label" default="Id"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "id")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.title.label" default="Title"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "title")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.status.label" default="Status"/>
        </td>

        
            <td class="show-value"><common:approvalStatus status="${testimonialInstance.status}" type="badge" /></td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.submittedBy.label" default="Submitted By"/>
        </td>

        
        <td class="show-value">
            <g:link controller="user" action="show" id="${testimonialInstance?.submittedBy?.id}">
                <avatar:gravatar email="${testimonialInstance?.submittedBy?.email}"
                                 size="16"/> ${testimonialInstance?.submittedBy?.email}
            </g:link>
            </td>
        
    </tr>

    <tr>
        <td class="show-label">
            <g:message code="testimonial.companyName.label" default="Company Name"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "companyName")}</td>
        
    </tr>

    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.featured.label" default="Featured"/>
        </td>

        
            <td class="show-value">${testimonialInstance?.featured ? 'Yes' : 'No'}</td>
        
    </tr>


    <tr>
        <td class="show-label">Responses</td>
        <td>
            <dl style="margin: 0px;">
                <g:each in="${testimonialInstance.genericApprovalResponses}" var="${resp}">
                    <dt>${resp.moderatedBy?.login} marked submission as <common:approvalStatus status="${resp.status}"/> on ${resp.dateCreated}
                    <dd><blockquote>${resp.responseText}</blockquote></dd>
                    </dt>
                </g:each>
            </dl>
        </td>
    </tr>
    
    </tbody>
</table>


<h2>Content</h2>
<wiki:shorten length="100" wikiText="${testimonialInstance.body}" />

<p><a class="btn preview" data-contents="${testimonialInstance.body?.encodeAsHTML()}">Preview</a></p>

<h2 class="page-header">Respond to Submitter</h2>
<g:form class="form-horizontal" action="disposition">
    <fieldset>
        <g:hiddenField name="id" value="${testimonialInstance.id}"/>

        <div class="control-group">
            <label class="control-label" for="status">Request has been:</label>

            <div class="controls">
                <select name="status" id="status">
                    <option value=""></option>
                    <option value="${ApprovalStatus.APPROVED}">APPROVED</option>
                    <option value="${ApprovalStatus.REJECTED}">REJECTED</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="responseText">Response (for email):</label>

            <div class="controls">
                <textarea cols="50" rows="10" style="width: 99%;" name="responseText" id="responseText"></textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="featured">Is Featured?</label>

            <div class="controls">
                <label class="checkbox" for="featured">
                    <g:checkBox name="featured" value="${testimonialInstance?.featured}"/>
                    Check this box if the website is featured on the Testimonials page.
                </label>
            </div>
        </div>

        <div class="form-actions">
            <g:submitButton id="submit-btn" name="submit" class="btn btn-primary"
                            value="Send Response to Submitter"
                            onclick="return confirm('Are you sure?');"/>
        </div>
    </fieldset>
</g:form>

<asset:script>
    $(function () {
        // Show preview of wiki content
        $('.preview').click(function() {
            var body = $(this).data('contents');
            $.ajax({
                type    : "POST",
                cache   : false,
                url     : "/previewWikiPage",
                data    : { body: body },
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
</asset:script>

</body>
</html>
