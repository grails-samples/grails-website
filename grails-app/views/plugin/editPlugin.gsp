<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="plugin"/>
    <r:require modules="content, codeMirror, fancyBox"/>
    <g:render template="tagSetup" model="[allTags:allTags]" />
    <style type="text/css">
.wiki-form  .CodeMirror {
        border: 1px solid #eee;
        width: 563px;
    
}


    </style>
</head>

<body>

<div id="content" class="content-aside-2" role="main">

    <g:render template="sideNav"/>

    <div id="main" class="plugins">

        <g:render template="searchBar" />

        <section class="plugin">
            <article>
                <header>
                    <h2>
                        Edit ${plugin?.title?.encodeAsHTML()}
                        <g:if test="${plugin.official}">
                            <small>supported by SpringSource</small>
                        </g:if>
                    </h2>
                    <ul class="meta">
                        <li>Current release : <strong>${plugin.currentRelease.encodeAsHTML()}</strong> • Grails version : ${plugin.grailsVersion.encodeAsHTML() ?: '*'}</li>
                        <li>Authors : <%= plugin.authors.collect { it.name.encodeAsHTML() }.join(', ') %></li>
                        <g:if test="${plugin.licenses}">
                        <li>License : <%= plugin.licenses.collect { '<a href="' + it.url.encodeAsHTML() + '">' + it.name + '</a>' }.join(',') %></li>
                        </g:if>
                        <g:if test="${plugin.organization}">
                        <li>Organization : 
                            <g:if test="${plugin.organizationUrl}">
                            <a href="${plugin.organizationUrl.encodeAsHTML()}">${plugin.organization.encodeAsHTML()}</a>
                            </g:if>
                            <g:else>
                            ${plugin.organization.encodeAsHTML()}
                            </g:else>
                        </li>
                        </g:if>
                    </ul>
                </header>
                <g:if test="${flash.message}">
                    <div class="alert alert-info">${flash.message}</div>
                </g:if>                    
                
                <div class="documentation">
                    <p><g:message code="wiki.create.description"/></p>
                    <g:form class="wiki-form content-form" name="wiki-form" url="[action: 'updatePlugin', id: plugin?.name]"
                            method="post">
                        <g:hiddenField name="id" value="${plugin?.name}"/>
                        <fieldset>
                            <section>
                                <h2>Summary</h2>
                                
                                <div class="control-group ${hasErrors(bean: plugin.summary, field: 'body', 'error')}">
                                    <div class="controls">
                                        <g:textField name="plugin.summary" value="${plugin.summary}" class="input-xxlarge"/>
                                    </div>
                                </div>

                            </section>    
                            <section>
                                <h2>Installation</h2>
                                
                                <div class="control-group ${hasErrors(bean: plugin.installation, field: 'body', 'error')}">
                                    <div class="controls">
                                        <g:hiddenField name="plugin.installation.version" value="${plugin?.installation?.version}"/>
                                        <g:textArea cols="30" rows="20" name="plugin.installation.body"
                                                    value="${plugin?.installation.body}" class="codeEditor input-medium"/>
                                    </div>
                                </div>

                            </section>                                                          
                             <section>
                                <h2>Description</h2>
                                
                                <div class="control-group ${hasErrors(bean: plugin.description, field: 'body', 'error')}">
                                    <div class="controls">
                                        <g:hiddenField name="plugin.description.version" value="${plugin?.description?.version}"/>
                                        <g:textArea cols="30" rows="20" name="plugin.description.body"
                                                    value="${plugin?.description.body}" class="codeEditor input-medium"/>
                                    </div>
                                </div>

                            </section>    

                              <div class="form-actions">

                                <g:submitButton name="updatePlugin" class="btn btn-primary" value="Update"/>
                                <g:link class="btn" uri="/plugin/${plugin.name}">Cancel</g:link>
                            </div>                    
             
                        </fieldset>

                    </g:form>     
                    <r:script>
                        $(function () {
                            $('textarea').each(function(e) {
                                var myCodeMirror = CodeMirror.fromTextArea(this, {
                                    lineNumbers: true,
                                    wordWrap: true,
                                    lineWrapping: true,
                                    gutter: true,
                                    fixedGutter: true,
                                    autofocus: true
                                });                            
                            })

                        });
                    </r:script>                    
               </div>
            </article>
        </section>
        <r:script>
            tagsInitialized = true
        </r:script>
        
    </div>
</div>

</body>
</html>
