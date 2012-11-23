<header><h3><g:link action="show" id="${testimonial.id}">${testimonial?.title}</g:link></h3></header>
<p>
    <wiki:shorten wikiText="${testimonial?.body}" length="50" />
</p>