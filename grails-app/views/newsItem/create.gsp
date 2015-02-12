<head>
    <meta content="masterv2" name="layout"/>
    <title>Submit a Grails News Item</title>
    <asset:stylesheet src="codeMirror"/>
    <asset:javascript src="codeMirror"/>
    <asset:stylesheet src="fancyBox"/>
    <asset:javascript src="fancyBox"/>
</head>
<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>

    <section id="main">
        <article>

            <h2><g:message code="news.submit.title" /></h2>
            <p><g:message code="news.submit.description" /></p>

            <flash:message flash="${flash}" bean="${newsItem}"/>

            <g:hasErrors bean="${newsItem}">
                <div class="alert alert-error">
                    <g:renderErrors bean="${newsItem}" as="list"/>
                </div>
            </g:hasErrors>

            <g:form action="create" class="content-form">
                <fieldset>
                    <div class="control-group ${hasErrors(bean: newsItem, field: 'title', 'error')}">
                        <label class="col-sm-2 control-label" for="title"><g:message code="news.title" /></label>
                        <div class="col-sm-10">
                            <g:textField name="title" value="${newsItem?.title}" required="required" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: newsItem, field: 'body', 'error')}">
                        <label class="col-sm-2 control-label" for="body"><g:message code="news.body" /></label>
                        <div class="col-sm-10">
                            <g:textArea cols="30" rows="10" name="body" id="wikiPageBody" value="${newsItem?.body}" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="form-actions">
                        <g:submitButton name="submit" value="Submit for Approval" class="btn"/>
                    </div>
                </fieldset>

            </g:form>

            <g:render template="/content/wikiCodeMirrorJavaScript"></g:render>
        </article>
    </section>
</div>

</body>
