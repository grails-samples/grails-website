<g:setProvider library="jquery" plugin="jquery"/>
<head>
    <plugin:isAvailable name="feeds">
        <feed:meta kind="rss" version="2.0" controller="webSite" action="feed"/>		
    </plugin:isAvailable>
    <title><g:message code="website.home.title" default="Web Sites"></g:message></title>
</head>
<body>
    <div class="artifactControls">
        <div class="menuButton"><g:link class="create" controller="webSite" action="create"><g:message code="grails.website.add.link" default="Add Web Site"></g:message></g:link></div>

        <plugin:isAvailable name="feeds">
            <div class="menuButton">
                <g:link class="feed" controller="webSite" action="feed" params="[format:'rss']">
                    <g:message code="grails.website.rss.link" 
                               default="RSS Feed"></g:message>
                </g:link>
            </div>			
        </plugin:isAvailable>
    </div>
    <div class="body">
        <div class="artifacts">
            <g:each in="${artifacts}" var="webSite" status="i">
            <div class="artifact">
                <g:render template="/webSite/siteSummary" model="[i: i, webSite: webSite]" plugin="grailsSites"/>
            </div>
            </g:each>
            <div class="paginateButtons">
                <g:paginate total="${total}"></g:paginate>
            </div>				
        </div>
            
    </div>
</body>
