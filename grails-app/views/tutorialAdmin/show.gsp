<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tutorial - ${tutorialInstance?.title}</title>
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
    Show Tutorial
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="${tutorialInstance?.id}"/>
            <g:link class="btn" action="list">Tutorial List</g:link>
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
        <td class="show-value">${fieldValue(bean: tutorialInstance, field: "id")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Status</td>
        <td class="show-value"><common:approvalStatus status="${tutorialInstance.status}" type="badge" /></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Submitted By</td>
        <td class="show-value">
            <g:link controller="user" action="show" id="${tutorialInstance?.submittedBy?.id}">
                <avatar:gravatar email="${tutorialInstance?.submittedBy?.email}"
                                 size="16"/> ${tutorialInstance?.submittedBy?.email}
            </g:link>
        </td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Title</td>
        <td class="show-value">${fieldValue(bean: tutorialInstance, field: "title")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Description</td>
        <td class="show-value">${fieldValue(bean: tutorialInstance, field: "description")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">URL</td>
        <td class="show-value">
            <a href="${fieldValue(bean: tutorialInstance, field: "url")}"
               target="_blank">${fieldValue(bean: tutorialInstance, field: "url")}</a></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Tags</td>
        <td class="show-value">
            <g:each in="${tutorialInstance?.tags}" var="tag">
                <span class="badge">${tag}</span>
            </g:each>
        </td>
    </tr>
    <tr>
        <td class="show-label">Responses</td>
        <td>
            <dl style="margin: 0px;">
                <g:each in="${tutorialInstance.genericApprovalResponses}" var="${resp}">
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
        <g:hiddenField name="id" value="${tutorialInstance.id}"/>

        <div class="control-group">
            <label class="col-sm-2 control-label" for="status">Request has been:</label>

            <div class="col-sm-10">
                <select name="status" id="status">
                    <option value=""></option>
                    <option value="${ApprovalStatus.APPROVED}">APPROVED</option>
                    <option value="${ApprovalStatus.REJECTED}">REJECTED</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="col-sm-2 control-label" for="responseText">Response (for email):</label>

            <div class="col-sm-10">
                <textarea cols="50" rows="10" style="width: 99%;" name="responseText" id="responseText"></textarea>
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