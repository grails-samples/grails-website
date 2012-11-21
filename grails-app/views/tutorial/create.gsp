<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/learn/sideNav"/>

    <section id="main">

            <h2><g:message code="tutorial.submit.title" /></h2>
            <p><g:message code="tutorial.submit.description" /></p>

            <flash:message flash="${flash}" bean="${tutorialInstance}"/>

            <g:hasErrors bean="${tutorialInstance}">
                <div class="alert alert-error">
                    <g:renderErrors bean="${tutorialInstance}" as="list"/>
                </div>
            </g:hasErrors>

            <g:form action="save" class="content-form">
                <fieldset>
                    <g:if test="${tutorialInstance?.id}">
                        <input type="hidden" name="id" value="${tutorialInstance.id}"></input>
                    </g:if>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'title', 'error')}">
                        <label class="control-label" for="title"><g:message code="tutorial.title" /></label>
                        <div class="controls">
                            <g:textField name="title" value="${tutorialInstance?.title}" required="required" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'description', 'error')}">
                        <label class="control-label" for="description"><g:message code="tutorial.description" /></label>
                        <div class="controls">
                            <g:textArea cols="30" rows="10" name="description" required="required" value="${tutorialInstance?.description}" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'url', 'error')}">
                        <label class="control-label" for="url"><g:message code="tutorial.url" /></label>
                        <div class="controls">
                            <g:textField name="url" value="${tutorialInstance?.url}" required="required" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'tags', 'error')}">
                        <label class="control-label" for="tags"><g:message code="tutorial.tags" /></label>
                        <div class="controls">
                            <g:textField name="tags" value="${tutorialInstance?.tags?.join(', ')}" class="input-fullsize"/>
                            <p class="help-block">Examples: introduction, security, screencast</p>
                        </div>
                    </div>

                    <div class="form-actions">
                        <g:submitButton name="submit" value="${ tutorialInstance?.id ? 'Update' :'Submit for Approval'}" class="btn"/>
                    </div>
                </fieldset>

            </g:form>

    </section>
</div>

</body>
</html>