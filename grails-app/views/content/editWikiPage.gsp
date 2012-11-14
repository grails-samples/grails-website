<head>
    <title><g:message code="wiki.edit.title" args="${[wikiPage?.title]}"/></title>
    <meta content="master" name="layout"/>
    <r:require modules="content, codeMirror, fancyBox,imageUpload, wikiEditor"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <div id="main">
        <article>
            <h2><g:message code="wiki.edit.title" args="${[wikiPage?.title]}"/></h2>
            <p><g:message code="wiki.edit.description"/></p>

            <wiki:uploadImages  />
            <g:render template="wikiForm"/>
        </article>
    </div>
</div>

</body>
</html>
