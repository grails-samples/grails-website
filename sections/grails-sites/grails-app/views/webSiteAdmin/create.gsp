<html>
<head>
    <meta content="admin" name="layout"/>
</head>

<body>

<h1 class="page-header">
    Create WebSite
    <span class="pull-right">
        <g:link class="btn" action="list">WebSite List</g:link>
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

<g:uploadForm name="uploadArtifactForm" action="save" class="form-horizontal">
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
            </div>
        </div>

        <div class="form-actions">
            <g:submitButton name="create" class="btn btn-primary" value="Create" />
            <g:link class="btn" action="list">Cancel</g:link>
        </div>

    </fieldset>
</g:uploadForm>

</body>
</html>
