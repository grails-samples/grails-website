<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Grails Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <asset:stylesheet src="admin"/>
    <asset:javascript src="admin"/>

    <link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.ico')}" type="image/x-icon">
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <g:layoutHead/>
</head>

<body>

    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <g:link controller="admin" class="navbar-brand">Grails Admin</g:link>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
        	<ul class="nav navbar-nav">
                    <li <g:if test="${controllerName == 'user'}">class="active"</g:if>>
                        <g:link controller="user" action="list">Users</g:link></li>

                    <li class="dropdown
                        <g:if test="${['webSiteAdmin', 'tutorialAdmin', 'wikiPage'].contains(controllerName)}"> active</g:if>
                    ">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Content <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><g:link controller="newsItemAdmin" action="list">News</g:link></li>
                            <li><g:link controller="wikiPageAdmin" action="list">Wiki Pages</g:link></li>
                            <li><g:link controller="webSiteAdmin" action="list">Web Sites</g:link></li>
                            <li><g:link controller="tutorialAdmin" action="list">Tutorials</g:link></li>
                            <li><g:link controller="screencastAdmin" action="list">Screencasts</g:link></li>
                            <li><g:link controller="videoHostAdmin" action="list">Video Hosts</g:link></li>
                            <li><g:link controller="contentPendingApproval" action="list">Approval Queue</g:link></li>
                        </ul>
                    </li>

                    <li class="dropdown
                        <g:if test="${['pluginAdmin', 'pluginPendingApproval'].contains(controllerName)}"> active</g:if>
                    ">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Plugins <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><g:link controller="pluginAdmin" action="list">Plugin List</g:link></li>
                            <li><g:link controller="pendingRelease" action="list">Pending Releases</g:link></li>
                            <li><g:link controller="pluginPendingApproval" action="list">Approval Queue</g:link></li>
                        </ul>
                    </li>
                    <li <g:if test="${controllerName == 'ratingLink'}">class="active"</g:if>>
                        <g:link controller="ratingLink" action="list">Ratings</g:link></li>

                    <plugin:isAvailable name="jobs">
                        <li <g:if test="${controllerName == 'jobAdmin'}">class="active"</g:if>>
                            <g:link controller="jobAdmin" action="list">Jobs</g:link></li>
                    </plugin:isAvailable>

                    <li><g:link uri="/">Back to site</g:link></li>
                </ul>

                <p class="nav navbar-text navbar-right">
                    Logged in as ${user?.login} | <g:link controller="user" action="logout">logout</g:link>
                </p>        
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
<div class="container">
    <g:layoutBody/>
</div>

<asset:deferredScripts/>

<script type="text/javascript">
    $(function() {
        $('.dropdown-toggle').dropdown();
    });
</script>

</body>
</html>
