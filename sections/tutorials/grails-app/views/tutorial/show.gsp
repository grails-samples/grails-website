<%@page defaultCodec="HTML"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <g:if test="${layout}">
        <meta name="layout" content="${layout}" />
        </g:if>
        <title>Screencast: ${screencast.title}</title>
		<plugin:isAvailable name="grails-ui">
			<gui:resources components="richEditor"/>		
		</plugin:isAvailable>
    </head>
    <body>
        <div class="body">
	        <div class="screencastControls">		
	            <div class="menuButton"><g:link class="list" action="list">Screencast List</g:link></div>
	            <div class="menuButton"><g:link class="edit" action="edit" id="${screencast.id}">Edit Screencast</g:link></div>	
	        </div>
			<g:render template="/screencast/screencast" plugin="screencasts" model="[screencast:screencast, showThumbnail:true]"></g:render>			
			
			<div class="screencastComments">
				<h2>Comments:</h2>
				<comments:render bean="${screencast}" />				
			</div>
        </div>
    </body>
</html>
