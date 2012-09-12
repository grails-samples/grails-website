<head>
    <title><g:message code="wiki.edit.title" args="${[content?.title]}"/></title>
    <meta content="master" name="layout"/>
    <r:require modules="content, codeMirror, fancyBox"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <div id="main">
        <article>
            <div id="wikiLastUpdated">Last updated by ${latest?.author?.login}
                <prettytime:display date="${content.lastUpdated}"/></div>
            <g:render template="viewActions" model="[content: content]"/>
                        
            <h3>Page: ${content?.title}, Version:${content?.number}</h3>

            <div id="editPane">
                <wiki:text>
                    ${content?.body}
                </wiki:text>

            </div>
  
        </article>
    </div>
</div>

</body>
</html>




