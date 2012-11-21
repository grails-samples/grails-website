<article class="item">
    <header>
        <h3>
            <a href="/plugins/pending/${pluginPendingApprovalInstance?.id}">
                ${pluginPendingApprovalInstance?.name}
                <g:if test="${pluginPendingApprovalInstance?.isNew}">
                    <g:img dir="../img/page" file="new.png" />
                </g:if>
            </a>
        </h3>

        <p class="meta">
            version <strong>${pluginPendingApprovalInstance?.versionNumber}</strong>,
            approval status: <strong><common:approvalStatus status="${pluginPendingApprovalInstance?.status}" /></strong>,
            submitted by <a href="#">${pluginPendingApprovalInstance?.submittedBy?.login}</a>
            <prettytime:display date="${pluginPendingApprovalInstance?.dateCreated}"/>
        </p>
    </header>

    <div class="desc">
        <p>${pluginPendingApprovalInstance?.notes}</p>
    </div>
</article>
