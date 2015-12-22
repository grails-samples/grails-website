<head>
    <meta content="masterv2" name="layout"/>
    <title>Submit a Grails Tutorial</title>
</head>

<body>

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
                        <label class="col-sm-2 control-label" for="title"><g:message code="tutorial.title" /></label>
                        <div class="col-sm-10">
                            <g:textField class="form-control input-fullsize" name="title" value="${tutorialInstance?.title}" required="required" />
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'description', 'error')}">
                        <label class="col-sm-2 control-label" for="description"><g:message code="tutorial.description" /></label>
                        <div class="col-sm-10">
                            <g:textArea class="form-control input-fullsize" cols="30" rows="10" name="description" required="required" value="${tutorialInstance?.description}" />
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'url', 'error')}">
                        <label class="col-sm-2 control-label" for="url"><g:message code="tutorial.url" /></label>
                        <div class="col-sm-10">
                            <g:textField class="form-control input-fullsize" name="url" value="${tutorialInstance?.url}" required="required" />
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'tags', 'error')}">
                        <label class="col-sm-2 control-label" for="tags"><g:message code="tutorial.tags" /></label>
                        <div class="col-sm-10">
                            <g:textField class="form-control input-fullsize" name="tags" value="${tutorialInstance?.tags?.join(', ')}" />
                            <p class="help-block">Examples: introduction, security, screencast</p>
                        </div>
                    </div>

                    <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
                        <g:submitButton name="submit" value="${ tutorialInstance?.id ? 'Update' :'Submit for Approval'}" class="btn"/>
                    </div></div>
                </fieldset>

            </g:form>

    </section>

</body>
