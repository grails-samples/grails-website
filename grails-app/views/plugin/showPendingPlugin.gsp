<head>
    <meta content="masterv2" name="layout"/>
    <title>Pending Grails Plugin: ${pluginPendingApprovalInstance?.name.encodeAsHTML()}</title>
    <asset:stylesheet src="plugin"/>
    <asset:javascript src="plugin"/>
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
                    <strong>source:</strong> <a href="${pluginPendingApprovalInstance?.scmUrl}">${pluginPendingApprovalInstance?.scmUrl}</a>
                    <br/>                    
                    submitted by <a href="#">${pluginPendingApprovalInstance?.submittedBy?.login}</a>
                    <prettytime:display date="${pluginPendingApprovalInstance?.dateCreated}"/>
                </p>
            </header>

            <div class="desc">
                <p>${pluginPendingApprovalInstance?.notes}</p>
            </div>
            <div class="disqus-container">
                <disqus:comments bean="${pluginPendingApprovalInstance}"  url="${createLink(uri:request.forwardURI, absolute:true)}"/>
            </div>
        </article>
        <g:if test="${pluginPendingApprovalInstance?.isNew}">
            <span class="status status-new">New</span>
        </g:if>
    </section>
</div>

</body>
