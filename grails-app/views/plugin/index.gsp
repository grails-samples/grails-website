<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
</head>

<body>

<div id="content" class="content-aside-2" role="main">

    <g:render template="sideNav"/>

    <div id="main" class="plugins">
        <section class="search">
            <input type="search" placeholder="Search a plugin"/>
            <a href="#" class="zoom">Search</a>
        </section>

        <g:each in="${plugins[0]}" var="plugin">
            <section class="plugin">
                <article>
                    <header>
                        <h3>
                            <g:link uri="/plugins/plugin/">
                                ${plugin.title}
                                <g:if test="${plugin.official}">
                                    <small>supported by SpringSource</small>
                                </g:if>
                            </g:link>
                        </h3>

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
                                <span class="note">999</span>
                            </p>

                            <p class="used">
                                <strong>68%</strong> Used in of apps
                            </p>
                        </div>
                    </header>

                    <div class="desc">
                        <p>${plugin.description.body} <a href="#">Read more</a></p>
                        <p class="dependency"><strong>Dependency :</strong><br/>
                            <code>compile ":gemfire:1.0.0.M5"</code>
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
                </article>
                <!--<span class="status status-new">New</span>-->
            </section>
        </g:each>

        <section class="pagination">
            <ul>
                <li class="first disabled"><span>Previous</span></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><span>...</span></li>
                <li><a href="#">40</a></li>
                <li><a href="#">41</a></li>
                <li><a href="#">42</a></li>
                <li class="last"><a href="#">Next</a></li>
            </ul>
        </section>
    </div>
</div>

</body>
</html>