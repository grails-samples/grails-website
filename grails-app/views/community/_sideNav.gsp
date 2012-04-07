<section class="aside">
    <aside id="community">
        <h3><img src="${resource(dir: "img/aside", file: "community.png")}" alt="" title=""/>Community</h3>
        <ul>
            <li<g:if test="${actionName == 'websites'}"> class="active"</g:if>>
                <a href="/community/websites">Sites using Grails</a></li>
            <li<g:if test="${actionName == 'testimonials'}"> class="active"</g:if>>
                <a href="/community/testimonials">Testimonials</a></li>
            <li<g:if test="${actionName == 'contribute'}"> class="active"</g:if>>
                <a href="/community/contribute">Contribute</a></li>
            <li><a href="http://jira.grails.org/browse/GRAILS">Issue Tracker</a></li>
            <li><a href="http://github.com/grails/grails-core/">Source code</a></li>
            <li><a href="/plugins">Plugins</a></li>
            <li<g:if test="${actionName == 'mailingList'}"> class="active"</g:if>>
                <a href="/community/mailingList">Mailing Lists</a></li>
            <li class="last"><a href="http://grails.1312388.n4.nabble.com/Grails-user-f1312389.html">Nabble Forums</a></li>
        </ul>
    </aside>
</section>