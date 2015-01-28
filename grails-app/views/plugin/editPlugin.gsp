<%@ page import="org.grails.plugin.Plugin" %>
<head>
    <meta content="masterv2" name="layout"/>
    <title>Edit Plugin: ${plugin.title.encodeAsHTML()}</title>
    <asset:stylesheet src="codeMirror"/>
    <asset:javascript src="codeMirror"/>
    <asset:stylesheet src="fancyBox"/>
    <asset:javascript src="fancyBox"/>
    <asset:stylesheet src="plugin-edit"/>
    <asset:javascript src="plugin-edit"/>
    <g:render template="tagSetup" model="[allTags:allTags]" />
    <style type="text/css">
        .wiki-form  .CodeMirror {
            border: 1px solid #eee;
            width: 563px;
            height: 100%;
        }
    </style>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <section id="main" class="plugin">

        <article class="plugin">
            <header>
                <h1>
                    ${plugin?.title?.encodeAsHTML()}
                    <g:if test="${plugin.official}">
                        <small>supported by SpringSource</small>
                    </g:if>
                </h1>
                <ul class="meta">
                    <li>
                        <g:render template="pluginTags" model="[plugin:plugin, enableEdit:true]" />
                    </li>
                    <li>Current release: <strong>${plugin.currentRelease.encodeAsHTML()}</strong> • Grails version: ${plugin.grailsVersion.encodeAsHTML() ?: '*'}</li>
                    <li>Authors: <%= plugin.authors.collect { it.name.encodeAsHTML() }.join(', ') %></li>
                    <g:if test="${plugin.licenses}">
                        <li>License: <%= plugin.licenses.collect { '<a href="' + it.url.encodeAsHTML() + '">' + it.name + '</a>' }.join(',') %></li>
                    </g:if>
                    <g:if test="${plugin.organization}">
                        <li>Organization:
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
                <div class="code">
                    <strong>Dependency:</strong>
                    <pre>${plugin.defaultDependencyScope.encodeAsHTML()} "${plugin.dependencyDeclaration.encodeAsHTML()}"</pre>
                    <g:if test="${plugin.customRepositoriesDeclaration}">
                        <strong>Custom repositories:</strong>
                        <pre>${plugin.customRepositoriesDeclaration.encodeAsHTML()}</pre>
                    </g:if>
                </div>
            </div>

            <g:if test="${flash.message}">
                <div class="alert alert-info">${flash.message}</div>
            </g:if>

            <div class="documentation">
                <div class="alert alert-info"><g:message code="wiki.create.description"/></div>
                <g:form class="wiki-form content-form" name="wiki-form" url="[action: 'updatePlugin', id: plugin?.name]"
                        method="post">
                    <g:hiddenField name="id" value="${plugin?.name}"/>
                    <fieldset>
                        <h2>Summary</h2>
                        <div class="control-group ${hasErrors(bean: plugin.summary, field: 'body', 'error')}">
                            <div class="controls">
                                <g:textField name="plugin.summary" value="${plugin.summary}" />
                            </div>
                        </div>

                        <h2>Installation</h2>
                        <div class="control-group ${hasErrors(bean: plugin.installation, field: 'body', 'error')}">
                            <div class="controls">
                                <g:hiddenField name="plugin.installation.version" value="${plugin?.installation?.version}"/>
                                <g:textArea cols="30" rows="20" name="plugin.installation.body"
                                            value="${plugin?.installation.body}" class="codeEditor input-medium"/>
                            </div>
                        </div>

                        <h2>Default dependency scope</h2>
                        <div class="control-group ${hasErrors(bean: plugin.defaultDependencyScope, field: 'body', 'error')}">
                            <div class="controls">
                                <g:select name="plugin.defaultDependencyScope" from="${Plugin.DEFAULT_SCOPE_WHITE_LIST}" value="${plugin?.defaultDependencyScope}" />
                            </div>
                        </div>

                        <h2>Description</h2>
                        <div class="control-group ${hasErrors(bean: plugin.description, field: 'body', 'error')}">
                            <div class="controls">
                                <g:hiddenField name="plugin.description.version" value="${plugin?.description?.version}"/>
                                <g:textArea cols="30" rows="20" name="plugin.description.body"
                                            value="${plugin?.description.body}" class="codeEditor input-medium"/>
                            </div>
                        </div>


                          <div class="form-actions">

                            <g:submitButton name="updatePlugin" class="btn btn-primary" value="Update"/>
                            <g:link class="btn" uri="/plugin/${plugin.name}">Cancel</g:link>
                        </div>

                    </fieldset>

                </g:form>
                <asset:script>
                    $(function () {
                        $('textarea').each(function(e) {
                            var myCodeMirror = CodeMirror.fromTextArea(this, {
                                lineNumbers: true,
                                wordWrap: true,
                                lineWrapping: true,
                                gutter: true,
                                fixedGutter: true
                            });
                        })

                    });
                </asset:script>
           </div>
        </article>
        <asset:script>
            tagsInitialized = true
        </asset:script>

    </section>
</div>

</body>
