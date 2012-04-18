<%@ page import="org.grails.plugin.ApprovalStatus" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <script type="text/javascript">
        $(function() {
            $('#submit-btn').attr('disabled', 'disabled');
            $('#status').change(function() {
                if ($(this).val() != '') {
                    $('#submit-btn').removeAttr('disabled');
                } else {
                    $('#submit-btn').attr('disabled', 'disabled');
                }
            });
            $('a.default_response').click(function(evt) {
                evt.preventDefault();
                $('#responseText').val($(this).data('response'))
                                  .focus();
            });
        })
    </script>
</head>

<body>

<h1 class="page-header">
    Show Plugin Pending Approval
    <span class="pull-right">
        <g:link class="btn" action="list">Plugin Pending Approval List</g:link>
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
        <td class="show-label">ID</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'id')}</td>
    <tr>
        <td class="show-label">Name</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'name')}</td>
    </tr>
    <tr>
        <td class="show-label">SCM URL</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'scmUrl')}</td>
    </tr>
    <tr>
        <td class="show-label">Email</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'email')}</td>
    </tr>
    <tr>
        <td class="show-label">Status</td>
        <td>${pluginPendingApproval.displayStatus()}</td>
    </tr>
    <tr>
        <td class="show-label">Notes</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'notes')}</td>
    </tr>
    <tr>
        <td class="show-label">Responses</td>
        <td>
            <dl style="margin: 0px;">
            <g:each in="${pluginPendingApproval.pluginPendingApprovalResponses}" var="${resp}">
                <dt>${resp.user?.login} marked submission as ${resp.displayStatus()} on ${resp.dateCreated}
                    <dd><blockquote>${resp.responseText}</blockquote></dd>
                </dt>
            </g:each>
            </dl>
        </td>
    </tr>
    </tbody>
</table>

<h2 class="page-header">Respond to Submitter</h2>
<g:form class="form-horizontal" action="dispositionPendingPlugin">
    <fieldset>
        <g:hiddenField name="id" value="${pluginPendingApproval.id}"/>

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
                <p>
                    <g:each in="${defaultResponses}" var="resp" status="i">
                        <g:if test="${i > 0}">
                            |
                        </g:if>
                        <a href="#" class="default_response" data-response="${resp.value}">${resp.key}</a>
                    </g:each>
                </p>
            </div>
        </div>

        <div class="form-actions">
            <g:submitButton id="submit-btn" name="submit" class="btn btn-primary"
                            value="Send Response to Submitter"
                            onclick="return confirm('Are you sure?');" />
        </div>
    </fieldset>
</g:form>

</body>
</html>
