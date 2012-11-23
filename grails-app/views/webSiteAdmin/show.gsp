<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Web Site - ${webSiteInstance?.title}</title>
    <meta name="layout" content="admin"/>
    <script>
        $(function () {
            $('#submit-btn').attr('disabled', 'disabled');
            $('#status').change(function () {
                if ($(this).val() != '') {
                    $('#submit-btn').removeAttr('disabled');
                } else {
                    $('#submit-btn').attr('disabled', 'disabled');
                }
            });
            $('a.default_response').click(function (evt) {
                evt.preventDefault();
                $('#responseText').val($(this).data('response'))
                        .focus();
            });
        })
    </script>
</head>

<body>

<h1 class="page-header">
    Show Web Site
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="${webSiteInstance?.id}"/>
            <g:link class="btn" action="list">Web Site List</g:link>
            <g:actionSubmit class="btn btn-info" action="edit" value="Edit"/>
            <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                            onclick="return confirm('Are you sure?');"/>
        </g:form>
    </span>
</h1>

<flash:message flash="${flash}"/>

<table class="table table-bordered table-striped">
    <tbody>

    <tr>
        <td class="show-label" nowrap="nowrap" style="width: 150px;">ID</td>
        <td class="show-value">${fieldValue(bean: webSiteInstance, field: "id")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Status</td>
        <td class="show-value"><common:approvalStatus status="${webSiteInstance.status}" type="badge" /></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Submitted By</td>
        <td class="show-value">
            <g:link controller="user" action="show" id="${webSiteInstance?.submittedBy?.id}">
                <avatar:gravatar email="${webSiteInstance?.submittedBy?.email}"
                                 size="16"/> ${webSiteInstance?.submittedBy?.email}
            </g:link>
        </td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Title</td>
        <td class="show-value">${fieldValue(bean: webSiteInstance, field: "title")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Short Description</td>
        <td class="show-value">${fieldValue(bean: webSiteInstance, field: "shortDescription")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Description</td>
        <td class="show-value">${fieldValue(bean: webSiteInstance, field: "description")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">URL</td>
        <td class="show-value">
            <a href="${fieldValue(bean: webSiteInstance, field: "url")}"
               target="_blank">${fieldValue(bean: webSiteInstance, field: "url")}</a></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Is Featured?</td>
        <td class="show-value">${webSiteInstance?.featured ? 'Yes' : 'No'}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Preview Image</td>
        <td class="show-value">
            <bi:hasImage bean="${webSiteInstance}">
                <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank"><bi:img size="large"
                                                                                        bean="${webSiteInstance}"/></a>
            </bi:hasImage>
        </td>
    </tr>
    <tr>
        <td class="show-label">Responses</td>
        <td>
            <dl style="margin: 0px;">
                <g:each in="${webSiteInstance.genericApprovalResponses}" var="${resp}">
                    <dt>${resp.moderatedBy?.login} marked submission as <common:approvalStatus status="${resp.status}"/> on ${resp.dateCreated}
                    <dd><blockquote>${resp.responseText}</blockquote></dd>
                    </dt>
                </g:each>
            </dl>
        </td>
    </tr>

    </tbody>
</table>

<h2 class="page-header">Respond to Submitter</h2>
<g:form class="form-horizontal" action="disposition">
    <fieldset>
        <g:hiddenField name="id" value="${webSiteInstance.id}"/>

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
                    <g:checkBox name="featured" value="${webSiteInstance?.featured}"/>
                    Check this box if the website is featured on the <a href="/websites"
                                                                        target="_blank">community websites</a> page.
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

</body>
</html>
