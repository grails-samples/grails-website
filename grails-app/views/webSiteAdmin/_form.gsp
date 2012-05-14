<div class="control-group">
    <label class="control-label">Submitter</label>

    <div class="controls">
        <label class="checkbox" style="padding-left: 0px;">
            <g:link controller="user" action="show" id="${webSiteInstance?.submittedBy?.id}">
                <avatar:gravatar email="${webSiteInstance?.submittedBy?.email}"
                                 size="16"/> ${webSiteInstance?.submittedBy?.email}
            </g:link>
        </label>
    </div>
</div>

<g:if test="${!webSiteInstance.id}">
    <div class="control-group">
        <label class="control-label" for="title">Auto Approve?</label>

        <div class="controls">
            <label class="checkbox" for="autoApprove">
                <g:checkBox name="autoApprove"/>
                Check this box if you want the website to appear immediately on the websites page.
            </label>
        </div>
    </div>
</g:if>

<div class="control-group ${hasErrors(bean: webSiteInstance, field: 'title', 'error')}">
    <label class="control-label" for="title">Title</label>

    <div class="controls">
        <g:textField name="title" value="${webSiteInstance?.title}" class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: webSiteInstance, field: 'shortDescription', 'error')}">
    <label class="control-label" for="shortDescription">Short Description</label>

    <div class="controls">
        <g:textField name="shortDescription" value="${webSiteInstance?.shortDescription}" class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: webSiteInstance, field: 'title', 'error')}">
    <label class="control-label" for="description">Description</label>

    <div class="controls">
        <g:textArea cols="30" rows="5" name="description" value="${webSiteInstance?.description}"
                    class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: webSiteInstance, field: 'url', 'error')}">
    <label class="control-label" for="url">URL</label>

    <div class="controls">
        <g:textField name="url" value="${webSiteInstance?.url}" class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: webSiteInstance, field: 'featured', 'error')}">
    <label class="control-label" for="featured">Is Featured?</label>

    <div class="controls">
        <label class="checkbox" for="featured">
            <g:checkBox name="featured" value="${webSiteInstance?.featured}"/>
            Check this box if the website is featured on the <a href="/community/websites"
                                                                target="_blank">/community/websites</a> page.
        </label>
    </div>
</div>

<div class="control-group">
    <label class="control-label">Preview Image</label>

    <div class="controls">
        <input type="file" name="preview"/>

        <div class="hint">Accepted file types: png, gif, jpg</div>
    </div>
</div>
