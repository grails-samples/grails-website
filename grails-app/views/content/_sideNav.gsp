<section class="aside">
    <aside id="getStarted">
        <h3><img src="${resource(dir: 'img/aside', file: 'getStarted.png')}" alt="" title=""/>Get Started</h3>
        <ul>
            <li<g:if test="${actionName == 'gettingStarted'}"> class="active"</g:if>>
                <a href="/start">Start with Grails!</a></li>
            <li<g:if test="${actionName == 'installation'}"> class="active"</g:if>>
                <a href="/doc/latest/guide/gettingStarted.html#requirements">Installation</a></li>
            <li<g:if test="${actionName == 'quickStart'}"> class="active"</g:if>>
                <a href="/doc/latest/guide/gettingStarted.html#creatingAnApplication">Quick Start</a></li>
            <li<g:if test="${actionName == 'ideSetup'}"> class="active"</g:if>>
                <a href="/doc/latest/guide/gettingStarted.html#ide">IDE Setup</a></li>
            <li<g:if test="${actionName == 'tutorials'}"> class="active"</g:if>>
                <a href="/tutorials">Tutorials</a></li>
            <li class="last<g:if test="${actionName == 'screencasts'}"> active</g:if>"><a href="/screencasts">Screencasts</a></li>
        </ul>
    </aside>
    <aside id="reference">
        <h3><img src="${resource(dir: 'img/aside', file: 'reference.png')}" alt="" title=""/>Reference</h3>
        <ul>
            <li><a href="/Documentation">Documentation</a></li>
            <li><a href="/FAQ">FAQs</a></li>
            <li class="last"><a href="/Roadmap">Roadmap</a></li>
        </ul>
    </aside>
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