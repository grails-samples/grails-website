<g:setProvider library="yui"/>
<g:if test="${hasError}">
<div class="error">
Invalid versions string provided.
</div>
</g:if>
Displayed versions (in order): <strong>${ versions*.baseVersion.join(', ') }</strong> <g:remoteLink controller="download" action="adminEditVersionOrder" method="get" update="versionDisplayOrder">Change</g:remoteLink>
