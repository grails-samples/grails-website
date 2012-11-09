<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <g:render template="/learn/sideNav"/>
    <div id="main" class="boxWhite">
        <div class="alert alert-block margin-bottom-15">
            <p><g:message code="tutorial.list.submit.description" /></p>
            <p>
                <g:link uri="/tutorials/add" class="btn"><g:message code="tutorial.list.submit.button" /></g:link>
            </p>
        </div>
        <flash:message flash="${flash}" />

        <g:render template="/common/searchBar" model="[type:'tutorial']" />
        <g:render template="tutorial" collection="${tutorialInstanceList}" var="tutorialInstance"/>
    </div>
</div>

</body>
</html>