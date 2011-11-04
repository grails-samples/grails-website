<g:setProvider library="${jsLibrary}"/>
<span class="like">Like:&nbsp;<span class="count">${item.popularity.liked}</span></span>
<span class="disliked">Dislike:&nbsp;<span class="count">${item.popularity.disliked}</span></span>
&nbsp;<like:vote js="${jsLibrary}" item="${item}" userId="${principal}"/>
