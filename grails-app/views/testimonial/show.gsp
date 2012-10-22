
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
        <h1 class="page-header">${testimonialInstance?.title}
            <shiro:hasRole name="Administrator" >
                <span class="pull-right">
                    <g:form controller="testimonial">
                        <g:hiddenField name="id" value="${testimonialInstance?.id}"/>
                        <g:actionSubmit class="btn btn-info" action="edit"
                                        value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                    </g:form>
                </span>
            </shiro:hasRole>
        </h1>

        <h2>${testimonialInstance?.companyName}</h2>

        <g:if test="${flash.message}">
            <div class="alert alert-info">
                ${flash.message}
            </div>
        </g:if>



        <wiki:text>
            ${testimonialInstance?.body}
        </wiki:text>



            </section>
</div>

</body>
</html>
