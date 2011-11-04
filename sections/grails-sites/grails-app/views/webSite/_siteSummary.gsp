<hr>
<div class="webSiteThumbnail ${i % 2 ? 'odd' : 'even'}">
<bi:hasImage bean="${webSite}">
    <a href="${webSite.url.encodeAsHTML()}"><bi:img size="large" bean="${webSite}" /></a>
</bi:hasImage>
</div>
<h2 style="margin-bottom: 0.5em;">
    ${webSite.title.encodeAsHTML()}&nbsp;&nbsp;
    <g:link controller="webSite" action="edit" id="${webSite.id}"><r:img uri="/images/icon-edit.png"/></g:link>
</h2>
<div class="tagList">
    <strong>Tags:</strong>
    <g:each in="${webSite.tags}" var="tag">
        <g:link action="search" params="[tag: tag]">${tag.encodeAsHTML()}</g:link>
    </g:each>
</div>
<p>${webSite.description.encodeAsHTML()}</p>
