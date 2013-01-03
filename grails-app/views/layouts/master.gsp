<%@page import="grails.util.Environment" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"><!--<![endif]-->
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="robots" content="NOODP">
    <meta name="description" content="Grails is a high-productivity web framework based on the Groovy language that embraces the coding by convention paradigm, but is designed specifically for the Java platform.">   
    <meta name="author" content="Grails community">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><g:layoutTitle default="Grails - The search is over."/></title>

    <g:if test="${Environment.current != Environment.PRODUCTION}">
        <script>
            var disqus_developer = 1;
        </script>
    </g:if>

    <link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.ico')}" type="image/x-icon">

    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${resource(dir: 'img', file: 'grails-icon-144x144.png')}">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${resource(dir: 'img', file: 'grails-icon-114x114.png')}">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${resource(dir: 'img', file: 'grails-icon-72x72.png')}">
    <link rel="apple-touch-icon-precomposed" href="${resource(dir: 'img', file: 'grails-icon-57x57.png')}">


    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Magra:400' rel='stylesheet' type='text/css'>
    <r:require modules="master"/>
    <r:layoutResources/>
    <g:if test="${Environment.developmentMode}">
        <g:external dir="js" file="less-1.3.0.min.js" />
    </g:if>
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
                <li class="current-user"><g:link uri="/profile">${user?.email}</g:link></li>
            </shiro:isLoggedIn>
            <shiro:isNotLoggedIn>
                <li><a href="/login?targetUri=${request.forwardURI}" class="login">Login</a></li>
                <li class="spacing">&nbsp;</li>
                <li><a href="/register">Create Account</a></li>
            </shiro:isNotLoggedIn>
        </ul>

        <div class="spring">
            <a href="http://www.springsource.org/" class="springSource">SpringSource</a>
        </div>

        <div id="quickSearch" role="search">
           <g:form name="globalSearchForm" controller="content" action="search" method="get">
            <div class="inputSearch">
                <input name="q" type="text" placeholder="Search on grails.org" />
            </div>
           <button type="submit" title="Search the whole site" class="btn gray">
               <i class="icon-search"></i>
           </button>
            </g:form>
        </div>
    </header>

    <g:render template="/layouts/navigation" />

    <g:layoutBody/>

    <footer id="footer" role="contentinfo">
        <div class="buildWithGrails">
            Built with Grails <g:meta name="app.grails.version" />
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
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-2728886-12']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

<!-- Copyright 2009 - 2012 SpringSource -->

</body>
</html>
