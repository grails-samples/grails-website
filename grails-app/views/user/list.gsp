
<%@ page import="org.grails.auth.User" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.list.label" args="[entityName]"/>

    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">
              <g:message code="default.new.label" args="[entityName]"/>
          </g:link>

    </span>

</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>
<g:form url="[action:'search']">
   <input name="q"></input>
   <button class="btn btn-primary">Search</button>
</g:form>
<table class="table table-bordered table-striped">
    <thead>
    <tr>

        <g:sortableColumn property="id"
                          title="${message(code: 'user.id.label', default: 'Id')}"/>

        <g:sortableColumn property="email"
                          title="${message(code: 'user.email.label', default: 'Email')}"/>

        <g:sortableColumn property="login"
                          title="${message(code: 'user.login.label', default: 'Login')}"/>

        <g:sortableColumn property="password"
                          title="${message(code: 'user.password.label', default: 'Password')}"/>

    </tr>
    </thead>
    <tbody>
    <g:each in="${userInstanceList}" status="i" var="userInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td><g:link action="show"
                        id="${userInstance.id}">${fieldValue(bean: userInstance, field: "id")}</g:link></td>

            <td>${fieldValue(bean: userInstance, field: "email")}</td>

            <td>${fieldValue(bean: userInstance, field: "login")}</td>

            <td>${fieldValue(bean: userInstance, field: "password")}</td>

        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${userInstanceTotal ?: 0}"/>
</div>

</body>
</html>
