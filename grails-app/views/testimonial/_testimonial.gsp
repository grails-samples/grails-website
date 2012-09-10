<g:each in="${testimonialInstanceList}" var="testimonial" status="idx">
    <article class="col${(idx % 3) + 1}">

        <h3><g:link action="show" id="${testimonial.id}"><wiki:shorten text="${testimonial?.title?.encodeAsHTML()}" length="20" />
        </g:link>
        <p>
            <wiki:shorten text="${testimonial?.body?.encodeAsHTML()}" length="50" />
        </p>

        </h3>

    </article>
</g:each>

<div class="pagination">
    <g:paginate total="${testimonialInstanceTotal}"/>
</div>
