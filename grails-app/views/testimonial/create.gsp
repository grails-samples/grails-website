<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="master"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>

    <r:require modules="codeMirror, fancyBox" />
</head>

<body>


<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${testimonialInstance}">
    <div class="alert alert-error">
        <g:renderErrors bean="${testimonialInstance}" as="list"/>
    </div>
</g:hasErrors>


<div id="content" class="content-aside" role="main">
    <g:render template="/community/sideNav"/>

    <section id="main">
        <article>
            <h2>Add a Testimonial</h2>

            <g:form action="save" class="form-horizontal" >


            <fieldset>
               <g:render template="form" model="model" />

                <div class="form-actions">
                    <g:submitButton name="create" class="btn btn-primary"
                                    value="Submit for Approval"/>

                <a class="btn preview">Preview</a>


            </div>
            </fieldset>
            </g:form>

        </article>
    </section>
</div>

</body>
</html>
