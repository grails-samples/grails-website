<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="download"/>
</head>

<body>

<div id="content" class="content-aside-2" role="main">
    <div class="aside">
        <aside id="github" class="github">
            <div><a href="https://github.com/grails/grails-core" target="_blank">Fork me on GitHub</a></div>
        </aside>
        <aside class="hudson">
            <div>
                <h3>Grails Development Builds</h3>
                <p>You can obtain a development build of Grails from our Hudson continuous integration server at <a href="http://hudson.grails.org" target="_blank">hudson.grails.org</a></p>
            </div>
        </aside>
    </div>
    <div id="main">
        <g:render template="latestRelease" bean="${latestDownload}" var="latestDownload" />
        <g:render template="previousReleases" model="${[stableDownloads: stableDownloads, betaDownloads: betaDownloads]}" />
        <g:render template="pluginShortList" model="${[pluginList: pluginList]}" />
    </div>
</div>

</body>
</html>