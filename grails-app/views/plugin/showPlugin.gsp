<%@ page import="org.grails.plugin.Plugin" %>
<g:setProvider library="yui"/>

<html>
<head>
    <meta name="layout" content="pluginDetails">
</head>
<body>
	<div id="pluginDetailsBox">
		<div id="pluginDetailsTop"></div>
		<div id="pluginDetailsContainer">
			<g:if test="${plugin.zombie}">
			<div id="pluginZombie">
				<h1><g:message code="plugin.zombie"/></h1>
			<!%-- TODO Add 'Revive' link --%>
			</div>
			</g:if>
			
			<gui:dialog id='loginDialog' title="Login required" modal="true">
			    <div id='loginFormDiv'></div>
			</gui:dialog>

			<div id="downloadBox">
				<a href="${plugin.downloadUrl}"><r:img uri="/images/new/plugins/Buttons/downloadBox_btn.png" alt="Download" border="0"/></a>
			</div>

			<h1 id="pluginBoxTitle">${plugin?.title?.encodeAsHTML()}</h1>

			<div class="ratingBox">
			    <shiro:isLoggedIn>
					<rateable:ratings bean="${plugin}"/>						
			    </shiro:isLoggedIn>
			    <shiro:isNotLoggedIn>
					<div id="ratingDisplay">
						<rateable:ratings bean="${plugin}" active="false" href="${createLink(controller:'user', action:'login', params:[originalURI:request.forwardURI])}"/>																							
					</div>
			    </shiro:isNotLoggedIn>
			</div>
		    <div class="pluginDetail">
				<g:if test="${plugin.usage}">
				<div class="pluginUsage">
				    <div>Used by approximately</div>
					<div class="value"><g:formatNumber number="${plugin.usage}" type="percent"/></div>
					<div>of Grails users</div>
				</div>
				</g:if>
		        <table>
		            <tr>
		                <th>Author(s)</th>
		                <td>
		                	<g:each in="${plugin.authors}" var="author" status="i">
								<g:if test="${i}">&#183;</g:if>
								${author.name.encodeAsHTML()}
							</g:each>
		                </td>
		            </tr>
		            <tr>
		                <th>Current Release</th>
		                <td>${plugin.currentRelease?.encodeAsHTML()}<g:if test="${plugin.lastReleased}">&nbsp;&nbsp;&nbsp;(<prettytime:display date="${plugin.lastReleased}"/>)</g:if></td>
		            </tr>
		            <tr>
		                <th>Grails Version</th>
		                <td>${plugin.grailsVersion?.encodeAsHTML() ?: '?'}</td>
		            </tr>
					<g:if test="${plugin.licenses?.size()}">
					<tr>
						<th>License(s)</th>
						<td>${plugin.licenses.sort { it.name }.collect { l -> '<a href="' + l.url.encodeAsHTML() + '">' + l.name.encodeAsHTML() + '</a>' }.join(', ') }</td>
					</tr>
					</g:if>
					<g:if test="${plugin.organization}">
					<tr>
						<th>Organization</th>
						<td>
						<g:if test="${plugin.organizationUrl}"><a href="${plugin.organizationUrl.encodeAsHTML()}"></g:if>
						${plugin.organization.encodeAsHTML()}
						<g:if test="${plugin.organizationUrl}"></a></g:if>
					</tr>
					</g:if>
		            <tr>
		                <th>Tags</th>
		                <td class='tags'>
		                    <span id='pluginTags'>
		                        <g:render template='tags' var='plugin' bean="${plugin}"/>
		                    </span>
		                    <span id='addTagTrigger'><r:img uri="/images/famfamfam/add.png"/></span>
		                </td>
		            </tr>
		        </table>
		    </div>
		    <ul class="links">
		        <tmpl:pluginLinks plugin="${plugin}" />
		        <li>
		            <g:link controller="plugin" action="editPlugin" id="${plugin.id}">
                        <r:img uri="/images/new/plugins/icons/16x16_icons/edit.png" border="0" />&nbsp;Edit Plugin
                    </g:link>
                </li>
		    </ul>
		</div>

		<div class="description">
                    <wiki:text key="${'pluginInfo_' + plugin?.name}">${plugin?.summary}</wiki:text>
                </div>
		
	</div>

	
</div>



    %{--
        Logged in users will be able to add tags
    --}%
    <shiro:isLoggedIn>
        <gui:dialog id='addTagDialog'
            title='Add Tags'
            form='true' url="${createLink(controller:'plugin', action:'addTag', params:[id:plugin.id])}"
            triggers="[show:[id:'addTagTrigger',on:'click']]"
            update='pluginTags'
        >
            <gui:autoComplete id='newTag'
                controller='tag' action='autoCompleteNames'
                resultName='tagResults'
                labelField='name'
                minQueryLength='1'
                queryDelay='1'
            />
        </gui:dialog>

        <script>
            YAHOO.util.Event.onDOMReady(function() {
                // on show, put the dialog in the right place
                GRAILSUI.addTagDialog.subscribe('show', function() {
                    var pos = YAHOO.util.Dom.getXY('addTagTrigger');
                    this.cfg.setProperty('x',pos[0]+20);
                    this.cfg.setProperty('y',pos[1]+20);
                });
                // on hide, clear out the text within it
                GRAILSUI.addTagDialog.subscribe('hide', function() {
                    document.getElementById('newTag').value = ''
                });
            });
        </script>
    </shiro:isLoggedIn>
    %{-- Unauthenticated users get defered to the login screen --}%
    <shiro:isNotLoggedIn>
        <script>
            YAHOO.util.Event.onDOMReady(function() {
                // on show, put the dialog in the right place
                YAHOO.util.Event.on('addTagTrigger', 'click', function() {
                    window.location = "${createLink(controller:'user', action:'login', params:[originalURI:request.forwardURI])}";
                });
                // also hang up rating click if not logged in, redirect to login page with originalURI of this page
                // for redirect
                YAHOO.util.Event.on('ratingdiv', 'click', function(e) {
                    YAHOO.util.Event.stopEvent(e);
                    window.location = "${createLink(controller:'user', action:'login', params:[originalURI:request.forwardURI])}";
                });
            });
        </script>
    </shiro:isNotLoggedIn>

	<div id="pluginContent">
	    <cache:text key="pluginTabs_${plugin.id}">
	        <gui:tabView>
	            <g:each var="wiki" in="${Plugin.WIKIS}">
	                <gui:tab id="${wiki}Tab" label="${wiki[0].toUpperCase() + wiki[1..-1]}" active="${wiki == 'description'}">
                            <g:include controller="pluginTab" action="index"
                                       id="${plugin[wiki].title}" params="[_ul: wiki + 'Tab']"/>
	                </gui:tab>
	            </g:each>
	        </gui:tabView>
		</cache:text>		
	</div>
</body>
</html>
