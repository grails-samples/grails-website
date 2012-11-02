
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

        <flash:message flash="${flash}" />

         <aside class="alert alert-block">
             <p><g:message code="testimonial.list.submit.description" /></p>
             <p>
                 <g:link action="create" class="btn"><g:message code="testimonial.list.submit.button" /></g:link>
             </p>

         </aside>

         <h2>Testimonials</h2>


         <g:each in="${featuredList}" var="testimonial">
             <g:render template="featuredTestimonial" model="['testimonial': testimonial]" />
         </g:each>

         <g:each in="${nonFeaturedList}" var="testimonial" status="idx">
             <article class="col${(idx % 3) + 1}">
                <g:render template="testimonial" model="['testimonial': testimonial]" />
             </article>
         </g:each>
         <div class="pagination">
             <g:paginate total="${nonFeaturedTotal}"/>
         </div>

     </article>
</section>



</div>


</body>
</html>
