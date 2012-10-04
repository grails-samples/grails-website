
<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="master"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>

    <section id="main">
        <h1 class="page-header">
    ${testimonialInstance?.title}
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">
        ${flash.message}
    </div>
</g:if>

    <shiro:hasRole name="Administrator" >
        <span class="pull-right">
            <g:form controller="testimonialAdmin">
                <g:hiddenField name="id" value="${testimonialInstance?.id}"/>
                <g:actionSubmit class="btn btn-info" action="edit"
                                value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                <g:actionSubmit class="btn btn-danger" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </g:form>
        </span>
    </shiro:hasRole>


    <wiki:text key="${testimonialInstance?.title}">
        ${testimonialInstance?.body}
    </wiki:text>



            </section>
</div>

</body>
</html>
