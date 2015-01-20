<head>
    <meta content="master" name="layout"/>
    <title>Submit a Grails Plugin for Approval</title>
    <asset:stylesheet src="plugin"/>
    <asset:javascript src="plugin"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <div class="aside">
        <g:render template="sideSubmission"/>
    </div>

    <section id="main" class="plugins">

        <article>
            <h2>Submit Plugin for Approval</h2>

            <g:if test="${flash.message}">
                <div class="alert alert-${pluginPendingApproval.errors.errorCount > 0 ? 'error' : 'info'}">${flash.message}</div>
            </g:if>

            <g:form action="submitPlugin" class="content-form padding-top">

                <g:hiddenField name="status" value="${pluginPendingApproval.status}"/>

                <div class="control-group ${hasErrors(bean: pluginPendingApproval, field: 'name', 'error')}">
                    <label for="name">Plugin Name <small>(whatever you passed to the create-plugin command)</small></label>

                    <div class="controls">
                        <g:textField name="name" value="${params.name}" required="required" class="input-fullsize"/>
                        <g:if test="${pluginPendingApproval.errors.getFieldErrorCount('name') > 0}">
                            <p class="error-block">${pluginPendingApproval.errors.getFieldError('name').defaultMessage}</p>
                        </g:if>
                    </div>
                </div>

                <div class="control-group ${hasErrors(bean: pluginPendingApproval, field: 'versionNumber', 'error')}">
                    <label for="versionNumber">Version Number <small>(example: 0.0.1)</small></label>
                    <div class="controls">
                        <g:textField name="versionNumber" value="${params.versionNumber}" required="required" class="input-fullsize"/>
                        <g:if test="${pluginPendingApproval.errors.getFieldErrorCount('versionNumber') > 0}">
                            <p class="help-block">${pluginPendingApproval.errors.getFieldError('versionNumber').defaultMessage}</p>
                        </g:if>
                        <p class="help-block">This is important indicating versions if you need to re-submit plugins</p>
                    </div>
                </div>

                <div class="control-group ${hasErrors(bean: pluginPendingApproval, field: 'scmUrl', 'error')}">
                    <label for="scmUrl">Repository URL <small>(for example, http://www.github.com/grails/grails-core)</small></label>
                    <div class="controls">
                        <g:textField name="scmUrl" value="${params.scmUrl}" required="required" class="input-fullsize"/>
                        <g:if test="${pluginPendingApproval.errors.getFieldErrorCount('scmUrl') > 0}">
                            <p class="error-block">${pluginPendingApproval.errors.getFieldError('scmUrl').defaultMessage}</p>
                        </g:if>
                    </div>
                </div>

                <div class="control-group">
                    <label for="notes">Plugin Description</label>

                    <div class="controls">
                        <g:textArea rows="8" cols="60" name="notes" value="${params.notes}" required="required" class="input-fullsize"></g:textArea>
                    </div>
                </div>

                <p>
                    <em>Please note that in order for a plugin to be approved, you must make sure that the plugin is well
                    documented, which includes a full description and usage.</em>
                </p>

                <div class="form-actions">
                    <g:submitButton name="submit" value="Submit for Approval" class="btn"/>
                </div>

            </g:form>
        </article>

    </section>

</div>

</body>
