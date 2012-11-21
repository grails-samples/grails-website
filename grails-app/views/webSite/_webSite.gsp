<article>
    %{-- <a href="#" class="like">+1</a> --}%
    <div class="img">
        <bi:hasImage bean="${webSiteInstance}">
            <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank" style="height: 130px; overflow: hidden;">
                <bi:img size="large" bean="${webSiteInstance}"/>
            </a>
        </bi:hasImage>
    </div>
    <div class="content">
        <h3><g:link action="show" id="${webSiteInstance?.id}">${webSiteInstance?.title?.encodeAsHTML()}</g:link></h3>
        <small style="color: #999; font-size: 14px;">
            <wiki:shorten key="${'webSite_large_' + webSiteInstance.id}" wikiText="${webSiteInstance?.shortDescription}" length="80" />
        </small>
        <p class="tags">
            <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank">${webSiteInstance?.url?.encodeAsHTML()}</a>
        </p>
        <p><wiki:text key="${'webSite_' + webSiteInstance.id}">${webSiteInstance.description}</wiki:text></p>
        <g:if test="${request.user == webSiteInstance.submittedBy}">
            <p class="buttons">
                <g:link title="Edit WebSite" class="actionIcon" action="edit" id="${webSiteInstance?.id}">
                    <i class="icon-edit"></i> Edit
                </g:link>
            </p>
        </g:if>
    </div>


</article>
