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

    <div id="main" class="websites">
        <section>
            <article class="tutorial">
                <a href="#" class="like ${!tutorialInstance?.isNew ?: 'with-ribbon'}">+1</a>
                <div>
                    <h3><g:link action="show" id="${tutorialInstance?.id}">${tutorialInstance?.title?.encodeAsHTML()}</g:link></h3>

                    <p class="tags">
                        <g:each in="${tutorialInstance?.tags}" var="tag">
                            <a href="#" class="btn blueLight">${tag}</a>
                        </g:each>
                    </p>

                    <p>${tutorialInstance?.description?.encodeAsHTML()}</p>

                    <p class="link">
                        <a href="${tutorialInstance.url.encodeAsHTML()}" target="_blank">${tutorialInstance?.url?.encodeAsHTML()}</a>
                    </p>
                </div>

                <g:if test="${tutorialInstance?.isNew}">
                    <span class="status status-new">New</span>
                </g:if>

            </article>
            <disqus:comments bean="${tutorialInstance}" />
        </section>
    </div>
</div>

</body>
</html>