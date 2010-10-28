<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Grails - <g:layoutTitle default="The search is over."></g:layoutTitle></title>

    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <wiki:robots/>
    <meta name="Description" content="Grails is a high-productivity web framework based on the Groovy language that embraces the coding by convention paradigm, but is designed specifically for the Java platform.">	

    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon">

    <link rel="stylesheet" href="${resource(dir: 'css/new', file: 'master.css')}" type="text/css" />
    <%-- Page-specific CSS goes in here --%>
    <g:pageProperty name="page.pageCss" />
    <!--[if IE]><link rel="stylesheet" href="${resource(dir: 'css/new', file: 'ie.css')}"/><![endif]-->

    <g:layoutHead />
</head>
<body>
    
    <div align="center">
        <g:render template="/content/logos" />
        <div class="mainMenuBarWrapper">
            <g:render template="/content/mainMenuBar" />
        </div>
    </div>

    <div id="contentWrapper" align="center">
        <div id="contentInnerWrapper">
            <div id="contentCenter" >
                <g:layoutBody />
            </div>
        </div>
    </div>

    <div id="btmSectionGraphicsWrapper">
        <div id="mountainLeft"></div>
        <div id="knight"></div>
        <div id="mountainRight"></div>
        <div id="castle"></div>
    </div>

    <g:render template="/content/footer" />

    <%-- Google Analytics --%>
    <g:render template="/content/analytics" />
</body>
</html>
