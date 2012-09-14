<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <g:render template="/learn/sideNav"/>
    <div id="main" class="plugins">
        <div class="alert alert-block margin-bottom-15">
            <p><g:message code="news.submit.description" /></p>
            <p>
                <g:link uri="/news/add" class="btn"><g:message code="news.submit.button" /></g:link>
            </p>
        </div>
        <flash:message flash="${flash}" />
        
        <article>
            <h2>Latest News</h2>
            
            <g:each in="${newsItems}" var="newsItem">
                <article class="news">
                    <h3><g:link controller="newsItem" action="show" id="${newsItem.id}">
                            <time datetime="${joda.format(value:newsItem.dateCreated, pattern:'yyyy-MM-dd')}">
                                <joda:format value="${newsItem.dateCreated}" pattern="dd MMM yyyy" />
                            </time> ${newsItem.title.encodeAsHTML()}</g:link></h3>
                    <div class="body">
                        <wiki:text>${newsItem.body}</wiki:text>                        
                    </div>


                </article>                        
            </g:each>
        </article>
    </div>
</div>

</body>
</html>