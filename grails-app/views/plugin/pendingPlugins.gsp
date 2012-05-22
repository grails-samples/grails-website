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
        <g:if test="${pluginPendingApprovalTotal == 0}">
            <section class="plugin">
                <article>
                    <h2>Woot!</h2>
                    <p>We must really be on top of things if we have no pending plugin approvals.</p>
                    <p>To submit your plugin for approval, go to the <a href="/plugins/submitPlugin">Submit a Plugin</a> page.</p>
                </article>
            </section>
        </g:if>
        <g:render template="pendingPlugin" collection="${pluginPendingApprovalList}"
                  var="pluginPendingApprovalInstance"/>
    </div>

</div>

</body>
</html>