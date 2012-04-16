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
        <aside class="tags">
            <h4>Instructions</h4>

            Data and CSS needs to go here
        </aside>
    </div>

    <div id="main" class="plugins">

        <section class="previousRelease">
            <h3>Submit Plugin for Approval</h3>

            <g:if test="${flash.message}">
                <div class="alert alert-info">${flash.message}</div>
            </g:if>

            <g:hasErrors bean="${pluginPendingApproval}">
                <div class="alert alert-error">
                    <g:renderErrors bean="${pluginPendingApproval}" as="list"/>
                </div>
            </g:hasErrors>

            <g:form action="submitPlugin" class="content-form">
                <p>
                    <label for="name">Plugin Name <small>(whatever you passed to the create-plugin command)</small></label>
                    <g:textField name="name" value="${params.name}" style="width: 41em;" />
                </p>
                <p>
                    <label for="scmUrl">Repository URL <small>(for example, http://www.github.com/grails/grails-core)</small></label>
                    <g:textField name="scmUrl" value="${params.scmUrl}" style="width: 41em;" />
                </p>
                <p>
                    <label for="email">Email Address <small>(so we can contact you when it's approved)</small></label>
                    <g:textField name="email" value="${params.email}" style="width: 41em;" />
                </p>

                <p>
                    <em>Please note that in order for a plugin to be approved, you must make sure that the plugin is well
                    documented, which includes a full description and usage.</em>
                </p>

                <g:submitButton name="submit" value="Submit for Approval" class="btn" />
            </g:form>

        </section>
    </div>

</div>

</body>
</html>