<g:setProvider library="yui"/>

<g:set var="updateElement" value="${update ?: 'contentPane'}"/>

<div id="infoLinks" style="margin-left:450px;">
    <g:remoteLink update="${updateElement}" id="${content?.title}" params="[_ul: updateElement]">
        <img src="${createLinkTo(dir:'images/','icon-edit.png')}" width="15" height="15" alt="Icon Edit" class="inlineIcon" border="0" />
        View Page
    </g:remoteLink>

    <shiro:authenticated>
        <g:remoteLink update="editPane" action="markupWikiPage" id="${content?.title}" params="[_ul: updateElement]">
            <img src="${createLinkTo(dir:'images/','icon-edit.png')}" width="15" height="15" alt="Icon Edit" class="inlineIcon" border="0" />
            View Markup
        </g:remoteLink>
    </shiro:authenticated>

</div>


<h3>Page: ${content?.title}, Version:${content?.number}</h3>

<div id="editPane">
    <wiki:text>
        ${content?.body}
    </wiki:text>

</div>
