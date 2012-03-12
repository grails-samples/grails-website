<div class="webSite">
    <hr>
    <div class="webSiteThumbnail ${i % 2 ? 'odd' : 'even'}">
    <bi:hasImage bean="${artifact}">
        <a href="${artifact.url.encodeAsHTML()}"><bi:img size="large" bean="${artifact}" /></a>
    </bi:hasImage>
    </div>
    <h2 style="margin-bottom: 0.5em;">
        ${artifact.title.encodeAsHTML()}&nbsp;&nbsp;
        <g:link controller="webSite" action="edit" id="${artifact.id}"><r:img uri="/images/icon-edit.png"/></g:link>
        <div id="like-${artifact.id}" class="vote"><like:voteUp item="${artifact}" js="jquery"/></div>
    </h2>
    <div class="tagList">
        <strong>Tags:</strong>
        <g:each in="${artifact.tags}" var="tag">
            <g:link action="search" params="[tag: tag]">${tag.encodeAsHTML()}</g:link>
        </g:each>
    </div>
    <div>
        <text:lineBreak>
            <text:summarize length="200" encodeAs="HTML">${artifact.description}</text:summarize>
        </text:lineBreak>
        (<g:link controlle="webSite" action="show" id="${artifact.id}">more...</g:link>)
    </div>
    <p class="likeCount">Liked by <em>${artifact.popularity.netLiked}</em> ${artifact.popularity.netLiked == 1 ? 'person' : 'people'}</p>
</div>
