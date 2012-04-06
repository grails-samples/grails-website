<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title>Grails - The search is over.</title>
    <meta name="description" content="">
    <meta name="author" content="Grails community">
    <meta name="viewport" content="width=device-width">

    <link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.ico')}" type="image/x-icon">

    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Magra:400' rel='stylesheet' type='text/css'>
    <link href="${resource(dir: 'css', file: 'style.css')}" rel="stylesheet" type="text/css">
    <script src="${resource(dir: 'js/libs', file: 'jquery-1.7.1.min.js')}"></script>
    <script src="${resource(dir: 'js/libs', file: 'modernizr-2.5.3-respond-1.1.0.min.js')}"></script>
    <g:layoutHead/>
</head>

<body>

<div id="page">
    <header id="header" role="banner">
        <h1 id="logo"><a href="index.html">Grails 2.0</a></h1>
        <ul class="topLinks nav pills">
            <li class="dropdown last" id="menu1">
                <a class="dropdown-toggle" data-toggle="dropdown">
                    <img src="img/lang/uk.png"/>
                    English
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="#">
                            <img src="img/lang/fr.png"/>
                            French
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <img src="img/lang/fr.png"/>
                            Spanish
                        </a>
                    </li>
                </ul>
            </li>
            <li class="spacing">&nbsp;</li>
            <li class="active"><a href="login.html">My account</a></li>
        </ul>
        <a href="http://www.springsource.org/" class="springSource">SpringSource</a>

        <div id="quickSearch" role="search">
            <div class="inputSearch hide">
                <input type="search"/>
            </div>
            <a href="#" class="submitSearch">Search</a>
        </div>
    </header>

    <nav id="navigation" role="navigation">
        <ul>
            <li <g:if test="${controllerName == 'content'}">class="active"</g:if>><a href="/">Homepage</a></li>
            <li><a href="http://www.springsource.com">Products, Services &amp; Training</a></li>
            <li <g:if test="${controllerName == 'learn'}">class="active"</g:if>><a href="/learn">Learn</a></li>
            <li><a href="community.html">Community</a></li>
            <li <g:if test="${controllerName == 'download'}">class="active"</g:if>><a href="/download">Downloads</a></li>
            <li class="last"><a href="plugins.html">Plugins</a></li>
        </ul>
    </nav>

    <g:layoutBody/>

    <footer id="footer" role="contentinfo">
        <div class="buildWithGrails">
            Build with Grails
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

<script src="${resource(dir: 'js', file: 'script-ck.js')}"></script>
<script>
    var _state = 0, _banners, _containerBanner, _time = 5;

    function tick() {
        if (_state == (_banners.length - 1))
            _state = 0;
        else
            _state++;
        _containerBanner.animate({'left':-_state * 980}, 500);
        setTimeout('tick()', _time * 1000);
    }

    $(function () {
        _banners = $('div.banner').detach();
        _containerBanner = $('<div />')
                .css('position', 'absolute')
                .css('left', 0).css('top', 0)
                .width(980 * _banners.length);
        _containerBanner.append(_banners);
        $('div#banner').append(_containerBanner);

        setTimeout("tick()", _time * 1000);
    });
</script>


<!-- Copyright 2009 - 2012 SpringSource -->

</body>
</html>