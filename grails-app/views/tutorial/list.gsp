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
    <div class="content-title">
        <h1>Tutorials <small>Share & learn</small></h1>
        <g:render template="/common/searchBar" model="[type:'tutorial']" />
    </div>
    <section id="main" class="items">
        <article>
            <div class="alert alert-block">
                <p>
                    <g:message code="tutorial.list.submit.description" />
                    <g:link uri="/tutorials/add"><g:message code="tutorial.list.submit.button" />.</g:link>
                </p>
            </div>
            <flash:message flash="${flash}" />

            <g:render template="tutorial" collection="${tutorialInstanceList}" var="tutorialInstance"/>

            <g:if test="${tutorialCount}">
                <section class="pager">
                    <g:paginate total="${tutorialCount}" max="10" />
                </section>

            </g:if>
        </article>
    </section>
</div>

</body>
</html>