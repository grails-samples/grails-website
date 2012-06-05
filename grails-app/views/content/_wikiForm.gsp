<h2><g:message code="wiki.edit.title" args="${[wikiPage?.title]}" /></h2>
<p><g:message code="wiki.edit.description" /></p>

<flash:message flash="${flash}" bean="${wikiPage}"/>

<g:form class="content-form" name="wiki-form" url="[action: 'saveWikiPage', id: wikiPage.title]" method="post">
    <g:hiddenField name="title" value="${wikiPage?.title}" />
    <input type="hidden" name="version" value="${wikiPage?.version}" />
    <fieldset>

        <div class="control-group ${hasErrors(bean: wikiPage, field: 'body', 'error')}">
            <div class="controls">
                <g:textArea cols="30" rows="20" id="wikiPageBody" name="body" required="required" value="${wikiPage?.body}" class="input-fullsize"/>
            </div>
        </div>

        <div class="form-actions">
            <span class="pull-right">
                <a href="/${wikiPage.title}" class="btn">Cancel</a>
            </span>
            <g:submitButton name="submit" value="Save Changes" class="btn"/>
            <a class="btn preview">Preview</a>
        </div>
    </fieldset>

</g:form>
