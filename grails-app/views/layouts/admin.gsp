<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Grails Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="${resource(dir: 'css', file: 'bootstrap.min.css')}" rel="stylesheet">
    <style type="text/css">
    body {
        padding-top: 40px;
    }
    </style>
    <link href="${resource(dir: 'css', file: 'bootstrap-responsive.min.css')}" rel="stylesheet">

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
                    %{--<li><g:link controller="news" action="list">News</g:link></li>--}%
                    <li <g:if test="${controllerName == 'user'}">class="active"</g:if>>
                        <g:link controller="user" action="list">Users</g:link></li>
                    %{--<li><g:link controller="userInfo" action="list">User Info</g:link></li>--}%
                    <li <g:if test="${controllerName == 'wikiPage'}">class="active"</g:if>>
                        <g:link controller="wikiPage" action="list">Wiki Pages</g:link></li>
                    <li <g:if test="${controllerName == 'download'}">class="active"</g:if>>
                        <g:link controller="download" action="list">Downloads</g:link></li>
                    <li <g:if test="${controllerName == 'commentLink'}">class="active"</g:if>>
                        <g:link controller="commentLink" action="list">Comments</g:link></li>
                    <li <g:if test="${controllerName == 'tag'}">class="active"</g:if>>
                        <g:link controller="tag" action="list">Tags</g:link></li>
                    <li <g:if test="${controllerName == 'ratingLink'}">class="active"</g:if>>
                        <g:link controller="ratingLink" action="list">Ratings</g:link></li>
                    <li <g:if test="${controllerName == 'blogEntry'}">class="active"</g:if>>
                        <g:link controller="blogEntry" action="adminList">Blog Entries</g:link></li>
                    <li <g:if test="${controllerName == 'pluginAdmin'}">class="active"</g:if>>
                        <g:link controller="pluginAdmin" action="list">Plugins</g:link></li>
                    <plugin:isAvailable name="jobs">
                        <li <g:if test="${controllerName == 'jobAdmin'}">class="active"</g:if>>
                            <g:link controller="jobAdmin" action="list">Jobs</g:link></li>
                    </plugin:isAvailable>
                </ul>

                <p class="navbar-text pull-right">
                    Logged in as <shiro:principal/> | <g:link controller="user" action="logout">logout</g:link>
                </p>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>

<div class="container">
    <g:layoutBody/>
</div>

</body>
</html>