<article>
    <a href="#" class="like">+1</a>

    <div class="img">
        <bi:hasImage bean="${webSiteInstance}">
            <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank"><bi:img size="large"
                                                                                    bean="${webSiteInstance}"/></a>
        </bi:hasImage>
    </div>

    <div>
        <h3><g:link action="show" id="${webSiteInstance?.id}">${webSiteInstance?.title?.encodeAsHTML()}</h3></g:link>

        <small style="color: #999; font-size: 14px;"><wiki:shorten text="${webSiteInstance?.shortDescription?.encodeAsHTML()}" length="80" /></small>

        <p class="tags">
            <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank">${webSiteInstance?.url?.encodeAsHTML()}</a>
        </p>

        <p>${webSiteInstance?.description?.encodeAsHTML()}</p>
    </div>
</article>