<%@page import="org.grails.auth.Role" %>
<%@page import="org.grails.wiki.WikiPage"%>
<g:set var="updateElement" value="${update ?: 'contentPane'}"/>
<div id="editLinks" class="wikiLinks">

    <g:set var="href" value="${update ? 'javascript:void(0)' : '#'}"/>
    <ul class="wikiActionMenu">
        <shiro:hasRole name="${Role.ADMINISTRATOR}">
        	<g:if test="${content.instanceOf(org.grails.wiki.WikiPage)}">
	            <li>
	                <a href="${href}" class="actionIcon" onclick="myYUI.appear('deprecateDialog')">
	                    <asset:image src="icon-upload.png" width="15" height="15" alt="Icon Image Upload" class="inlineIcon" border="0"/>
	                    <span>Deprecate</span>
	                </a>
	            </li>
            </g:if>
        </shiro:hasRole>
        <shiro:lacksRole name="${Role.ADMINISTRATOR}">
            <li>
                <a href="${href}" class="actionIcon" onclick="myYUI.appear('uploadDialog')">
                    <asset:image src="icon-upload.png" width="15" height="15" alt="Icon Image Upload" class="inlineIcon" border="0"/>
                    <span>Upload Image</span>
                </a>
            </li>
        </shiro:lacksRole>
        <li>
            <a href="${href}" class="actionIcon"
               onclick="YAHOO.util.Connect.setForm('${editFormName}');YAHOO.util.Connect.asyncRequest('POST', '/preview/${content?.title}', {success: function(o){YAHOO.util.Dom.get('previewPane').innerHTML = o.responseText;showPreview();}, failure: function(o){}}, '_ul=${updateElement}');return false;" class="actionIcon">
                <asset:image src="icon-preview.png" width="18" height="15" alt="Icon Edit" class="inlineIcon" border="0"/>
                <span>Preview</span>
            </a>
        </li>
        <li>
            <a href="${href}" class="actionIcon"
               onclick="YAHOO.util.Connect.setForm('${editFormName}');YAHOO.util.Connect.asyncRequest('POST', '${createLink(action: 'saveWikiPage', id: content.title, params: [_ul: updateElement])}', {success: function(o){showCommentPost();YAHOO.util.Dom.get('${updateElement}').innerHTML = o.responseText; fadeMessages()}, failure: function(o){}}, null);return false;" class="actionIcon">
                <asset:image src="icon-save.png" width="15" height="15" alt="Icon Save" class="inlineIcon" border="0"/>
                <span>Save</span>
            </a>
        </li>
        <li>
            <g:remoteLink class="actionIcon" update="${updateElement}" action="index" id="${content?.title}" params="[_ul: updateElement]" method="GET" onLoaded="showCommentPost()">
                <asset:image src="famicons/cancel.png" width="15" height="15" alt="Icon Cancel" class="inlineIcon" border="0"/>
                <span>Cancel</span>
            </g:remoteLink>
        </li>
        <li>
            <g:remoteLink class="actionIcon" action="infoWikiPage" id="${content?.title}" update="${updateElement}" params="[_ul: updateElement]" method="GET" onLoaded="showCommentPost()">
                <asset:image border="0" src="icon-info.png" width="15" height="15" alt="Icon Edit" class="inlineIcon" border="0"/>
                <span>View Info</span>
            </g:remoteLink>
        </li>
        <li>
            <g:render template='/common/wikiSyntaxLink'/>
        </li>
    </ul>
</div>
