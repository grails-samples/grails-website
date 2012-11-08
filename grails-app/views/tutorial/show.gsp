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

    <div id="main" class="plugins boxWhite">
   
        <section class="plugin box">

            <article>
                <header>
                    <h3 class="single">
                        ${tutorialInstance?.title?.encodeAsHTML()}
                        <g:if test="${request.user == tutorialInstance.submittedBy}">
                             <g:link title="Edit Tutorial" class="actionIcon" action="edit" id="${tutorialInstance?.id}">
                                 <r:img border="0" uri="/img/famicons/page_edit.png" width="16" height="16" alt="Edit News" class="inlineIcon"/>
                             </g:link>
                        </g:if>                             
                    </h3>

                    <p class="meta">
                        Tags :
                        <g:if test="${tutorialInstance.tags.size() > 0}">
                            <g:each in="${tutorialInstance.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="#">${tag}</a></g:each>
                        </g:if>
                        <g:else>
                            none
                        </g:else>
                        <br/>
                        submitted by <a href="#">${tutorialInstance?.submittedBy?.login}</a>
                        <prettytime:display date="${tutorialInstance?.dateCreated}"/>
                    </p>

                </header>

                <div class="desc">
                    <p><wiki:text key="${'tutorial_' + tutorialInstance.id}">${tutorialInstance?.description}</wiki:text></p>
                </div>

                <p class="link">
                    <a href="${tutorialInstance.url.encodeAsHTML()}"
                       target="_blank">${tutorialInstance?.url?.encodeAsHTML()}</a>
                </p>
                <g:if test="${tutorialInstance?.isNew}">
                    <span class="status status-new">New</span>
                </g:if>

                <div class="disqus-container">
                    <disqus:comments bean="${tutorialInstance}"/>
                </div>
            </article>
        </section>
    </div>
</div>

</body>
</html>
