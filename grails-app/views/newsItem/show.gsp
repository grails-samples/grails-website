<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
     <r:require modules="content"/>
    <style type="text/css">
        .author {
            font-family: Helvetica, Arial, sans-serif;
            font-size: 12px;        
            font-size: 12px;
            color: rgba(102, 102, 102, 0.79);        
        }
    </style>     
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/content/sideNav"/>
    <div id="main">
         <article>
             <g:if test="${request.user == newsItem.author}">

                <div id="wikiLastUpdated">Last updated by ${latest?.author?.login}
                 <prettytime:display date="${newsItem.lastUpdated}"/>. Status: ${newsItem.status}
                </div>    
                <div id="viewLinks" class="wiki-links">

                 <ul class="wikiActionMenu">
                         <li>
                             <g:link title="Edit Page" class="actionIcon" action="edit" id="${newsItem?.id}">
                                 <r:img border="0" uri="/img/famicons/page_edit.png" width="16" height="16" alt="Edit News" class="inlineIcon"/>
                             </g:link>
                         </li>
                 </ul>
                </div>
            </g:if>

            <g:if test="${flash.message}">
                <div class="alert alert-info">${flash.message}</div>
            </g:if>                 

            <header>
                <h3 style="padding: 5px 0;">
                    <time datetime="${joda.format(value:newsItem.dateCreated, pattern:'yyyy-MM-dd')}">
                        <joda:format value="${newsItem.dateCreated}" pattern="dd MMM yyyy" />
                    </time>                         
                    - <a href="/news/${newsItem?.id}">${newsItem?.title?.encodeAsHTML()}</a>
                </h3>

                <p class="author">
                    Submitted by <a href="#">${newsItem?.author?.login}</a>
                    <prettytime:display date="${newsItem?.dateCreated}"/>
                </p>

            </header>

            <div class="desc">
                <wiki:text>${newsItem?.body}</wiki:text>
            </div>

            
            <div class="disqus-container">
                <disqus:comments bean="${newsItem}"/>
            </div>
        </article>
    </div>
</div>

</body>
</html>
