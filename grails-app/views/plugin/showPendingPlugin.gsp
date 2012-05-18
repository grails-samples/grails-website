<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="plugin"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <div class="aside">
        <g:render template="sideSubmission"/>
    </div>

    <div id="main" class="plugins">
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
                <div class="disqus-container">
                    <disqus:comments bean="${pluginPendingApprovalInstance}" />
                </div>
            </article>
            <g:if test="${pluginPendingApprovalInstance?.isNew}">
                <span class="status status-new">New</span>
            </g:if>
        </section>
    </div>
</div>

</body>
</html>