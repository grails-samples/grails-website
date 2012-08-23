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

    <g:render template="sideNav"/>

    <div id="main" class="plugins">

        <g:render template="searchBar" />

        <section class="plugin">
            <article>
                <header>
                    <h2>
                        ${plugin?.title?.encodeAsHTML()}
                        <g:if test="${plugin.official}">
                            <small>supported by SpringSource</small>
                        </g:if>
                    </h2>
                    <p class="meta">
                        Tags :
                        <g:if test="${plugin.tags.size() > 0}">
                            <g:each in="${plugin.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="#">${tag}</a></g:each>
                        </g:if>
                        <g:else>
                            /
                        </g:else>
                        <br/>
                        Current release : <strong>${plugin.currentRelease}</strong> • Grails version : ${plugin.grailsVersion ?: '*'}
                    </p>
                    <div class="right">
                        <plugin:rating averageRating="${plugin.avgRating ?: 0}" total="${plugin.ratingCount ?: 0}" />
                        <g:if test="${plugin.usage>0}">
                            <p class="used">
                                <strong><g:formatNumber number="${plugin.usage}" type="percent"/></strong> of Grails users
                            </p>
                        </g:if>
                        <%--
                        <g:if test="${plugin.isScmGitHub()}">
                            <p class="download">
                                <a href="${plugin.scmUrl}" target="_blank" class="btn primary">Source on GitHub</a>
                            </p>
                        </g:if>
                        --%>
                    </div>
                </header>
                <div class="desc">
                    <div class="alert alert-info">
                        <strong>Dependency :</strong><br />
                        <code>${plugin.defaultDependencyScope} "${plugin.dependencyDeclaration.encodeAsHTML()}"</code>
                    </div>
                    <g:if test="${plugin.customRepositoriesDeclaration}">
                    <div class="alert alert-info">
                        <strong>Custom repositories :</strong><br />
                        <pre>${plugin.customRepositoriesDeclaration.encodeAsHTML()}</pre>
                    </div>
                    </g:if>
                </div>
                <p class="buttons">
                    <g:if test="${plugin.scmUrl}">
                        <a href="${plugin.scmUrl}" target="_blank" class="btn blueLight source"><span class="ico"></span>Source</a>
                    </g:if>
                    <g:if test="${plugin.documentationUrl}">
                        <a href="${plugin.documentationUrl}" target="_blank" class="btn blueLight doc"><span class="ico"></span>Documentation</a>
                    </g:if>
                    <g:if test="${plugin.issuesUrl}">
                        <a href="${plugin.issuesUrl}" target="_blank" class="btn blueLight issues"><span class="ico"></span>Issues</a>
                    </g:if>
                </p>
                <div class="documentation">
                    <wiki:text key="${'pluginInfo_' + plugin?.name}">${plugin?.summary}</wiki:text>
                </div>
            </article>
        </section>
    </div>
</div>

</body>
</html>
