<%@ page import="org.grails.taggable.Tag" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
</head>

<body>

<g:form action="save" method="post" class="form-horizontal">
    <h1 class="page-header">
        Tag List
        <span class="pull-right">
            <input class="input-xlarge" type="text" id="name" name="name"
                   value="${fieldValue(bean: tagInstance, field: 'name')}"
                   placeholder="New tag name"/>
            <g:submitButton name="submit" class="btn btn-primary" action="create" value="Create Tag"/>
        </span>
    </h1>
</g:form>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

<table class="table">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="name" title="Name"/>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${tagInstanceList}" status="i" var="tagInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td style="width: 100px;">${fieldValue(bean: tagInstance, field: 'id')}</td>
            <td>${fieldValue(bean: tagInstance, field: 'name')}</td>
            <td style="text-align: right;"><g:link action="edit" id="${tagInstance?.id}" class="btn btn-mini btn-primary">Edit</g:link></td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${tagInstanceTotal}" />
</div>

</body>
</html>
