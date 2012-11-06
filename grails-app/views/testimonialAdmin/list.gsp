
<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
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

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        
        <g:sortableColumn property="id"
                          title="${message(code: 'testimonial.id.label', default: 'Id')}"/>

        <th><g:message code="testimonial.submittedBy.label" default="Submitted By"/></th>


        <g:sortableColumn property="title"
                          title="${message(code: 'testimonial.title.label', default: 'Title')}"/>
        

        <g:sortableColumn property="companyName"
                          title="${message(code: 'testimonial.companyName.label', default: 'Company Name')}"/>


        <g:sortableColumn property="featured" title="Is Featured?"/>


        <g:sortableColumn property="status"
                          title="${message(code: 'testimonial.status.label', default: 'Status')}"/>



    </tr>
    </thead>
    <tbody>
    <g:each in="${testimonialInstanceList}" status="i" var="testimonialInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            
            <td><g:link action="show"
                        id="${testimonialInstance.id}">${fieldValue(bean: testimonialInstance, field: "id")}</g:link></td>
            
            <td><g:link controller="user" action="show" id="${testimonialInstance?.submittedBy?.id}">
                <avatar:gravatar email="${testimonialInstance?.submittedBy?.email}"
                                 size="16"/> ${testimonialInstance?.submittedBy?.email}
            </g:link></td>

            <td>${fieldValue(bean: testimonialInstance, field: "title")}</td>

            <td>${fieldValue(bean: testimonialInstance, field: "companyName")}</td>

            <td><g:formatBoolean boolean="${testimonialInstance.featured}"/></td>

            <td><common:approvalStatus status="${testimonialInstance.status}" type="badge" /></td>


        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${testimonialInstanceTotal}"/>
</div>

</body>
</html>
