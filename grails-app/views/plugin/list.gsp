<head>
    <meta content="masterv2" name="layout"/>
    <title>Grails Plugins</title>
    <asset:stylesheet src="plugin"/>
    <asset:javascript src="plugin"/>
    <g:render template="tagSetup" model="[allTags:allTags]" />


</head>

<body>

<div id="content" class="content-aside" role="main">
    <div class="content-aside-title">
        <h1 id="logo">Plugins <small>You can find out about all the publicly available Grails plugins.</small></h1>
        <g:render template="searchBar" />
        <g:render template="loginBar" />
    </div>
    <g:render template="sideNav" bean="${tags}" />
    <!-- IMPORTANT: DO NOT delete the link below, it is commented out, but used for plugin resolution -->
    <!-- <a href="http://plugins.grails.org/.plugin-meta">.plugin-meta</a> -->
    <!-- <a href="https://grails.org/plugins/.plugin-meta">.plugin-meta</a> -->

    <section id="main" class="plugins">

        <g:if test="${home}">
            <div class="alert alert-block">
                <p>
                    Interested in your own plugin appearing here? Take a look at
                    the <a href="/Creating+Plugins" target="_blank">Publishing Plugins Guide</a>
                    and when you're ready, <a href="/plugins/submitPlugin">please fill out this form</a>.
                    <strong>NEW:</strong> Get involved by voicing your opinion on new plugins waiting to be approved in the
                    <a href="/plugins/pending">Pending Plugin</a> portal.
                </p>
            </div>
        </g:if>

        <g:if test="${query}">
            <g:if test="${searchResult.results}">
            <p>
                Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
                result${searchResult.results.size() > 1 ? 's' : ''} for <strong>${query}</strong>
            </p>
            </g:if>
            <g:elseif test="${parseException}">
                <p>Your query - <strong>${query}</strong> - is not valid.</p>
            </g:elseif>
            <g:elseif test="${clauseException}">
                <p>Your query - <strong>${query}</strong> - cannot be handled, sorry. Be more restrictive with your wildcards, like '*'.
                </p>
            </g:elseif>
            <g:else>
                <p>Nothing matched your query - <strong>${query}</strong></p>
            </g:else>
        </g:if>

        <g:each in="${plugins}" var="plugin">
            <article class="plugin">
                <header>
                    <h3>
                        <g:link uri="/plugin/${plugin.name}">
                            ${plugin?.title?.encodeAsHTML()}
                        </g:link>
                    </h3>
                    <g:if test="${plugin.official}">
                        <p><small>Plugin Collection</small></p>
                    </g:if>

                    <ul class="meta">
                        <li>
                            <g:render template="pluginTags" model="[plugin:plugin]" />
                        </li>
                        <li>
                            Latest: <strong>${plugin.currentRelease}</strong>
                        </li>
                        <li>Last Updated: <strong><joda:format pattern="dd MMMMM yyyy" value="${plugin.lastReleased}" /></strong></li>
                        <li>
                            Grails version: ${plugin.grailsVersion ?: '*'}
                        </li>
                    </ul>

                    <div class="right">
                        <g:render template="pluginRating" model="[plugin:plugin]" />
                        %{-- <plugin:rating averageRating="${plugin.avgRating ?: 0}" total="${plugin.ratingCount ?: 0}" /> --}%
                        <g:if test="${plugin.usage>0}">
                            <p class="used">
                                <strong><g:formatNumber number="${plugin.usage}" type="percent"/></strong> of Grails users
                            </p>
                        </g:if>
                    </div>
                </header>

                <div class="desc">
                    <p><wiki:shorten key="${'plugin_' + plugin.id}" wikiText="${plugin.summary}" length="500"/> <g:link uri="/plugin/${plugin.name}">Read more</g:link></p>
                    <p class="code"><strong>Dependency :</strong><br/>
                        <code>${plugin.defaultDependencyScope} "${plugin.dependencyDeclaration.encodeAsHTML()}"</code>
                    </p>
                </div>
                <tmpl:pluginButtons plugin="${plugin}" />
            </article>
            <!--<span class="status status-new">New</span>-->
        </g:each>

        <section class="pager">
            <%
                otherParams = otherParams ?: [:]
                if (activeTag) otherParams.tag = activeTag
                if (activeFilter) otherParams.filter = activeFilter
            %>
            <g:if test="${actionName == 'search'}">
                <g:paginate total="${pluginCount}" max="10" params="${otherParams}" />
            </g:if>
            <g:else>
                <g:paginate mapping="pluginList" total="${pluginCount}" max="10" params="${otherParams}" />
            </g:else>
        </section>
        <asset:script>
            tagsInitialized = true
        </asset:script>
    </section>
</div>

</body>
