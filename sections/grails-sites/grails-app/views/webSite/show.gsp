<%@page defaultCodec="none"%>
<g:setProvider library="jquery" plugin="jquery"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>${artifact.title.encodeAsHTML()}</title>
    </head>
    <body>
        <div class="body artifact">
            <div class="artifactControls">		
                <div class="menuButton"><g:link class="list" action="list">Web Sites</g:link></div>
                <div class="menuButton"><g:link class="edit" action="edit" id="${artifact.id}">Edit Web Site</g:link></div>	
            </div>
            <p>
                <wiki:text key="${'website_description_' + artifact?.id}">${artifact?.description}</wiki:text>
            </p>
            <div class="webSiteThumbnail">
            <bi:hasImage bean="${artifact}">
                <a href="${artifact.url.encodeAsHTML()}"><bi:img size="large" bean="${artifact}" /></a>
            </bi:hasImage>
            </div>
            <p class="tags"><span class="label">Tags:</span>
              <g:each in="${artifact.tags}" var="tag">
                <g:link action="search" params="[tag: tag]">${tag.encodeAsHTML()}</g:link>
              </g:each>
            </p>
            <p class="likeCount">Liked by <em>${artifact.popularity.netLiked}</em> ${artifact.popularity.netLiked == 1 ? 'person' : 'people'} |</p>
            <div id="like-${artifact.id}" class="vote"><like:voteUp item="${artifact}" js="jquery"/></div>
        </div>
    </body>
</html>
