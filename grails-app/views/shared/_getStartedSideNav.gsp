<g:if test="${!onlyLink}">
<aside id="getStarted">
    <h3><img src="${resource(dir: 'img/aside', file: 'getStarted.png')}" alt="" title=""/>Get Started</h3>
</g:if>
    <ul>
        <li<g:if test="${actionName == 'gettingStarted'}"> class="active"</g:if>>
            <a href="/start">Start with Grails!</a></li>
        <li<g:if test="${content?.title == 'Installation'}"> class="active"</g:if>>
            <a href="/Installation">Installation</a></li>
        <li<g:if test="${actionName == 'quickStart'}"> class="active"</g:if>>
            <a href="/doc/latest/guide/gettingStarted.html#creatingAnApplication">Quick Start</a></li>
        <li<g:if test="${actionName == 'ideSetup'}"> class="active"</g:if>>
            <a href="/doc/latest/guide/gettingStarted.html#ide">IDE Setup</a></li>
        <li<g:if test="${controllerName == 'tutorial'}"> class="active"</g:if>>
            <a href="/tutorials">Tutorials</a></li>
        <li class="last<g:if test="${controllerName == 'screencast'}"> active</g:if>"><a
                href="/screencasts">Screencasts</a></li>
    </ul>
<g:if test="${!onlyLink}">
</aside>
</g:if>