<%@ page import="org.grails.community.Testimonial" %>
<html>
<head>
    <meta name="layout" content="master"/>
    <g:set var="entityName" value="${message(code: 'testimonial.label', default: 'Testimonial')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <g:render template="/community/sideNav"/>

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
            <article class="item col${(idx % 3) + 1}">
                <g:render template="testimonial" model="['testimonial': testimonial]"/>
            </article>
        </g:each>
        <div class="pager">
            <div class="pagination">
                <g:paginate total="${nonFeaturedTotal}"/>
            </div>
        </div>
    </section>

</div>

</body>
</html>
