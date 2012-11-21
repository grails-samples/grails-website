<%@ page import="org.grails.wiki.WikiPage" %>
<head>
    <title>Publish Plugin</title>
    <meta content="subpage" name="layout" />
</head>
<body>
    <div id="editPane">
        <h1>Publish Plugin</h1>
        <g:if test="${message}">
            <div class="message">${message}</div>
        </g:if>

        <g:hasErrors bean="${publishCommand}">
            <div id="errors" class="errors">
                <g:renderErrors bean="${publishCommand}"></g:renderErrors>
            </div>
        </g:hasErrors>

        <g:uploadForm name="publishPlugin" url="[controller:'repository', action:'publish']" method="POST">
            <input type="hidden" name="format" value="html" />
            Plugin Name:&nbsp;&nbsp;&nbsp;<g:textField id="plugin" name="plugin" value="${publishCommand?.plugin}" /> <br /><br/>

            Plugin Version:&nbsp;&nbsp;&nbsp;<g:textField id="version" name="version" value="${publishCommand?.version}" /> <br /><br/>
            Plugin ZIP:&nbsp;&nbsp;&nbsp;<g:field type="file" name="zip" /> <br /><br/>
            Plugin POM:&nbsp;&nbsp;&nbsp;<g:field type="file" name="pom" /> <br /><br/>
            Plugin XML:&nbsp;&nbsp;&nbsp;<g:field type="file" name="xml" /> <br /><br/>
            <g:submitButton name="publish" value="Publish" />
        </g:uploadForm>
    </div>
</body>
