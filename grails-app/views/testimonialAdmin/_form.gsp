<%@ page import="org.grails.community.Testimonial" %>

<div class="control-group">
    <label class="col-sm-2 control-label">Submitter</label>

    <div class="col-sm-10">
        <label class="checkbox" style="padding-left: 0px;">
            <g:link controller="user" action="show" id="${testimonialInstance?.submittedBy?.id}">
                <avatar:gravatar email="${testimonialInstance?.submittedBy?.email}"
                                 size="16"/> ${testimonialInstance?.submittedBy?.email}
            </g:link>
        </label>
    </div>
</div>

<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'title', 'error')}">
    <label class="col-sm-2 control-label" for="title">Title</label>

    <div class="col-sm-10">
        <g:textField class="form-control input-xxlarge" name="title" value="${testimonialInstance?.title}" />
    </div>
</div>


<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'companyName', 'error')}">
    <label class="col-sm-2 control-label" for="title">Company Name</label>

    <div class="col-sm-10">
        <g:textField class="form-control input-xxlarge" name="companyName" value="${testimonialInstance?.companyName}" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: testimonialInstance, field: 'featured', 'error')}">
    <label class="col-sm-2 control-label" for="featured">Is Featured?</label>

    <div class="col-sm-10">
        <label class="checkbox" for="featured">
            <g:checkBox name="featured" value="${testimonialInstance?.featured}"/>
            Check this box if the testimonial is featured on the <a href="/community/testimonial"
                                                                target="_blank">/community/testimonial</a> page.
        </label>
    </div>
</div>

<p><g:message code="testimonial.body.description"/></p>

<wiki:uploadImages prefix="testimonial${Testimonial.count() + 1}" />

<g:textArea name="body" class="wiki" rows="40" cols="130" value="${testimonialInstance?.body}"/>

</fieldset>


