<g:each in="${testimonialInstanceList}" var="testimonial" status="idx">
    <article class="col${(idx % 3) + 1}">

        <h3><a href="#"><wiki:shorten text="${testimonial?.title?.encodeAsHTML()}" length="20" />
            <small><wiki:shorten text="${testimonial?.content?.encodeAsHTML()}" length="50" /></small>
        </a>
        </h3>

    </article>
</g:each>

<div class="pagination">
    <g:paginate total="${testimonialInstanceTotal}"/>
</div>
