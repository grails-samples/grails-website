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

    <div id="main" class="screencasts boxWhite">
        <section class="screencast box">
            <article>
                <header>
                    <h3 style="padding: 5px 0;">
                        ${screencastInstance?.title?.encodeAsHTML()}
                        <g:if test="${request.user == screencastInstance.submittedBy}">
                             <g:link title="Edit Screencast" class="actionIcon" action="edit" id="${screencastInstance?.id}">
                                 <r:img border="0" uri="/img/famicons/page_edit.png" width="16" height="16" alt="Edit News" class="inlineIcon"/>
                             </g:link>
                        </g:if>                             
   
                    </h3>

                    <p class="meta">
                        Tags :
                        <g:if test="${screencastInstance.tags.size() > 0}">
                            <g:each in="${screencastInstance.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="#">${tag}</a></g:each>
                        </g:if>
                        <g:else>
                            none
                        </g:else>
                        <br/>
                        submitted by <a href="#">${screencastInstance?.submittedBy?.login}</a>
                        <prettytime:display date="${screencastInstance?.dateCreated}"/>
                    </p>

                </header>

                <div class="video-container">
                    <casts:embedPlayer screencast="${screencastInstance}" width="665" height="400" />
                </div>

                <div class="desc" style="padding-right: 10px;">
                    <p><wiki:text key="${'screencast_' + screencastInstance.id}">${screencastInstance.description}</wiki:text></p>
                </div>

                <g:if test="${screencastInstance?.isNew}">
                    <span class="status status-new">New</span>
                </g:if>

                <div class="disqus-container">
                    <disqus:comments bean="${screencastInstance}"/>
                </div>
            </article>
        </section>
    </div>
</div>

</body>
</html>
