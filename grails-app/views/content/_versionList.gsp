<g:setProvider library="yui"/>

<g:set var="updateElement" value="${update ?: 'contentPane'}" />
<g:if test="${message}">
    <div id="message" class="message">${message}</div>
</g:if>
<ul>

<g:each in="${versions}" var="v" status="i">
    <li>
        <g:remoteLink update="${updateElement}"
                action="showWikiVersion" id="${wikiPage?.title}"
                params="[number: v, _ul: updateElement]"
                mapping="${urlMapping}"
                method="GET">
            Version ${v}</g:remoteLink> (Updated by <strong>${authors[i].login}</strong>)

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
            <g:if test="${previous}">
                <g:remoteLink update="diffPane"
                        action="diffWikiVersion"
                        id="${wikiPage?.title}"
                        params="[number:v, diff:previous, _ul: updateElement]"
                        mapping="${urlMapping}"
                        method="GET"
                        onComplete="showDiff();">Diff with previous</g:remoteLink></li>
            </g:if>
            <g:else>
                First Version
            </g:else>
        </shiro:isLoggedIn>

        <g:set var="previous" value="${v}" />
</g:each>
</ul>
<div id="diffPane">
</div>
<div id="diffOutputDiv" class="diffOutput" style="display:none;">
    
</div>
<script type="text/javascript">
    if(myYUI.get('message')!=null) {
        myYUI.fade('message', {delay:3})
    }
</script>
