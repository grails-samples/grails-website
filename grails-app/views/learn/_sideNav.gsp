<section class="aside">
    <aside id="getStarted">
        <h3><img src="${resource(dir: 'img/aside', file: 'getStarted.png')}" alt="" title=""/>Get Started</h3>
        <ul>
            <li<g:if test="${actionName == 'gettingStarted'}"> class="active"</g:if>>
                <a href="/learn">Start with Grails!</a></li>
            <li<g:if test="${actionName == 'installation'}"> class="active"</g:if>>
                <a href="/learn/installation">Installation</a></li>
            <li<g:if test="${actionName == 'quickStart'}"> class="active"</g:if>>
                <a href="/learn/quickStart">Quick Start</a></li>
            <li<g:if test="${actionName == 'ideSetup'}"> class="active"</g:if>>
                <a href="/learn/IDE_setup">IDE Setup</a></li>
            <li<g:if test="${actionName == 'tutorials'}"> class="active"</g:if>>
                <a href="/learn/tutorials">Tutorials</a></li>
            <li class="last<g:if test="${actionName == 'screencasts'}"> active</g:if>"><a href="/learn/screencasts">Screencasts</a></li>
        </ul>
    </aside>
    <aside id="reference">
        <h3><img src="${resource(dir: 'img/aside', file: 'reference.png')}" alt="" title=""/>Reference</h3>
        <ul>
            <li><a href="#">Documentation</a></li>
            <li><a href="#">FAQs</a></li>
            <li class="last"><a href="#">Roadmap</a></li>
        </ul>
    </aside>
</section>