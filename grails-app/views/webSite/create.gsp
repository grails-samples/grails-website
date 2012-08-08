<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="community"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>

    <div id="main" class="websites">
        <section>
            <article>
                <h2>Add Web Site</h2>

                <p>Fill out this form to submit your web site for approval to appear on the websites page.</p>

                <flash:message flash="${flash}" bean="${webSiteInstance}"/>

                <g:hasErrors bean="${webSiteInstance}">
                    <div class="alert alert-error">
                        <g:renderErrors bean="${webSiteInstance}" as="list"/>
                    </div>
                </g:hasErrors>

                <g:uploadForm action="save" class="content-form padding-top">

                    <div class="control-group ${hasErrors(bean: webSiteInstance, field: 'title', 'error')}">
                        <label class="control-label" for="title">Title</label>
                        <div class="controls">
                            <g:textField name="title" value="${webSiteInstance?.title}" class="input-fullsize"/>
                        </div>
                    </div>


                    <div class="control-group ${hasErrors(bean: webSiteInstance, field: 'shortDescription', 'error')}">
                        <label class="control-label" for="shortDescription">Short Description</label>
                        <div class="controls">
                            <g:textField name="shortDescription" value="${webSiteInstance?.shortDescription}" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: webSiteInstance, field: 'url', 'error')}">
                        <label class="control-label" for="description">Description</label>
                        <div class="controls">
                            <g:textArea cols="30" rows="4" name="description"
                                        value="${webSiteInstance?.description}" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: webSiteInstance, field: 'url', 'error')}">
                        <label class="control-label" for="url">URL</label>
                        <div class="controls">
                            <g:textField name="url" value="${webSiteInstance?.url}" class="input-fullsize"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">Preview Image</label>

                        <div class="controls">
                            <input type="file" name="preview"/>
                            <div class="hint">Accepted file types: png, gif, jpg</div>
                        </div>
                    </div>

                    <div class="form-actions">
                        <g:submitButton name="submit" value="Submit for Approval" class="btn"/>
                    </div>

                </g:uploadForm>

            </article>
        </section>
    </div>
</div>

</body>
</html>