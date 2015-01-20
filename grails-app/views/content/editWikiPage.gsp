<head>
    <title><g:message code="wiki.edit.title" args="${[wikiPage?.title]}"/></title>
    <meta content="master" name="layout"/>
    <asset:stylesheet src="codeMirror"/>
    <asset:javascript src="codeMirror"/>
    <asset:stylesheet src="fancyBox"/>
    <asset:javascript src="fancyBox"/>
    <asset:javascript src="imageUpload"/>
    <asset:javascript src="wikiEditor"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <section id="main">
        <article>
            <h2><g:message code="wiki.edit.title" args="${[wikiPage?.title]}"/></h2>
            <p><g:message code="wiki.edit.description"/></p>

            <wiki:uploadImages  />
            <g:render template="wikiForm"/>
        </article>
    </section>
</div>

</body>
</html>
