<head>
    <title><g:message code="wiki.edit.title" args="${[content?.title]}"/></title>
    <meta content="master" name="layout"/>
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
    </section>
</div>

</body>
</html>




