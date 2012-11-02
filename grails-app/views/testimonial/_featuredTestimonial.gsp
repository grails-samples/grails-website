<h3><g:link action="show" id="${testimonial.id}">${testimonial?.title}</g:link></h3>
<p>
    <wiki:shorten text="${testimonial?.body}" length="200" />
</p>

