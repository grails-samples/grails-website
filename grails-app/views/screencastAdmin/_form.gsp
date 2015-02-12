<div class="control-group">
    <label class="col-sm-2 control-label">Submitter</label>

    <div class="col-sm-10">
        <label class="checkbox" style="padding-left: 0;">
            <g:link controller="user" action="show" id="${screencastInstance?.submittedBy?.id}">
                <avatar:gravatar email="${screencastInstance?.submittedBy?.email}"
                                 size="16"/> ${screencastInstance?.submittedBy?.email}
            </g:link>
        </label>
    </div>
</div>

<g:if test="${!screencastInstance.id}">
    <div class="control-group">
        <label class="col-sm-2 control-label" for="title">Auto Approve?</label>

        <div class="col-sm-10">
            <label class="checkbox" for="autoApprove">
                <g:checkBox name="autoApprove"/>
                Check this box if you want the website to appear immediately on the websites page.
            </label>
        </div>
    </div>
</g:if>

<div class="control-group ${hasErrors(bean: screencastInstance, field: 'title', 'error')}">
    <label class="col-sm-2 control-label" for="title">Title</label>

    <div class="col-sm-10">
        <g:textField name="title" value="${screencastInstance?.title}" class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: screencastInstance, field: 'description', 'error')}">
    <label class="col-sm-2 control-label" for="description">Description</label>

    <div class="col-sm-10">
        <g:textArea cols="30" rows="5" name="description" value="${screencastInstance?.description}"
                    class="input-xxlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: screencastInstance, field: 'videoId', 'error')}">
    <label class="col-sm-2 control-label" for="videoId">Video ID</label>

    <div class="col-sm-10">
        <g:select name="videoHost.id" from="${org.grails.learn.screencasts.VideoHost.list()}"
                  value="${screencastInstance?.videoHost?.id}" optionKey="id" optionValue="name" />
        <g:textField name="videoId" value="${screencastInstance?.videoId}" class="input-xlarge"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: screencastInstance, field: 'tags', 'error')}">
    <label class="col-sm-2 control-label" for="tags">Tags</label>

    <div class="col-sm-10">
        <g:textField name="tags" value="${screencastInstance?.tags?.join(', ')}" class="input-xxlarge"/>
        <div class="hint">Examples: introduction, security, screencast</div>
    </div>
</div>
