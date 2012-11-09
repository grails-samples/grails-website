<article>
    %{-- <a href="#" class="like">+1</a> --}%
    <g:if test="${request.user == webSiteInstance.submittedBy}">
         <g:link title="Edit WebSite" class="actionIcon" action="edit" id="${webSiteInstance?.id}">
             <r:img border="0" uri="/img/famicons/page_edit.png" width="16" height="16" alt="Edit News" class="inlineIcon"/>
         </g:link>
    </g:if>                             


    <div class="img">
        <bi:hasImage bean="${webSiteInstance}">
            <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank"><bi:img size="large"
                                                                                    bean="${webSiteInstance}"/></a>
        </bi:hasImage>
    </div>

    <div>

        <h3><g:link action="show" id="${webSiteInstance?.id}">${webSiteInstance?.title?.encodeAsHTML()}</g:link>
        </h3>

        <small style="color: #999; font-size: 14px;">
            <wiki:shorten key="${'webSite_large_' + webSiteInstance.id}" wikiText="${webSiteInstance?.shortDescription}" length="80" />
        </small>

        <p class="tags">
            <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank">${webSiteInstance?.url?.encodeAsHTML()}</a>
        </p>

        <p><wiki:text key="${'webSite_' + webSiteInstance.id}">${webSiteInstance.description}</wiki:text></p>
    </div>
    

</article>
<div class="disqus-container">
    <disqus:comments bean="${webSiteInstance}"/>
</div>