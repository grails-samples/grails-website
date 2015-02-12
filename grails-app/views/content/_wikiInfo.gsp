<g:setProvider library="yui"/>

<g:set var="updateElement" value="${update ?: 'contentPane'}" />

<div id="infoLinks" class="wikiLinks">
    <ul class="wikiActionMenu">
        <li>
            <g:remoteLink class="actionIcon" action="editWikiPage" id="${wikiPage?.title}" update="${updateElement}" params="[_ul: updateElement]" method="GET" onLoaded="hideCommentPost()">
                <asset:image border="0" src="icon-edit.png" width="15" height="15" alt="Icon Edit" class="inlineIcon" border="0" />
                <span>Edit</span>
            </g:remoteLink>
        </li>
        <li>
            <g:remoteLink class="actionIcon" action="index" id="${wikiPage?.title}" update="${updateElement}" params="[_ul: updateElement]" method="GET" onLoaded="showCommentPost()">
                <asset:image src="icon-info.png" width="15" height="15" alt="Icon Edit" class="inlineIcon" border="0" />
                <span>View Page</span>
            </g:remoteLink>
        </li>
    </ul>
</div>
<g:if test="${!update}">
    <h1>Page: ${wikiPage?.title}</h1>
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

