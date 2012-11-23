<div class="control-group">
    <label class="control-label">Submitter</label>

    <div class="controls">
        <label class="checkbox" style="padding-left: 0px;">
            <g:link controller="user" action="show" id="${tutorialInstance?.submittedBy?.id}">
                <avatar:gravatar email="${tutorialInstance?.submittedBy?.email}"
                                 size="16"/> ${tutorialInstance?.submittedBy?.email}
            </g:link>
        </label>
    </div>
</div>

<g:if test="${!tutorialInstance.id}">
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

<div class="control-group ${hasErrors(bean: tutorialInstance, field: 'title', 'error')}">
    <label class="control-label" for="title">Title</label>

    <div class="controls">
        <g:textField name="title" value="${tutorialInstance?.title}" class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: tutorialInstance, field: 'description', 'error')}">
    <label class="control-label" for="description">Description</label>

    <div class="controls">
        <g:textArea cols="30" rows="5" name="description" value="${tutorialInstance?.description}"
                    class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: tutorialInstance, field: 'url', 'error')}">
    <label class="control-label" for="url">URL</label>

    <div class="controls">
        <g:textField name="url" value="${tutorialInstance?.url}" class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: tutorialInstance, field: 'tags', 'error')}">
    <label class="control-label" for="tags">Tags</label>

    <div class="controls">
        <g:textField name="tags" value="${tutorialInstance?.tags?.join(', ')}" class="input-xxlarge"/>
        <div class="hint">Examples: introduction, security, screencast</div>
    </div>
</div>
