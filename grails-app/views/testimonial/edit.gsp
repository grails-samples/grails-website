<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta name="layout" content="masterv2"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <asset:stylesheet src="codeMirror.css"/>
    <asset:stylesheet src="fancyBox.css"/>
    <asset:javascript src="fancyBox.js"/>
    <asset:javascript src="imageUpload.js"/>
    <asset:javascript src="wikiEditor.js"/>
</head>

<body>


<flash:message flash="${flash}" bean="${testimonialInstance}" />

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

                <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
                    <g:submitButton name="update" class="btn btn-primary"
                                    value="Update"/>

                <a class="btn preview">Preview</a>


            </div></div>
            </fieldset>
            </g:form>

        </article>
    </section>
</div>



</body>
</html>
