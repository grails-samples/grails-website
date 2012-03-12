<div class="tutorial">
  <h2><a href="${artifact.url}">${artifact.title.encodeAsHTML()}</a></h2>
  <div class="edit">
    <g:link action="edit" id="${artifact.id}"><r:img border="0" uri="/images/icon-edit.png" width="15" height="15" alt="Icon Edit" class="inlineIcon" border="0"/></g:link>
    <div id="like-${artifact.id}"><like:voteUp item="${artifact}" js="jquery"/></div>
  </div>
  <p><text:summarize length="200"><wiki:text key="${'tutorial_description_' + artifact?.id}">${artifact.description}</wiki:text></text:summarize> (<g:link controller="tutorial" action="show" id="${artifact.id}">more...</g:link>)</p>
  <span class="likeCount">Liked by <em>${artifact.popularity.netLiked}</em> ${artifact.popularity.netLiked == 1 ? 'person' : 'people'}</span>
  <h3><span class="label">Tags:</span>
    <g:each in="${artifact.tags}" var="tag">
      <g:link action="search" params="[tag: tag]">${tag.encodeAsHTML()}</g:link>
    </g:each>
  </h3>
</div>
