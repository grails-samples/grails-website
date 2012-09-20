<nav id="navigation" role="navigation">
    <ul>
        <li ${controllerName == 'content' && actionName == 'homePage' ? ' class="active"' : ''}>
            <g:link uri="/"><g:message code="layout.nav.home"/></g:link>
        </li>
        <li class="dropdown learn${controllerName == 'learn' ? ' active' : ''}">
            <a class="dropdown-toggle" href="#" data-toggle="dropdown">
                <g:message code="layout.nav.learn"/>
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><g:link uri="/learn"><g:message code="layout.nav.learn.getstarted"/></g:link></li>
                <li><g:link uri="/Documentation"><g:message code="layout.nav.learn.documentation"/></g:link></li>
            </ul>

        </li>
        <li class="dropdown services-springSource">
            <a class="dropdown-toggle" href="#" data-toggle="dropdown">
                <g:message code="layout.nav.services"/>
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li>
                    <g:link uri="/products/ggts">
                        <g:message code="layout.nav.products.ggts"/>
                    </g:link>
                </li>
                <li>
                    <g:link url="http://www.vmware.com/products/application-platform/vfabric.html">
                        <g:message code="layout.nav.services.products"/>
                    </g:link>
                </li>
                <%-- Re-enable once we have a URL for support
                <li>
                    <g:link url=""><g:message code="layout.nav.services.support"/></g:link>
                </li>
                --%>
                <li>
                    <g:link url="http://www.springsource.org/consulting">
                        <g:message code="layout.nav.services.consulting"/>
                    </g:link>
                </li>
                <li>
                    <g:link url="http://mylearn.vmware.com/mgrreg/courses.cfm?ui=www_edu&a=one&id_subject=18353">
                        <g:message code="layout.nav.services.training"/>
                    </g:link>
                </li>
                <li>
                    <g:link url="http://www.springsource.org">
                        <g:message code="layout.nav.services.about"/>
                    </g:link>
                </li>
            </ul>
        </li>
        <li${controllerName == 'community' ? ' class="active"' : ''}>
            <g:link controller="community" action="index"><g:message code="layout.nav.community"/></g:link>
        </li>
        <li${controllerName == 'download' ? ' class="active"' : ''}>
            <g:link controller="download" action="index"><g:message code="layout.nav.downloads"/></g:link>
        </li>
        <li${controllerName == 'plugin' ? ' class="active"' : ''}>
            <g:link controller="plugin" action="list"><g:message code="layout.nav.plugins"/></g:link>
        </li>
        <shiro:hasRole name="Administrator">
        <li><g:link uri="/admin/"><g:message code="layout.nav.admin"/></g:link></li>
        </shiro:hasRole>
    </ul>
</nav>
