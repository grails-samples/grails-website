<g:setProvider library="yui"/>
<g:formRemote name="versionDisplayOrderForm" url="[controller: 'download', action: 'adminUpdateVersionOrder']" update="versionDisplayOrder">
Displayed versions (in order): <g:textField name="versions" value="${ versions*.baseVersion.join(', ') }"/> <g:submitButton name="Change"/> <g:remoteLink controller="download" action="adminShowVersionOrder" method="get" update="versionDisplayOrder">Cancel</g:remoteLink>
</g:formRemote>
