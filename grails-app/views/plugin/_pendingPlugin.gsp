<section class="plugin">
    <article>
        <header>
            <h3><a href="/plugins/pending/${pluginPendingApprovalInstance?.id}">${pluginPendingApprovalInstance?.name}</a></h3>

            <p class="meta">
                version ${pluginPendingApprovalInstance?.versionNumber}
                <br/>
                approval status: <common:approvalStatus status="${pluginPendingApprovalInstance?.status}" />
                <br/>
                submitted by <a href="#">${pluginPendingApprovalInstance?.submittedBy?.login}</a>
                <prettytime:display date="${pluginPendingApprovalInstance?.dateCreated}"/>
            </p>
        </header>

        <div class="desc">
            <p>${pluginPendingApprovalInstance?.notes}</p>
        </div>
    </article>
    <g:if test="${pluginPendingApprovalInstance?.isNew}">
        <span class="status status-new">New</span>
    </g:if>
</section>
<!--<span class="status status-new">New</span>-->