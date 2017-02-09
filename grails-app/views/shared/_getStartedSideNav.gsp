<g:if test="${!onlyLink}">
<aside id="getStarted">
    <h3>Get Started</h3>
</g:if>
    <ul>
        <li<g:if test="${actionName == 'gettingStarted'}"> class="active"</g:if>>
            <a href="/start">
                <g:img dir="img/icons" file="grails.png" />
                Start with Grails!
            </a>
        </li>
        <li<g:if test="${content?.title == 'Installation'}"> class="active"</g:if>>
            <a href="/doc/latest/guide/gettingStarted.html#requirements">
                <g:img dir="img/icons" file="installation.png" />
                Installation
            </a>
        </li>
        <li<g:if test="${actionName == 'quickStart'}"> class="active"</g:if>>
            <a href="/doc/latest/guide/gettingStarted.html#creatingAnApplication">
                <g:img dir="img/icons" file="quickStart.png" />
                Quick Start
            </a>
        </li>
        <li<g:if test="${actionName == 'ideSetup'}"> class="active"</g:if>>
            <a href="/doc/latest/guide/gettingStarted.html#ide">
                <g:img dir="img/icons" file="idesetup.png" />
                IDE Setup
            </a>
        </li>
    </ul>
<g:if test="${!onlyLink}">
</aside>
</g:if>
