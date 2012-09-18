
<%@ page import="org.grails.auth.User" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.show.label" args="[entityName]"/>
    <span class="pull-right">
        <g:form action="delete" id="${userInstance?.id}">
            <g:link class="btn" action="list">
                <g:message code="default.list.label" args="[entityName]"/>
            </g:link>
            <g:link class="btn btn-info" action="edit" id="${userInstance?.id}">
                <g:message code="default.button.edit.label" default="Edit"/>
            </g:link>
            <g:submitButton name="delete" class="btn btn-danger" action="delete"
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
            <g:message code="user.id.label" default="Id"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: userInstance, field: "id")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="user.email.label" default="Email"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: userInstance, field: "email")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="user.login.label" default="Login"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: userInstance, field: "login")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="user.password.label" default="Password"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: userInstance, field: "password")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="user.permissions.label" default="Permissions"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: userInstance, field: "permissions")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="user.roles.label" default="Roles"/>
        </td>

        
            <td class="show-value">
                <ul>
                    <g:each in="${userInstance.roles}" var="r">
                        <li>
                            <g:link controller="role" action="show"
                                    id="${r.id}">${r?.encodeAsHTML()}</g:link>
                        </li>
                    </g:each>
                </ul>
            </td>
        
    </tr>
    
    </tbody>
</table>

</body>
</html>
