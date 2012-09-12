<g:set var="updateElement" value="${update ?: 'contentPane'}" />
<g:if test="${message}">
    <div id="message" class="alert-success">${message}</div>
</g:if>
<ul>

<g:each in="${versions}" var="v" status="i">
    <li>
        <g:link controller="content"
                mapping="${urlMapping}"
                action="showWikiVersion" id="${wikiPage?.title}" params="[number:v]">
            Version ${v}</g:link> (Updated by <strong>${authors[i].login}</strong>)

        <shiro:isLoggedIn>
            <g:if test="${v != wikiPage.version}">
                <shiro:hasRole name="Administrator">
                    <g:remoteLink update="versions"
                            action="rollbackWikiVersion"
                            id="${wikiPage?.title}"
                            params="[number:v, _ul: updateElement]"
                            mapping="${urlMapping}">
                        Rollback to here
                    </g:remoteLink>
                </shiro:hasRole>
            </g:if>
            <g:else>Latest Version</g:else>
            |
            <g:if test="${previous != null}">
                <g:link 
                        action="diffWikiVersion"
                        params="[id:wikiPage?.title,number:v, diff:previous, _ul: updateElement]"
                        mapping="${urlMapping}"
                        method="GET">Diff with previous</g:link>
            </g:if>
            <g:else>
                First Version
            </g:else>
        </shiro:isLoggedIn>
    </li>
    <g:set var="previous" value="${v}" />
</g:each>
</ul>
