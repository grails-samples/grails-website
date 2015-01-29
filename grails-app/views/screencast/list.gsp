<head>
    <meta content="masterv2" name="layout"/>
    <title>Grails Screencasts</title>
</head>

<body>

<div id="content" class="content-aside" role="main">
    <g:render template="/learn/sideNav"/>

    <div class="content-title">
        <h1>Screencast <small>See & learn</small></h1>
        <g:render template="/common/searchBar" model="[type:'screencast']" />
    </div>


    <section id="main" class="items">

        <div class="alert alert-block margin-bottom-15">
            <p><g:message code="screencast.list.submit.description" />
                <g:link uri="/screencasts/add"><g:message code="screencast.list.submit.button" /></g:link>.
            </p>
        </div>
        <flash :message flash="${flash}" />

        <g:render template="screencast" collection="${screencastInstanceList}" var="screencastInstance"/>
        <g:if test="${screencastTotal}">
            <section class="pager">
                <g:paginate total="${screencastTotal}" max="10" />
            </section>
        </g:if>
    
    </section>
</div>

</body>
