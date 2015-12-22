<head>
    <meta content="masterv2" name="layout"/>
    <title>Submit a Grails Screencast</title>
</head>

<body>

    <section id="main">
        <article>

            <h2><g:message code="screencast.submit.title" /></h2>
            <p><g:message code="screencast.submit.description" /></p>

            <flash:message flash="${flash}" bean="${screencastInstance}"/>

            <g:hasErrors bean="${screencastInstance}">
                <div class="alert alert-error">
                    <g:renderErrors bean="${screencastInstance}" as="list"/>
                </div>
            </g:hasErrors>

            <g:form action="save" class="content-form">
                <input type="hidden" name="id" value="${screencastInstance.id}"></input>
                <fieldset>
                    <div class="control-group ${hasErrors(bean: screencastInstance, field: 'title', 'error')}">
                        <label class="col-sm-2 control-label" for="title"><g:message code="screencast.title" /></label>
                        <div class="col-sm-10">
                            <g:textField class="form-control input-fullsize" name="title" value="${screencastInstance?.title}" required="required" />
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: screencastInstance, field: 'description', 'error')}">
                        <label class="col-sm-2 control-label" for="description"><g:message code="screencast.description" /></label>
                        <div class="col-sm-10">
                            <g:textArea class="form-control input-fullsize" cols="30" rows="10" name="description" required="required" value="${screencastInstance?.description}" />
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: screencastInstance, field: 'videoId', 'error')}">
                        <label class="col-sm-2 control-label" for="videoId"><g:message code="screencast.videoId" /></label>

                        <div class="col-sm-10">
                            <g:select class="form-control" name="videoHost.id" from="${org.grails.learn.screencasts.VideoHost.list()}"
                                      value="${screencastInstance?.videoHost?.id}" optionKey="id" optionValue="name" />
                            <g:textField name="videoId" value="${screencastInstance?.videoId}" class="input-large"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: screencastInstance, field: 'tags', 'error')}">
                        <label class="col-sm-2 control-label" for="tags"><g:message code="screencast.tags" /></label>
                        <div class="col-sm-10">
                            <g:textField class="form-control input-fullsize" name="tags" value="${screencastInstance?.tags?.join(', ')}" />
                            <p class="help-block">Examples: introduction, security, screencast</p>
                        </div>
                    </div>

                    <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
                        <g:submitButton name="submit" value="Submit for Approval" class="btn"/>
                    </div></div>
                </fieldset>

            </g:form>

        </article>
    </section>


</body>
