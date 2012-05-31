<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Grails Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <r:require modules="admin"/>
    <r:external uri="/img/favicon.ico"/>
    <r:layoutResources/>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <g:layoutHead/>
</head>

<body>

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="#">Grails Admin</a>

            <div class="nav-collapse">
                <ul class="nav">
                    <li <g:if test="${controllerName == 'user'}">class="active"</g:if>>
                        <g:link controller="user" action="list">Users</g:link></li>

                    <li class="dropdown
                        <g:if test="${['downloadAdmin', 'downloadFile', 'mirror'].contains(controllerName)}"> active</g:if>
                    ">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Downloads <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><g:link controller="downloadAdmin" action="list">Downloads</g:link></li>
                            <li><g:link controller="downloadFile" action="list">Files</g:link></li>
                            <li><g:link controller="mirror" action="list">Mirrors</g:link></li>
                        </ul>
                    </li>

                    <li class="dropdown
                        <g:if test="${['webSiteAdmin', 'tutorialAdmin', 'wikiPage'].contains(controllerName)}"> active</g:if>
                    ">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Content <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><g:link controller="wikiPage" action="list">Wiki Pages</g:link></li>
                            <li><g:link controller="webSiteAdmin" action="list">Web Sites</g:link></li>
                            <li><g:link controller="tutorialAdmin" action="list">Tutorials</g:link></li>
                            <li><g:link controller="screencastAdmin" action="list">Screencasts</g:link></li>
                            <li><g:link controller="videoHostAdmin" action="list">Video Hosts</g:link></li>
                        </ul>
                    </li>

                    <li class="dropdown
                        <g:if test="${['pluginAdmin', 'pluginPendingApproval'].contains(controllerName)}"> active</g:if>
                    ">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Plugins <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><g:link controller="pluginAdmin" action="list">Plugin List</g:link></li>
                            <li><g:link controller="pluginPendingApproval" action="list">Approval Queue</g:link></li>
                        </ul>
                    </li>
                    <li <g:if test="${controllerName == 'ratingLink'}">class="active"</g:if>>
                        <g:link controller="ratingLink" action="list">Ratings</g:link></li>
                    <li <g:if test="${controllerName == 'blogEntry'}">class="active"</g:if>>
                        <g:link controller="blogEntry" action="adminList">Blog Entries</g:link></li>

                    <plugin:isAvailable name="jobs">
                        <li <g:if test="${controllerName == 'jobAdmin'}">class="active"</g:if>>
                            <g:link controller="jobAdmin" action="list">Jobs</g:link></li>
                    </plugin:isAvailable>
                </ul>

                <p class="navbar-text pull-right">
                    Logged in as ${user?.login} | <g:link controller="user" action="logout">logout</g:link>
                </p>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>

<div class="container">
    <g:layoutBody/>
</div>

<r:layoutResources/>

<script type="text/javascript">
    $(function() {
        $('.dropdown-toggle').dropdown();
    });
</script>

</body>
</html>