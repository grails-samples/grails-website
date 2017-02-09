<nav id="navigation" role="navigation">
    <ul>
        <li ${controllerName == 'content' && actionName == 'homePage' ? ' class="active"' : ''}>
            <g:link uri="/"><g:message code="layout.nav.home"/></g:link>
        </li>
        <li class="learn${(controllerName in ['learn', 'tutorial', 'screencast']) ? ' active' : ''}">
            <g:link uri="/learn">
                <g:message code="layout.nav.learn"/>
            </g:link>
        </li>
        <li class="dropdown services-springSource${controllerName == 'product' ? ' active' : ''}">
            <a class="dropdown-toggle" href="#" data-toggle="dropdown">
                <g:message code="layout.nav.services"/>
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li class="${controllerName == 'product' ? 'active' : ''}">
                    <g:link uri="/products/ggts">
                        <g:message code="layout.nav.products.ggts"/>
                    </g:link>
                </li>
                <li>
                    <g:link url="http://pivotal.io/oss">
                        <g:message code="layout.nav.services.products"/>
                    </g:link>
                </li>
                <%-- Re-enable once we have a URL for support
                <li>
                    <g:link url=""><g:message code="layout.nav.services.support"/></g:link>
                </li>
                --%>
                <li>
                    <g:link url="http://www.pivotal.io/contact/spring-consulting">
                        <g:message code="layout.nav.services.consulting"/>
                    </g:link>
                </li>
                <li>
                    <g:link url="http://www.pivotal.io/training">
                        <g:message code="layout.nav.services.training"/>
                    </g:link>
                </li>
                <li>
                    <g:link url="http://pivotal.io/">
                        <g:message code="layout.nav.services.about"/>
                    </g:link>
                </li>
            </ul>
        </li>
        <li${controllerName in ['community', 'newsItem'] ? ' class="active"' : ''}>
            <g:link controller="community" action="index"><g:message code="layout.nav.community"/></g:link>
        </li>
        <li${controllerName == 'plugin' ? ' class="active"' : ''}>
            <g:link mapping="pluginList"><g:message code="layout.nav.plugins"/></g:link>
        </li>
        <shiro:hasRole name="Administrator">
        <li><g:link uri="/admin/"><g:message code="layout.nav.admin"/></g:link></li>
        </shiro:hasRole>
    </ul>
</nav>
