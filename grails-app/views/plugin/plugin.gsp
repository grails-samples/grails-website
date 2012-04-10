<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <div class="aside">
        <aside class="search">
            <input type="search" placeholder="Search a plugin" />
            <a href="#" class="zoom">Search</a>
        </aside>
        <aside class="filters">
            <h4>Popular filters</h4>
            <ul>
                <li><a href="#">Featured</a></li>
                <li><a href="#">Top Installed</a></li>
                <li><a href="#">Highest Voted</a></li>
                <li><a href="#">Recently Updated</a></li>
                <li><a href="#">Newest</a></li>
                <li class="last"><a href="#">Supported by SpringSource</a></li>
            </ul>
        </aside>
        <aside class="tags">
            <h4>Popular tags</h4>
            <ul>
                <li><a href="#">Ajax</a></li>
                <li><a href="#">Javascript</a></li>
                <li><a href="#">Functionality</a></li>
                <li><a href="#">jQuery</a></li>
                <li><a href="#">Security</a></li>
                <li><a href="#">Database</a></li>
                <li class="last"><a href="#">Testing</a></li>
            </ul>
            <ul>
                <li><a href="#">Rich client</a></li>
                <li><a href="#">Resources</a></li>
                <li><a href="#">Perfomance</a></li>
                <li><a href="#">Grails2</a></li>
                <li><a href="#">Ui</a></li>
                <li><a href="#">Sexy</a></li>
                <li class="last"><a href="#">Bootstrap2</a></li>
            </ul>
        </aside>
    </div>
    <div id="main" class="plugins">
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
                        Grails version : ${plugin.grailsVersion ?: '*'} • Current release : ${plugin.currentRelease}
                    </p>
                    <div class="right">
                        <p class="rating">
                            <span class="star-100"></span>
                            <span class="star-100"></span>
                            <span class="star-100"></span>
                            <span class="star-50"></span>
                            <span class="star-0"></span>
                            <span class="note">10</span>
                        </p>
                        <p class="used">
                            <strong>68%</strong> Used in of apps
                        </p>
                        <p class="download">
                            <a href="#" class="btn primary">Source on GitHub</a>
                        </p>
                    </div>
                </header>
                <div class="desc">
                    <div class="alert alert-info">
                        <strong>Installation :</strong><br />
                        <code>${plugin.installation.body.encodeAsHTML()}</code>
                    </div>
                    <p class="dependency"><strong>Dependency :</strong><br />
                        <code>${plugin.defaultDependencyScope} "${plugin.dependencyDeclaration.encodeAsHTML()}"</code>
                    </p>
                </div>
                <p class="buttons">
                    <g:if test="${plugin.scmUrl}">
                        <a href="${plugin.scmUrl}" class="btn blueLight source"><span class="ico"></span>Source</a>
                    </g:if>
                    <g:if test="${plugin.documentationUrl}">
                        <a href="${plugin.documentationUrl}" class="btn blueLight doc"><span class="ico"></span>Documentation</a>
                    </g:if>
                    <g:if test="${plugin.issuesUrl}">
                        <a href="${plugin.issuesUrl}" class="btn blueLight issues"><span class="ico"></span>Issues</a>
                    </g:if>
                </p>
                <div class="tabbable" id="tabs">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab1" data-toggle="tab">Description</a></li>
                        <li><a href="#tab2" data-toggle="tab">Installation</a></li>
                        <li class="disabled"><a href="#tab3">Screenshots</a></li>
                        <li class="disabled"><a href="#tab4">Faq</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab1">
                            <p>The Cloud Foundry plugin for Grails integrates Cloud Foundry's cloud deployment services to manage the running of Grails applications in the cloud from the command line.</p>
                            <h3>Documentation</h3>
                            <p>Please see the user guide for official documentation.</p>
                            <p>(See also: “One-step deployment with Grails and Cloud Foundry,” a post by Peter Ledbrook on the SpringSource blog.)</p>
                        </div>
                        <div class="tab-pane" id="tab2">
                            <p>
                                Execute (<a href="#">copy</a>):<br />
                                <code>grails install-plugin cloud-foundry</code>
                            </p>
                        </div>
                        <div class="tab-pane" id="tab3">
                            <p>No screenshot.</p>
                        </div>
                        <div class="tab-pane" id="tab4">
                            <p>No FAQ.</p>
                        </div>
                    </div>
                </div>
            </article>
        </section>
    </div>
</div>

</body>
</html>