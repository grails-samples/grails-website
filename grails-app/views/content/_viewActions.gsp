<div id="viewLinks" class="wiki-links">
    <g:set var="updateElement" value="${update ?: 'contentPane'}"/>

    <ul class="wikiActionMenu">
        <g:if test="${content?.locked}">
            <li>
                <r:img border="0" uri="/img/famicons/lock.png" width="16" height="16" alt="Page Locked" class="inlineIcon"/>
            </li>
        </g:if>
        <g:else>
            <li>
                <g:link title="Edit Page" class="actionIcon" action="editWikiPage" id="${content?.title}">
                    <r:img border="0" uri="/img/famicons/page_edit.png" width="16" height="16" alt="Edit Page" class="inlineIcon"/>
                </g:link>
            </li>
        </g:else>
        <li>
            <g:link title="Revision History" class="actionIcon" action="infoWikiPage" id="${content?.title}">
                <r:img border="0" uri="/img/famicons/time.png" width="16" height="16" alt="Revision History" class="inlineIcon"/>
            </g:link>
        </li>
    </ul>
</div>
