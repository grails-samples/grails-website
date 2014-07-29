<%@ page import="org.grails.common.ApprovalStatus" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
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
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Submitted By</td>
        <td class="show-value">
            <g:link controller="user" action="show" id="${pluginPendingApproval?.submittedBy?.id}">
                <avatar:gravatar email="${pluginPendingApproval?.submittedBy?.email}"
                                 size="16"/> ${pluginPendingApproval?.submittedBy?.email}
            </g:link>
        </td>
    </tr>
    <tr>
        <td class="show-label">Name</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'name')}</td>
    </tr>
    <tr>
        <td class="show-label">SCM URL</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'scmUrl')}</td>
    </tr>
    <tr>
        <td class="show-label">Status</td>
        <td><common:approvalStatus status="${pluginPendingApproval.status}" type="badge" /></td>
    </tr>
    <tr>
        <td class="show-label">Notes</td>
        <td>${fieldValue(bean: pluginPendingApproval, field: 'notes')?.encodeAsHTML()?.replace('\n', '<br/>\n')}</td>
    </tr>
    <tr>
        <td class="show-label">Responses</td>
        <td>
            <dl style="margin: 0px;">
            <g:each in="${pluginPendingApproval.genericApprovalResponses}" var="${resp}">
                <dt>${resp.moderatedBy?.login} marked submission as ${resp.status?.toString()} on ${resp.dateCreated}
                    <dd><blockquote>${resp.responseText}</blockquote></dd>
                </dt>
            </g:each>
            </dl>
        </td>
    </tr>
    </tbody>
</table>

<div class="row">
    <div class="span4">
        <h3 class="page-header">Respond to Submitter</h3>

        <g:form action="disposition">
            <fieldset>
                <g:hiddenField name="id" value="${pluginPendingApproval.id}"/>
                <g:hiddenField name="pluginName" value="${pluginPendingApproval.name}"/>

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
                        <textarea cols="50" rows="10" style="width: 99%;" name="responseText" id="responseText" required="required"></textarea>
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

    </div>
    <div class="span8">
        <h3 class="page-header">Disqus Thread</h3>
        <div style="padding: 0 20px;">
            <disqus:comments bean="${pluginPendingApproval}"  url="${createLink(uri:request.forwardURI, absolute:true)}"/>
        </div>
    </div>
</div>




</body>
</html>
