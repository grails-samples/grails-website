<li><a href="${plugin.scmUrl ?: plugin.fisheye}"> <r:img uri="/images/new/plugins/icons/fisheye.png" border="0" />&nbsp;Source</a></li>
<li><a href="${plugin.documentationUrl}"> <r:img uri="/images/new/plugins/icons/doc.png" border="0" />&nbsp;Docs</a></li>
<g:if test="${plugin.issuesUrl}">
  <li><a href="${plugin.issuesUrl}"><r:img uri="/images/new/plugins/icons/issues.png" border="0" />&nbsp;Issues</a></li>
</g:if>
