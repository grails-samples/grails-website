<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="plugin"/>
</head>

<body>

<div id="content" class="content-aside-2" role="main">

    <g:render template="sideNav" bean="${tags}" />

    <div id="main" class="plugins">

        <g:render template="searchBar" />

        <g:if test="${query}">
            <g:if test="${searchResult}">
            <p>
                Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
                results for <strong>${query}</strong>
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
            <section class="plugin">
                <article>
                    <header>
                        <h3>
                            <g:link uri="/plugin/${plugin.name}">
                                ${plugin?.title?.encodeAsHTML()}
                                <g:if test="${plugin.official}">
                                    <small>supported by SpringSource</small>
                                </g:if>
                            </g:link>
                        </h3>

                        <p class="meta">
                            Tags :
                            <g:if test="${plugin.tags.size() > 0}">
                                <g:each in="${plugin.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="/plugins/tag/${tag}">${tag}</a></g:each>
                            </g:if>
                            <g:else>
                                /
                            </g:else>
                            <br/>
                            Latest : <strong>${plugin.currentRelease}</strong> • Grails version : ${plugin.grailsVersion ?: '*'}
                        </p>

                        <div class="right">
                            <plugin:rating averageRating="${plugin.avgRating ?: 0}" total="${plugin.ratingCount ?: 0}" />
                            <g:if test="${plugin.usage>0}">
                                <p class="used">
                                    <strong><g:formatNumber number="${plugin.usage}" type="percent"/></strong> of Grails users
                                </p>
                            </g:if>
                        </div>
                    </header>

                    <div class="desc">
                        <p><wiki:shorten key="${'plugin_' + plugin.id}" wikiText="${plugin.summary}" length="500"/> <g:link uri="/plugin/${plugin.name}">Read more</g:link></p>
                        <p class="dependency"><strong>Dependency :</strong><br/>
                            <code>${plugin.defaultDependencyScope} "${plugin.dependencyDeclaration.encodeAsHTML()}"</code>
                        </p>
                    </div>

                    <p class="buttons">
                        <g:if test="${plugin.documentationUrl}">
                            <a href="${plugin.documentationUrl}" class="btn blueLight doc"><span class="ico"></span>Documentation</a>
                        </g:if>
                        <g:if test="${plugin.scmUrl}">
                            <a href="${plugin.scmUrl}" class="btn blueLight source"><span class="ico"></span>Source</a>
                        </g:if>
                        <g:if test="${plugin.issuesUrl}">
                            <a href="${plugin.issuesUrl}" class="btn blueLight issues"><span class="ico"></span>Issues</a>
                        </g:if>
                    </p>
                </article>
                <!--<span class="status status-new">New</span>-->
            </section>
        </g:each>

        <section class="pager">
            <%
                otherParams = otherParams ?: [:]
                if (activeTag) otherParams.tag = activeTag
                if (activeFilter) otherParams.filter = activeFilter
            %>
            <g:paginate total="${pluginCount}" max="10" params="${otherParams}" />
        </section>
    </div>
</div>

</body>
</html>
