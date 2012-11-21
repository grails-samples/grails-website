<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="plugin"/>
</head>

<body>

<div id="content" class="content-aside-2" role="main">

    <div class="aside">
        <g:render template="sideSubmission" />
    </div>

    <section id="main">

        <article>
            <h3>Submit Plugin for Approval</h3>

            <g:if test="${flash.message}">
                <div class="alert alert-info">${flash.message}</div>
            </g:if>

            <p>Thank you for submitting your plugin. We will get back to you within 24 hours with either an approval, or
            a rejection with an explanation.</p>

        </article>
    </section>

</div>

</body>
</html>