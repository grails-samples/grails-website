<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="plugin"/>
</head>

<body>

${pluginPendingApproval.errors.inspect()}

<div id="content" class="content-aside-2" role="main">

    <div class="aside">
        <aside class="tags">
            <h4>Instructions</h4>

            <p>Data and CSS needs to go here</p>
        </aside>
    </div>

    <div id="main" class="plugins">

        <section class="previousRelease">
            <h3>Submit Plugin for Approval</h3>

            <g:if test="${flash.message}">
                <div class="alert alert-${pluginPendingApproval.errors.errorCount > 0 ? 'error' : 'info'}">${flash.message}</div>
            </g:if>

            <g:form action="submitPlugin" class="content-form">
                
                <g:hiddenField name="status" value="${pluginPendingApproval.status}" />

                <div class="control-group ${hasErrors(bean: pluginPendingApproval, field: 'name', 'error')}">
                    <label for="name">Plugin Name <small>(whatever you passed to the create-plugin command)</small>
                    </label>

                    <div class="controls">
                        <g:textField name="name" value="${params.name}" style="width: 41em;"/>
                        <g:if test="${pluginPendingApproval.errors.getFieldErrorCount('name') > 0}">
                            <p class="error-block">${pluginPendingApproval.errors.getFieldError('name').defaultMessage}</p>
                        </g:if>
                    </div>
                </div>

                <div class="control-group ${hasErrors(bean: pluginPendingApproval, field: 'scmUrl', 'error')}">
                    <label for="scmUrl">Repository URL <small>(for example, http://www.github.com/grails/grails-core)</small>
                    </label>

                    <div class="controls">
                        <g:textField name="scmUrl" value="${params.scmUrl}" style="width: 41em;"/>
                        <g:if test="${pluginPendingApproval.errors.getFieldErrorCount('scmUrl') > 0}">
                            <p class="error-block">${pluginPendingApproval.errors.getFieldError('scmUrl').defaultMessage}</p>
                        </g:if>
                    </div>
                </div>

                <div class="control-group ${hasErrors(bean: pluginPendingApproval, field: 'email', 'error')}">
                    <label for="email">Email Address</label>

                    <div class="controls">
                        <g:textField name="email" value="${params.email}" style="width: 41em;"/>
                        <g:if test="${pluginPendingApproval.errors.getFieldErrorCount('email') > 0}">
                            <p class="error-block">${pluginPendingApproval.errors.getFieldError('email').defaultMessage}</p>
                        </g:if>
                    </div>
                </div>

                <div class="control-group">
                    <label for="email">Comments to Moderator</label>

                    <div class="controls">
                        <g:textArea rows="3" cols="60" name="notes" value="${params.notes}" style="width: 41em;"></g:textArea>
                    </div>
                </div>

                <p>
                    <em>Please note that in order for a plugin to be approved, you must make sure that the plugin is well
                    documented, which includes a full description and usage.</em>
                </p>

                <g:submitButton name="submit" value="Submit for Approval" class="btn"/>
            </g:form>

        </section>
    </div>

</div>

</body>
</html>