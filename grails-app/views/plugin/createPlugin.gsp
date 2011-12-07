<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>New Plugin</title>
    <meta name="layout" content="pluginDetails">
    <style>
.pluginBoxBottom {
    top: -11px;
}
    </style>
</head>
<body>
    <div id="pluginDetailsBox" align="center">
        <div id="pluginDetailsTop"></div>
        <div id="pluginDetailsContainer">
            <h1 id="pluginBoxTitle">Create Plugin</h1>
            <div class="pluginDetail">

                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${plugin}">
                    <div class="errors">
                        <g:renderErrors bean="${plugin}" as="list"/>
                    </div>
                </g:hasErrors>

                <g:form name="createPlugin" action="createPlugin">
                    <table>
                        <tbody>

                        <plugin:input
                                name="Name"
                                description="This is what you would type in the command line after 'grails install-plugin'.">
                            <input type="text" id="name" name="name" value="${fieldValue(bean: plugin, field: 'name')}"/>
                        </plugin:input>


                        <plugin:input
                                name="Title"
                                description="The 'Human-Readable' title of your plugin">
                            <input type="text" id="title" name="title" value="${fieldValue(bean: plugin, field: 'title')}"/>
                        </plugin:input>

                        <plugin:input
                                name="Description"
                                description="A description/summary of the plugin">
                            <textarea id="summary" name="summary">${fieldValue(bean: plugin, field: 'summary')}</textarea>
                        </plugin:input>
                        
                        <plugin:input
                                name="Author"
                                description="Plugin author's name(s)">
                            <input type="text" id="author" name="author" value="${fieldValue(bean: plugin, field: 'author')}"/>
                        </plugin:input>

                        <plugin:input
                                name="Author Email"
                                description="Plugin author email addresses">
                            <input type="text" id="authorEmail" name="authorEmail" value="${fieldValue(bean: plugin, field: 'authorEmail')}"/>
                        </plugin:input>

                        <plugin:input
                                name="Grails Version"
                                description="The Grails version this plugin was developed under.">
                            <input type="text" id="grailsVersion" name="grailsVersion" value="${fieldValue(bean: plugin, field: 'grailsVersion')}"/>
                        </plugin:input>

                        <plugin:input
                                name="Current Release"
                                description="Current plugin release">
                            <input type="text" id="currentRelease" name="currentRelease" value="${fieldValue(bean: plugin, field: 'currentRelease')}"/>
                        </plugin:input>

                        <plugin:input
                                name="Documentation URL"
                                description="Where on the web are your docs? If this is it, then leave blank.">
                            <input type="text" id="documentationUrl" name="documentationUrl" value="${fieldValue(bean: plugin, field: 'documentationUrl')}"/>
                        </plugin:input>

                        <plugin:input
                                name="Download URL"
                                description="Where someone would click to get your plugin">
                            <input type="text" id="downloadUrl" name="downloadUrl" value="${fieldValue(bean: plugin, field: 'downloadUrl')}"/>
                        </plugin:input>
                                        <plugin:input name="Source URL" description="Where is the source code for your plugin? Links to browseable code preferred.">
                                            <input type="text" id="scmUrl" name="scmUrl" value="${fieldValue(bean: plugin, field: 'scmUrl')}"/>
                                        </plugin:input>
                        <plugin:input name="Issues URL" description="Where is the issue tracker for your plugin?">
                            <input type="text" id="issuesUrl" name="issuesUrl" value="${fieldValue(bean: plugin, field: 'issuesUrl')}"/>
                        </plugin:input>
                        </tbody>
                    </table>
                    <ul class="actionBar">
                        <li><g:submitButton name="save" value="Save"/></li>
                        <li><g:link action="home">Cancel</g:link></li>
                    </ul>
                </g:form>
            </div>
        </div>
    </div>
</body>
</html>
