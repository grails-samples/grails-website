<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="plugin"/>
    <g:render template="tagSetup" model="[allTags:allTags]" />
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
                    <ul class="meta">
                        <li>
                            <g:render template="pluginTags" model="[plugin:plugin]" />
                        </li>
                        <li>Current release : <strong>${plugin.currentRelease.encodeAsHTML()}</strong> • Grails version : ${plugin.grailsVersion.encodeAsHTML() ?: '*'}</li>
                        <li>Authors : <%= plugin.authors.collect { it.name.encodeAsHTML() }.join(', ') %></li>
                        <g:if test="${plugin.licenses}">
                        <li>License : <%= plugin.licenses.collect { '<a href="' + it.url.encodeAsHTML() + '">' + it.name + '</a>' }.join(',') %></li>
                        </g:if>
                        <g:if test="${plugin.organization}">
                        <li>Organization : 
                            <g:if test="${plugin.organizationUrl}">
                            <a href="${plugin.organizationUrl.encodeAsHTML()}">${plugin.organization.encodeAsHTML()}</a>
                            </g:if>
                            <g:else>
                            ${plugin.organization.encodeAsHTML()}
                            </g:else>
                        </li>
                        </g:if>
                    </ul>
                    <div class="right">
                       <g:render template="pluginRating" model="[plugin:plugin]" /> 
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
                        <a href="${plugin.scmUrl.encodeAsHTML()}" target="_blank" class="btn blueLight source"><span class="ico"></span>Source</a>
                    </g:if>
                    <g:if test="${plugin.documentationUrl}">
                        <a href="${plugin.documentationUrl.encodeAsHTML()}" target="_blank" class="btn blueLight doc"><span class="ico"></span>Documentation</a>
                    </g:if>
                    <g:if test="${plugin.issuesUrl}">
                        <a href="${plugin.issuesUrl.encodeAsHTML()}" target="_blank" class="btn blueLight issues"><span class="ico"></span>Issues</a>
                    </g:if>
                </p>
                <div class="documentation">
                    <section>
                    <h2>Description</h2>
                    <wiki:text key="${'pluginInfo_' + plugin?.name}">${plugin?.summary}</wiki:text>
                    </section>
                    <g:if test="${plugin.installation?.body}">
                    <section>
                    <h2>Installation</h2>
                    <wiki:text key="${'pluginInfo_install_' + plugin.name}">${plugin.installation.body}</wiki:text>
                    </section>
                    </g:if>
                    <g:if test="${plugin.description?.body}">
                    <section>
                    <h2>More Info</h2>
                    <wiki:text key="${'pluginInfo_desc_' + plugin.name}">${plugin.description.body}</wiki:text>
                    </section>
                    </g:if>
                </div>
            </article>
        </section>
        <r:script>
            tagsInitialized = true
        </r:script>
        
    </div>
</div>

</body>
</html>
