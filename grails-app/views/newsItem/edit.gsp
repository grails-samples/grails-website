<head>
    <meta content="masterv2" name="layout"/>
    <title>Edit a Grails News Item</title>
    <asset:stylesheet src="codeMirror"/>
    <asset:javascript src="codeMirror"/>
    <asset:stylesheet src="fancyBox"/>
    <asset:javascript src="fancyBox"/>
</head>

<body>

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



            <g:form url="[action:'edit', id:newsItem.id]" class="content-form">
                <input type="hidden" name="id" value="${newsItem.id}"></input>
                <fieldset>
                    <div class="control-group ${hasErrors(bean: newsItem, field: 'title', 'error')}">
                        <label class="col-sm-2 control-label" for="title"><g:message code="news.title" /></label>
                        <div class="col-sm-10">
                            <g:textField class="form-control input-fullsize" name="title" value="${newsItem?.title}" required="required" />
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: newsItem, field: 'body', 'error')}">
                        <label class="col-sm-2 control-label" for="body"><g:message code="news.body" /></label>
                        <div class="col-sm-10">
                            <g:textArea class="form-control input-fullsize" cols="30" rows="10" name="body" id="wikiPageBody" value="${newsItem?.body}" />
                        </div>
                    </div>

                    <div class="form-group"><div class="col-sm-offset-2 col-sm-10">
                        <g:submitButton name="submit" value="Update" class="btn"/>
                    </div></div>
                </fieldset>

            </g:form>

            <g:render template="/content/wikiCodeMirrorJavaScript"></g:render>
        </article>
    </section>


</body>
