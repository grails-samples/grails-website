<%@ page import="org.grails.wiki.WikiPage" %>
<head>
    <title><g:message code="wiki.create.title" args="${[wikiPage?.title]}"/></title>
    <meta content="master" name="layout"/>
    <r:require modules="content, codeMirror, fancyBox"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <section id="main">
        <article>
            <h2><g:message code="wiki.create.title" args="${[wikiPage?.title]}"/></h2>

            <p><g:message code="wiki.create.description"/></p>

            <g:render template="wikiForm"/>
        </article>
    </section>
</div>

</body>
