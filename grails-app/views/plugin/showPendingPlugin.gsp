<head>
    <meta content="master" name="layout"/>
    <title>Pending Grails Plugin: ${plugin.title.encodeAsHTML()}</title>
    <r:require modules="plugin"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <div class="aside">
        <g:render template="sideSubmission"/>
    </div>

    <section id="main" class="items">
        <article class="item">
            <header>
                <h3 class="single">${pluginPendingApprovalInstance?.name}</h3>

                <p class="meta">
                    <strong>version:</strong> ${pluginPendingApprovalInstance?.versionNumber}
                    <br/>
                    <strong>approval status:</strong> <common:approvalStatus status="${pluginPendingApprovalInstance?.status}" />
                    <br/>
                    submitted by <a href="#">${pluginPendingApprovalInstance?.submittedBy?.login}</a>
                    <prettytime:display date="${pluginPendingApprovalInstance?.dateCreated}"/>
                </p>
            </header>

            <div class="desc">
                <p>${pluginPendingApprovalInstance?.notes}</p>
            </div>
            <div class="disqus-container">
                <disqus:comments bean="${pluginPendingApprovalInstance}" />
            </div>
        </article>
        <g:if test="${pluginPendingApprovalInstance?.isNew}">
            <span class="status status-new">New</span>
        </g:if>
    </section>
</div>

</body>
