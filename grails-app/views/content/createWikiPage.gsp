<%@ page import="org.grails.wiki.WikiPage" %>
<head>
    <title><g:message code="wiki.create.title" args="${[wikiPage?.title]}"/></title>
    <meta content="masterv2" name="layout"/>
    <asset:stylesheet src="codeMirror"/>
    <asset:javascript src="codeMirror"/>
    <asset:stylesheet src="fancyBox"/>
    <asset:javascript src="fancyBox"/>
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
