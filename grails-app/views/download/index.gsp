<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
</head>

<body>

<div id="content" class="download" role="main">
    <div id="main">
        <g:render template="latestRelease" bean="${latestDownload}" var="latestDownload" />
        <g:render template="previousReleases" model="[majorVersions: majorVersions, groupedDownloads: groupedDownloads]" />

        <section id="downloadArchives" class="archives">
            <h3>Download previous release <small>Looking for older version ?</small></h3>
            <g:each var="majorVersion" in="${majorVersions}">
                <div class="release majorVersion">
                    <h4>Grails ${majorVersion} releases</h4>
                    <table class="table">
                        <g:each var="download" in="${groupedDownloads[majorVersion]}">
                            <tr>
                                <td>${download.download.softwareVersion}</td>
                                <td class="span2 align-center"><g:formatDate date="${download.download?.releaseDate?.toDate()}" format="y/MM/d" /></td>
                                <td class="span2 align-center"><a href="${download.download?.releaseNotes}">Release Notes</a></td>
                                <td class="span2 align-center"><a href="${download.binary[0].mirrors[0].urlString}">Binary</a></td>
                                <td class="span2 align-center">
                                    <g:if test="${download.source[0]?.mirrors}">
                                        <a href="${download.source[0].mirrors[0].urlString}">Source</a>
                                    </g:if><g:else><small>No source</small></g:else>
                                </td>
                                <td class="span2 align-center">
                                    <g:if test="${download.documentation[0]?.mirrors}">
                                        <a href="${download.documentation[0].mirrors[0].urlString}">Docs</a>
                                    </g:if><g:else><small>No doc</small></g:else>
                                </td>
                            </tr>
                        </g:each>
                    </table>
                </div>
            </g:each>
        </section>

        <section class="cols4">
            <div class="col-1">
                <g:img dir="../img/download" file="ubuntu.png" />
                <h3>Grails on <span class="ubuntu">Ubuntu</span></h3>
                <p class="desc">Install different versions of Grails using Ubuntu's package manager. <a href="/download/ubuntu">Find out more</a></p>
            </div>
            <div class="col-2">
                <g:img dir="../img/download" file="hudson.png" />
                <h3>Grails Development Builds</h3>
                <p class="desc">Build application and continuous integration server at <a href="http://hudson.grails.org">hudson.grails.org</a></p>
            </div>
            <div class="col-3">
                <g:img dir="../img/download" file="gvm.png" />
                <h3>Groovy enVironment Manager</h3>
                <p class="desc">Use GVM to manage Grails <br />installs and versions! <a href="http://gvmtool.net/">Find out more</a>.</p>
            </div>
            <div class="col-4">
                <g:img dir="../img/download" file="groovy.png" />
                <h3>Groovy Language</h3>
                <p class="desc">A dynamic language for <br />the Java platform. <a href="http://groovy.codehaus.org/">Find out more</a></p>
            </div>
        </section>

    </div>



</div>

<g:javascript>
    $(function(){
        $("#ubuntu>p>a").click(function(){
            $(this).text(($(this).text() == "Hide") ? "Find out more" : "Hide");
            $("#ubuntu>div").slideToggle();
            return false;
        });
    });
</g:javascript>
</body>
</html>
