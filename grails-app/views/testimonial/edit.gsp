<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="master"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>

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
            <h2>Edit a Testimonial</h2>

            <g:form action="update" class="form-horizontal" >

              <fieldset>
                  <g:hiddenField name="id" value="${testimonialInstance?.id}"/>
                  <g:hiddenField name="version" value="${testimonialInstance?.version}"/>

                  <g:render template="form" model="model" />

                <div class="form-actions">
                    <g:submitButton name="update" class="btn btn-primary"
                                    value="Update"/>

                <a class="btn preview">Preview</a>


            </div>
            </fieldset>
            </g:form>

        </article>
    </section>
</div>



</body>
</html>
