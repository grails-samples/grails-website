<head>
    <meta content="masterv2" name="layout"/>
    <title>Grails Screencast: ${screencastInstance.title.encodeAsHTML()}</title>
</head>

<body>
    <section id="main" class="screencasts items">

        <g:if test="${request.user == screencastInstance.submittedBy}">
            <div class="alert alert-info">
                <i class="icon-edit"></i> You can
                <g:link title="Edit Screencast" class="actionIcon" action="edit" id="${screencastInstance?.id}">
                edit the content
            </g:link> of the screencast.
            </div>
        </g:if>
        <article class="item">
            <header>
                <h3>
                    ${screencastInstance?.title?.encodeAsHTML()}
                    <g:if test="${screencastInstance?.isNew}">
                        <g:img dir="img/page" file="new.png" />
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

            <div class="disqus-container">
                <disqus:comments bean="${screencastInstance}"  url="${createLink(uri:request.forwardURI, absolute:true)}"/>
            </div>
        </article>
    </section>


</body>
</html>
