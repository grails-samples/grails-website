<a href="${plugin.scmUrl ?: plugin.fisheye}"> <r:img uri="/images/new/plugins/icons/fisheye.png" border="0" /> Source</a>
<a href="${plugin.documentationUrl}"> <r:img uri="/images/new/plugins/icons/doc.png" border="0" /> Docs</a>
<g:if test="${plugin.issuesUrl}">
  <a href="${plugin.issuesUrl}"><r:img uri="/images/new/plugins/icons/issues.png" border="0" />Issues</a>
</g:if>
