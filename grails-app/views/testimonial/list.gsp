<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta name="layout" content="masterv2"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

    <div class="content-title">
        <h1>Testimonials</h1>
    </div>

    <section id="main" class="items testimonials">

        <flash:message flash="${flash}"/>

        <div class="alert alert-block">
            <p>
                <g:message code="testimonial.list.submit.description"/>
                <g:link action="create"><g:message code="testimonial.list.submit.button"/></g:link>.
            </p>

        </div>

        <g:each in="${featuredList}" var="testimonial">
            <article class="item">
                <g:render template="featuredTestimonial" model="['testimonial': testimonial]"/>
            </article>
        </g:each>

        <g:each in="${nonFeaturedList}" var="testimonial" status="idx">
        	<g:if test="${idx % 3 == 0}"><g:if test="${idx > 0}"></div></g:if><div class="row"></g:if>
            <article class="item col-md-4">
                <g:render template="testimonial" model="['testimonial': testimonial]"/>
            </article>
        </g:each>
        </div>        
        <div class="pager">
            <div class="pagination">
                <g:paginate total="${nonFeaturedTotal}" max="12"/>
            </div>
        </div>
    </section>

</body>
</html>
