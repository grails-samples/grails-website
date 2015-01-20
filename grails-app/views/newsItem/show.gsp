<head>
    <meta content="master" name="layout"/>
    <title>Grails News: ${newsItem?.title?.encodeAsHTML()}</title>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>
    <section id="main" class="items item-new">
         <article class="item">
             <g:if test="${request.user == newsItem.author}">

                <div id="wikiLastUpdated">Last updated by ${latest?.author?.login}
                 <prettytime:display date="${newsItem.lastUpdated}"/>. Status: ${newsItem.status}
                </div>    
                <div id="viewLinks" class="wiki-links">

                 <ul class="wikiActionMenu">
                     <li>
                         <g:link title="Edit Page" class="actionIcon" action="edit" id="${newsItem?.id}">
                             <asset:image border="0" href="famicons/page_edit.png" width="16" height="16" alt="Edit News" class="inlineIcon"/>
                         </g:link>
                     </li>
                 </ul>
                </div>
            </g:if>

            <g:if test="${flash.message}">
                <div class="alert alert-info">${flash.message}</div>
            </g:if>                 

            <header>
                <h1>
                    ${newsItem?.title?.encodeAsHTML()}
                    <span class="date">

                    </span>
                </h1>

                <p class="author">
                    Submitted by <a href="#">${newsItem?.author?.login}</a>
                    <prettytime:display date="${newsItem?.dateCreated}"/>,
                    published on <time datetime="${joda.format(value:newsItem.dateCreated, pattern:'yyyy-MM-dd')}">
                        <joda:format value="${newsItem.dateCreated}" pattern="dd MMM yyyy" />
                    </time>
                </p>

            </header>

            <div class="desc">
                <wiki:text>${newsItem?.body}</wiki:text>
            </div>

            
            <div class="disqus-container">
                <disqus:comments bean="${newsItem}" url="${createLink(uri:request.forwardURI, absolute:true)}"/>
            </div>
        </article>
    </section>
</div>

</body>
