<head>
    <title><g:message code="wiki.edit.title" args="${[wikiPage?.title]}"/></title>
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
            <div id="wikiLastUpdated">Last updated by ${latest?.author?.login}
                <prettytime:display date="${wikiPage.lastUpdated}"/></div>
            <g:render template="viewActions" model="[content: wikiPage]"/>
                        
            <g:if test="${!update}">
                <h2>Page: ${wikiPage?.title}</h2>
            </g:if>
            <g:if test="${versions}">
            <p>
                <strong>First Created:</strong> ${wikiPage?.dateCreated} by <strong>${first.author.login}</strong>
            </p>
            <p>
                <strong>Last Updated:</strong> ${wikiPage?.lastUpdated} by <strong>${last.author.login}</strong>
            </p>

            <h3>Versions:</h3>
            <div id="versions">
                <g:set var="cacheKey" value="${'versionList' + wikiPage?.id}"/>
                <shiro:isLoggedIn>
                    <shiro:hasRole name="Administrator">
                        <g:set var="cacheKey" value="${cacheKey + '_admin'}"/>
                    </shiro:hasRole>
                    <shiro:lacksRole name="Administrator">
                        <g:set var="cacheKey" value="${cacheKey + '_user'}"/>
                    </shiro:lacksRole>
                </shiro:isLoggedIn>

                <cache:render key="${cacheKey}" template="/content/versionList"
                                          model="[versions: versions, authors: authors, wikiPage: wikiPage, update: updateElement]" />
            </div>
            </g:if>
            <g:else>No Versions</g:else>
  
        </article>
    </section>
</div>

</body>
</html>
