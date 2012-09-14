<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
     <r:require modules="content"/>
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
                                 <r:img border="0" uri="/img/famicons/page_edit.png" width="16" height="16" alt="Edit Page" class="inlineIcon"/>
                             </g:link>
                         </li>
                 </ul>
                </div>
            </g:if>
                 

                <header>
                    <h3 style="padding: 5px 0;">
                        <a href="/news/${newsItem?.id}">${newsItem?.title?.encodeAsHTML()}</a>
                    </h3>

                    <p class="meta">
                        submitted by <a href="#">${newsItem?.author?.login}</a>
                        <prettytime:display date="${newsItem?.dateCreated}"/>
                    </p>

                </header>

                <div class="desc">
                    <p><wiki:text>${newsItem?.body?.encodeAsHTML()}</wiki:text>
                    </p>
                </div>

                
                <div class="disqus-container">
                    <disqus:comments bean="${newsItem}"/>
                </div>
            </article>
    </div>
</div>

</body>
</html>