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

    <div id="main" class="websites">
        <section>
            <article>
                <flash:message flash="${flash}" />
                <a href="/tutorials/add">Add Tutorial</a>
            </article>
            <g:render template="tutorial" collection="${tutorialInstanceList}" var="tutorialInstance"/>
        </section>
    </div>
</div>

</body>
</html>