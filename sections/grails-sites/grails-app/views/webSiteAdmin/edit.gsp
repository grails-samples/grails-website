<html>
<head>
    <meta content="admin" name="layout"/>
</head>

<body>

<h1 class="page-header">
    Edit WebSite
    <span class="pull-right">
        <g:link class="btn" action="list">WebSite List</g:link>
        <g:link class="btn" action="create">Create WebSite</g:link>
    </span>
</h1>

<g:if test="${flash.message}">
    <div class="alert alert-info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${webSite}">
    <div class="alert alert-error">
        <g:renderErrors bean="${webSite}" as="list"/>
    </div>
</g:hasErrors>

<g:form method="post" class="form-horizontal" enctype="multipart/form-data">

    <g:hiddenField name="id" value="${webSite.id}"/>
    <g:hiddenField name="version" value="${webSite.version}"/>

    <fieldset>

        <div class="control-group ${hasErrors(bean: webSite, field: 'title', 'error')}">
            <label class="control-label" for="title">Title</label>
            <div class="controls">
                <g:textField name="title" value="${webSite.title}" class="input-xxlarge" />
            </div>
        </div>

        <div class="control-group ${hasErrors(bean: webSite, field: 'description', 'error')}">
            <label class="control-label" for="title">Description</label>
            <div class="controls">
                <g:textArea cols="40" rows="5" name="description" value="${webSite.description}" class="input-xxlarge" />
            </div>
        </div>

        <div class="control-group ${hasErrors(bean: webSite, field: 'url', 'error')}">
            <label class="control-label" for="url">URL</label>
            <div class="controls">
                <g:textField name="url" value="${webSite.url}" class="input-xxlarge" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="featured">Featured?</label>
            <div class="controls">
                <label class="checkbox">
                    <g:checkBox name="featured" value="${webSite.featured}"/>
                    If this is checked, the website is considered 'Featured'
                </label>
            </div>
        </div>

        <div class="control-group ${hasErrors(bean: webSite, field: 'title', 'error')}">
            <label class="control-label" for="title">Tags</label>
            <div class="controls">
                <g:textField name="tags" value="${params.tags}" class="input-xxlarge" />
                <p class="help-block">Examples: startup, nosql, high traffic</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="preview">Preview Image</label>
            <div class="controls">
                <input class="input-file" name="preview" id="previewImage" type="file">
                <bi:hasImage bean="${webSite}">
                    <bi:img size="large" bean="${webSite}"/>
                </bi:hasImage>
            </div>
        </div>

        <div class="form-actions">
            <g:submitButton name="update" class="btn btn-primary"
                            value="Update"/>
            <g:actionSubmit class="btn btn-danger" action="delete"
                            value="Delete"
                            onclick="return confirm('Are you sure?');"/>
            <g:link class="btn" action="list">Cancel</g:link>
        </div>

    </fieldset>
</g:form>

</body>
</html>