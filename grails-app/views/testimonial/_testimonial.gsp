<g:each in="${testimonialInstanceList}" var="testimonial" status="idx">
    <article class="col${(idx % 3) + 1}">

        <h3><g:link action="show" id="${testimonial.id}">${testimonial?.title}</g:link></h3>
        <p>
            <wiki:shorten text="${testimonial?.body?.encodeAsHTML()}" length="50" />
        </p>

    </article>
</g:each>

<div class="pagination">
    <g:paginate total="${testimonialInstanceTotal}"/>
</div>
