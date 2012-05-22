<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/learn/sideNav"/>

    <div id="main" class="websites">
        <section>
            <article>
                <h3>Add Tutorial</h3>

                <p>A tutorial on the Grails.org website is a short description and a link to a Grails-related tutorial.</p>

                <flash:message flash="${flash}" bean="${tutorialInstance}"/>

                <g:hasErrors bean="${tutorialInstance}">
                    <div class="alert alert-error">
                        <g:renderErrors bean="${tutorialInstance}" as="list"/>
                    </div>
                </g:hasErrors>

                <g:form action="save">

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'title', 'error')}">
                        <label class="control-label" for="title">Title</label>
                        <div class="controls">
                            <g:textField name="title" value="${tutorialInstance?.title}" class="input-xxlarge"/>
                        </div>
                    </div>

                    <div class="control-group ${hasErrors(bean: tutorialInstance, field: 'description', 'error')}">
                        <label class="control-label" for="description">Description</label>
                        <div class="controls">
                            <g:textArea cols="30" rows="4" name="description"
                                        value="${tutorialInstance?.description}" class="input-xxlarge"/>
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

                    <div>
                        <g:submitButton name="submit" value="Submit for Approval" class="btn"/>
                    </div>

                </g:form>

            </article>
        </section>
    </div>
</div>

</body>
</html>