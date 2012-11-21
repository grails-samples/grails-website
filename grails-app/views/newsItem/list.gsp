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
    <g:render template="/community/sideNav"/>
    <div class="content-title">
        <h1>Latest News</h1>
        <g:render template="/common/searchBar" model="[type:'website']" />
    </div>

    <section id="main" class="items">
        <div class="alert alert-block margin-bottom-15">
            <p><g:message code="news.submit.description" />
                <g:link uri="/news/add"><g:message code="news.submit.button" /></g:link>.
            </p>
        </div>
        <flash:message flash="${flash}" />

        <g:each in="${newsItems}" var="newsItem">
            <article class="item">
                <header>
                    <h3>
                        <g:link controller="newsItem" action="show" id="${newsItem.id}">
                            ${newsItem.title.encodeAsHTML()}
                        </g:link>
                    </h3>
                    <p class="author">
                        Submitted by <a href="#">${newsItem?.author?.login}</a>
                        <prettytime:display date="${newsItem?.dateCreated}"/>,
                        published on <time datetime="${joda.format(value:newsItem.dateCreated, pattern:'yyyy-MM-dd')}">
                        <joda:format value="${newsItem.dateCreated}" pattern="dd MMM yyyy" />
                    </time>
                    </p>
                </header>
                <div class="body">
                    <wiki:text>${newsItem.body}</wiki:text>
                </div>
            </article>
        </g:each>
    </section>
</div>

</body>
</html>