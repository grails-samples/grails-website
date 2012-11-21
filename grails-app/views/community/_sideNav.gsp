<div class="aside">
    <aside id="community">
        <h3>Community</h3>
        <ul>
            <li<g:if test="${controllerName == 'webSite'}"> class="active"</g:if>>
                <a href="/websites">
                    <g:img dir="../img/icons" file="grails.png" />
                    Sites using Grails
                </a>
            </li>
            <li<g:if test="${controllerName == 'testimonial'}"> class="active"</g:if>>
                <a href="/testimonials">
                    <g:img dir="../img/icons" file="testimonial.png" />
                    Testimonials
                </a>
            </li>
            <li<g:if test="${actionName == 'contribute'}"> class="active"</g:if>>
                <a href="/community/contribute">
                    <g:img dir="../img/icons" file="contribute.png" />
                    Contribute
                </a>
            </li>
            <li>
                <a href="http://jira.grails.org/browse/GRAILS">
                    <g:img dir="../img/icons" file="greenhopper.png" />
                    Issue Tracker
                </a>
            </li>
            <li>
                <a href="http://github.com/grails/grails-core/">
                    <g:img dir="../img/icons" file="sourcecode.png" />
                    Source code
                </a>
            </li>
            <li>
                <a href="/plugins">
                    <g:img dir="../img/icons" file="plugin.png" />
                    Plugins
                </a>
            </li>
            <li<g:if test="${actionName == 'mailingList'}"> class="active"</g:if>>
                <a href="/community/mailingList">
                    <g:img dir="../img/icons" file="maillinglist.png" />
                    Mailing Lists
                </a>
            </li>
            <li>
                <a href="http://grails.1312388.n4.nabble.com/Grails-user-f1312389.html">
                    <g:img dir="../img/icons" file="nabble.png" />
                    Nabble Forums
                </a>
            </li>
            <li class="last <g:if test="${controllerName == 'newsItem'}">active</g:if>">
                <a href="/news">
                    <g:img dir="../img/icons" file="news.png" />
                    Lastest news
                </a>
            </li>
        </ul>
    </aside>
</div>
