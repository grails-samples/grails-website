<g:setProvider library="yui"/>

<g:set var="updateElement" value="${update ?: 'contentPane'}"/>
<g:set var="formName" value="${editFormName ?: 'wikiEditForm'}"/>

<g:render template="/content/editActions" model="[content:wikiPage, update:updateElement, editFormName: formName, saveUri: saveUri]"/>

<div id="uploadDialog" class="dialog" style="display:none;margin-top:10px;width:500px;">
    <iframe id="uploadIframe" width="550" height="40" frameborder="0" scrolling="no" src="${createLink(controller: 'content', action: 'uploadImage', id: wikiPage.title)}"></iframe>
</div>

<div id="deprecateDialog" class="dialog" style="display:none;margin-top:10px;width:500px;">
    <g:form name="deprecateForm" url="[controller:'content', action:'deprecate', id:wikiPage.title]" onsubmit="myYUI.hide('deprecateDialog')">
        <input name="uri" type="text"/>
        <g:submitButton name="submit" value="Submit" />
    </g:form>
</div>

<div id="editForm" style="margin-top:10px;">
    <g:render template="/common/messages" model="${pageScope.getVariables() + [bean:wikiPage]}"/>

    <g:if test="${!wikiPage.locked}">
        <g:form name="${formName}" url="[controller:'content',action:'saveWikiPage',id:wikiPage.title]" method="post">
            <g:render template="/content/wikiFields" model="[wikiPage:wikiPage]"/>
        </g:form>
    </g:if>

</div>
