<%@ page import="org.grails.wiki.WikiPage" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Create Page</title>
    <meta content="master" name="layout"/>
    <r:require modules="content"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <div id="main">
        <article>
            <g:render template='wikiCreate' var="pageName" bean="${pageName}"/>
        </article>
    </div>

</div>
</body>
</html>
