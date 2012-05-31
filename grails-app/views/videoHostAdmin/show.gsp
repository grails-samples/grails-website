<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Video Host - ${videoHostInstance?.name}</title>
    <meta name="layout" content="admin"/>
</head>

<body>

<h1 class="page-header">
    Show Video Host
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="${videoHostInstance?.id}"/>
            <g:link class="btn" action="list">Video Host List</g:link>
            <g:link class="btn btn-info" action="edit" id="${videoHostInstance?.id}">Edit</g:link>
            <g:if test="${videoHostInstance.dependentScreencasts.size() == 0}">
                <g:actionSubmit class="btn btn-danger" action="delete" value="Delete"
                                onclick="return confirm('Are you sure?');"/>
            </g:if>
        </g:form>
    </span>
</h1>

<flash:message flash="${flash}"/>

<table class="table table-bordered table-striped">
    <tbody>

    <tr>
        <td class="show-label" nowrap="nowrap" style="width: 150px;">ID</td>
        <td class="show-value">${fieldValue(bean: videoHostInstance, field: "id")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Name</td>
        <td class="show-value">${fieldValue(bean: videoHostInstance, field: "name")}</td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">HTML Template</td>
        <td class="show-value"><pre><code>${fieldValue(bean: videoHostInstance, field: "embedTemplate")}</code></pre></td>
    </tr>
    <tr>
        <td class="show-label" nowrap="nowrap">Dependencies</td>
        <td class="show-value">
            <p>There are ${videoHostInstance.dependentScreencasts.size()} screencasts using this template</p>
            <ul>
                <g:each in="${videoHostInstance.dependentScreencasts}" var="screencast">
                    <li><g:link controller="screencastAdmin" action="show"
                                id="${screencast.id}">${screencast.title?.encodeAsHTML()}</g:link></li>
                </g:each>
            </ul>
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>