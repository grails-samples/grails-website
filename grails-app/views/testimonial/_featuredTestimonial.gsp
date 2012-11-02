<h3><g:link action="show" id="${testimonial.id}">${testimonial?.title}</g:link></h3>
<p>
    <wiki:shorten wikiText="${testimonial?.body}" length="200" />
</p>

