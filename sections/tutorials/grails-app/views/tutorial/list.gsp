<g:setProvider library="jquery" plugin="jquery"/>
<head>
    <plugin:isAvailable name="feeds">
        <feed:meta kind="rss" version="2.0" action="feed"/>		
    </plugin:isAvailable>
    <title><g:message code="tutorials.home.title" default="Tutorials"></g:message></title>
</head>
<body>
    <div class="artifactControls">
        <div class="menuButton"><g:link class="create" action="create"><g:message code="grails.tutorials.add.link" default="Add Tutorial"></g:message></g:link></div>

        <plugin:isAvailable name="feeds">
            <div class="menuButton">
                <g:link class="feed" action="feed" params="[format:'rss']">
                    <g:message code="grails.tutorials.rss.link" 
                               default="RSS Feed"></g:message>
                </g:link>
            </div>			
        </plugin:isAvailable>
    </div>
    <div class="body">
        <div class="artifacts">
            <g:each in="${artifacts}" var="artifact" status="i">
            <div class="artifact">
                <g:render template="listElement" model="[i: i, artifact: artifact]"/>
            </div>
            </g:each>
            <div class="paginateButtons">
                <g:paginate total="${total}"></g:paginate>
            </div>				
        </div>
            
    </div>
</body>
