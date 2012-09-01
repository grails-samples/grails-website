
<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="master"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <g:render template="/community/sideNav"/>


    <section id="main">
     <article>
         <div class="alert alert-block margin-bottom-15">
             <p><g:message code="testimonial.list.submit.description" /></p>
             <p>
                 <g:link action="create" class="btn"><g:message code="testimonial.list.submit.button" /></g:link>
             </p>
         </div>
         <flash:message flash="${flash}" />
    <h2>Testimonials</h2>

   <g:render template="testimonial" model="model" />

     </article>
</section>



</div>


</body>
</html>
