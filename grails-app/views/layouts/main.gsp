<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if IE 9 ]>    <html class="ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html class=""> <!--<![endif]-->
<head>
    <title>Grails - <g:layoutTitle default="The search is over."></g:layoutTitle></title>

    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <wiki:robots/>
    <meta name="Description" content="Grails is a high-productivity web framework based on the Groovy language that embraces the coding by convention paradigm, but is designed specifically for the Java platform.">	

    <r:require modules="master"/>
    <r:external uri="/images/favicon.ico"/>
    <r:layoutResources/>

    <r:script>
function addJsClass() {
    var classes = document.body.className.split(" ");
	if (classes.length == 1 && classes[0] == "") {
		classes = ["js"];
	}
	else {
		classes.push("js");
	}
    document.body.className = classes.join(" ");
}
    </r:script>
    
    <%-- Page-specific CSS goes in here --%>
    <g:pageProperty name="page.pageCss" />
    <g:layoutHead />
</head>
<body onload="addJsClass();">
    
    <div id="header" align="center">
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
    
    <r:layoutResources/>
</body>
</html>
