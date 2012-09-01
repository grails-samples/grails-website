
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
    <g:message code="default.show.label" args="[entityName]"/>
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
            <g:message code="testimonial.id.label" default="Id"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "id")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.title.label" default="Title"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "title")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.status.label" default="Status"/>
        </td>

        
            <td class="show-value">${testimonialInstance?.status?.encodeAsHTML()}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.submittedBy.label" default="Submitted By"/>
        </td>

        
        <td class="show-value">
                <g:link controller="user" action="show"
                        id="${testimonialInstance?.submittedBy?.id}">${testimonialInstance?.submittedBy?.encodeAsHTML()}
                </g:link>
            </td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.companyName.label" default="Company Name"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "companyName")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.content.label" default="Content"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "content")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.dateCreated.label" default="Date Created"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "dateCreated")}</td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.featured.label" default="Featured"/>
        </td>

        
            <td class="show-value"><g:formatBoolean boolean="${testimonialInstance?.featured}"/></td>
        
    </tr>
    
    <tr>
        <td class="show-label">
            <g:message code="testimonial.lastUpdated.label" default="Last Updated"/>
        </td>

        
        <td class="show-value">${fieldValue(bean: testimonialInstance, field: "lastUpdated")}</td>
        
    </tr>
    
    </tbody>
</table>
            </section>
</div>

</body>
</html>
