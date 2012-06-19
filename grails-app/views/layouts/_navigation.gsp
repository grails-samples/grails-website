<nav id="navigation" role="navigation">
    <ul>
        <li ${controllerName == 'content' && actionName == 'homePage' ? ' class="active"' : ''}>
            <g:link uri="/">Homepage</g:link>
        </li>
        <li class="dropdown services-springSource">
            <a class="dropdown-toggle" href="#" data-toggle="dropdown">
                Products, Services &amp; Training
                <span class="caret"></span>
            </a>
            <div class="dropdown-menu">
                <h3 class="springSource">SpringSource</h3>
                <ul>
                    <li>
                        <g:link url="http://www.springsource.com">Support & services</g:link>
                    </li>
                    <li>
                        <g:link url="http://www.springsource.com">Groovy & Grails Consulting</g:link>
                    </li>
                    <li>
                        <g:link url="http://www.springsource.com">Training</g:link>
                    </li>
                    <li>
                        <g:link url="http://www.springsource.com">About SpringSource</g:link>
                    </li>
                </ul>
            </div>
        </li>
        <li class="dropdown learn${controllerName == 'learn' ? ' active' : ''}">
            <a class="dropdown-toggle" href="#" data-toggle="dropdown">
                Learn
                <span class="caret"></span>
            </a>
            <div class="dropdown-menu">
                <div class="col1">
                    <h3>Get Started</h3>
                    <g:render template="/shared/getStartedSideNav" model="[onlyLink: true]" />
                </div>
                <div class="col2">
                    <h3>Reference</h3>

                    <g:render template="/shared/referenceSideNav" model="[onlyLink: true]" />
                </div>
            </div>

        </li>
        <li${controllerName == 'community' ? ' class="active"' : ''}>
            <g:link controller="community" action="index">Community</g:link>
        </li>
        <li${controllerName == 'download' ? ' class="active"' : ''}>
            <g:link controller="download" action="latest">Downloads</g:link>
        </li>
        <li${controllerName == 'plugin' ? ' class="active"' : ''}>
            <g:link controller="plugin" action="list">Plugins</g:link>
        </li>
    </ul>
</nav>