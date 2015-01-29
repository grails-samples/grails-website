<head>
    <meta content="masterv2" name="layout"/>
    <title>Grails Tutorial: ${tutorialInstance.title.encodeAsHTML()}</title>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/learn/sideNav"/>

    <section id="main" class="items">

        <g:if test="${request.user == tutorialInstance.submittedBy}">
            <div class="alert alert-info">
                <i class="icon-edit"></i> You can
            <g:link title="Edit Tutorial" class="actionIcon" action="edit" id="${tutorialInstance?.id}">
                edit the content
            </g:link> of the tutorial.
            </div>
        </g:if>

        <article class="item">
            <header>
                <h3 class="single">
                    ${tutorialInstance?.title?.encodeAsHTML()}
                    <g:if test="${tutorialInstance?.isNew}">
                        <g:img dir="img/page" file="new.png" />
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
            <div class="disqus-container">
                <disqus:comments bean="${tutorialInstance}"  url="${createLink(uri:request.forwardURI, absolute:true)}"/>
            </div>
        </article>
    </section>
</div>

</body>
