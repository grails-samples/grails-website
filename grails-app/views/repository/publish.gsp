<%@ page import="org.grails.wiki.WikiPage" %>
<head>
    <meta content="master" name="layout"/>
    <title>Publish Plugin</title>
    <r:require modules="plugin"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <div class="aside">
        <g:render template="/plugin/sideSubmission"/>
    </div>

    <section id="main" class="items">
    <div id="editPane">
        <h1>Publish Plugin</h1>
        
        <g:if test="${message}">
                <div class="alert">${message}</div>
        </g:if>        

        <g:hasErrors bean="${publishCommand}">
            <div id="errors" class="errors">
                <g:renderErrors bean="${publishCommand}"></g:renderErrors>
            </div>
        </g:hasErrors>

        <g:uploadForm name="publishPlugin" url="[controller:'repository', action:'publish']" method="POST" class="content-form padding-top">
            <input type="hidden" name="format" value="html" />

            <common:control field="plugin" bean="${publishCommand}" title="Plugin Name:">
                <g:textField name="plugin" value="${publishCommand?.plugin}" required="required" class="input-fullsize"/>
            </common:control>       
            
            <common:control field="version" bean="${publishCommand}" title="Plugin Version:">
                <g:textField name="version" value="${publishCommand?.version}" required="required" class="input-fullsize"/>
            </common:control>                          

            <common:control field="zip" bean="${publishCommand}" title="Plugin ZIP:">
                <g:field type="file" name="zip" class="input-fullsize"/>                
            </common:control>       
            
            <common:control field="pom" bean="${publishCommand}" title="Plugin POM:">
                <g:field type="file" name="pom" class="input-fullsize"/>                
            </common:control>                           

            <common:control field="xml" bean="${publishCommand}" title="Plugin XML:">
                <g:field type="file" name="xml" class="input-fullsize"/>                
            </common:control>                           
                    
            <g:submitButton name="publish" value="Publish" class="btn" />
        </g:uploadForm>
    </div>
    </section>

</div>

</body>
