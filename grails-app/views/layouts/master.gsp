<%@page import="grails.util.Environment" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="Grails - The search is over."/></title>
    <meta name="description" content="">
    <meta name="author" content="Grails community">
    <meta name="viewport" content="width=device-width">

    <g:if test="${Environment.current != Environment.PRODUCTION}">
        <script>
            var disqus_developer = 1;
        </script>
    </g:if>

    <link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.ico')}" type="image/x-icon">

    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Magra:400' rel='stylesheet' type='text/css'>
    <r:require modules="master"/>
    <r:layoutResources/>
    <g:layoutHead/>
</head>

<body>

<div id="page">
    <header id="header" role="banner">
        <h1 id="logo"><a href="/">Grails 2.0</a></h1>
        <ul class="topLinks nav pills">
            <shiro:isLoggedIn>
                <li><a href="/logout">Logout</a></li>
                <li class="spacing">&nbsp;</li>
                <li class="current-user">${user?.email}</li>
            </shiro:isLoggedIn>
            <shiro:isNotLoggedIn>
                <li><a href="/login" class="login">Login</a></li>
                <li class="spacing">&nbsp;</li>
                <li><a href="/register">Create Account</a></li>
            </shiro:isNotLoggedIn>
        </ul>
        <a href="http://www.springsource.org/" class="springSource">SpringSource</a>

        <div id="quickSearch" role="search">
           <g:form name="globalSearchForm" controller="content" action="search" method="get">
            <div class="inputSearch">
                <input name="q" type="search"/>
            </div>
            <input type="submit" alt="Search" title="Search the whole site" name="submit" />
            </g:form>
        </div>
    </header>

    <g:render template="/layouts/navigation" />

    <g:layoutBody/>

    <footer id="footer" role="contentinfo">
        <div class="buildWithGrails">
            Built with Grails
        </div>

        <div class="copyright">
            Copyright 2009 - 2012 SpringSource.<br/>
            All Rights Reserved.
        </div>
        <ul class="privacy">
            <li><a href="http://www.vmware.com/help/legal.html">Terms of Use</a></li>
            <li class="last"><a href="http://www.vmware.com/help/privacy.html">Privacy</a></li>
        </ul>
        <ul class="external-services">
            <li class="last"><a class="artifactory" href="http://www.jfrog.com/">Artifactory</a></li>
        </ul>
    </footer>

</div>

<r:layoutResources/>

<!-- Copyright 2009 - 2012 SpringSource -->

</body>
</html>
