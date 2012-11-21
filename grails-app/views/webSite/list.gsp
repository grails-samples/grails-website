<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="community"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>


    <div class="content-title">
        <h1>Site using Grails</h1>
        <g:render template="/common/searchBar" model="[type:'website']" />
    </div>

    <section id="main" class="websites">
        <div class="alert alert-block margin-bottom-15">
            <p>
                <g:message code="website.list.submit.description" />
                <g:link action="create"><g:message code="website.list.submit.button" /></g:link>.
            </p>
        </div>

        <flash:message flash="${flash}" />
        <g:render template="webSite" collection="${featuredWebSiteInstanceList}" var="webSiteInstance"/>
        <g:render template="webSite" collection="${webSiteInstanceList}" var="webSiteInstance"/>


        <div class="pager">
            <g:if test="${websiteCount}">
                <g:paginate total="${websiteCount}" max="12" />
            </g:if>
        </div>
  
    </section>
</div>

</body>
</html>
