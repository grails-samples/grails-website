<head>
    <meta content="master" name="layout"/>
    <title>Grails Tutorials</title>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <g:render template="/learn/sideNav"/>
    <div class="content-title">
        <h1>Tutorials <small>Share & learn</small></h1>
        <g:render template="/common/searchBar" model="[type:'tutorial']" />
    </div>
    <section id="main" class="items">
        <article>
            <div class="alert alert-block">
                <p>
                    <g:message code="tutorial.list.submit.description" />
                    <g:link uri="/tutorials/add"><g:message code="tutorial.list.submit.button" />.</g:link>
                </p>
            </div>
            <flash:message flash="${flash}" />

            <g:render template="tutorial" collection="${tutorialInstanceList}" var="tutorialInstance"/>

            <g:if test="${tutorialCount}">
                <section class="pager">
                    <g:paginate total="${tutorialCount}" max="10" />
                </section>

            </g:if>
        </article>
    </section>
</div>

</body>
