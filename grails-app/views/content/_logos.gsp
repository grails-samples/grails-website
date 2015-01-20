<div id="springSourceBar">
	<div id="grailsLogo">
		<a href="/"><asset:image href="new/grailslogo_topNav.png" border="0"/></a>			
	</div>

	<div id="springSourceLogo">
		<a href="http://www.springsource.com/"><asset:image href="new/springsource.png" border="0"/></a>			
	</div>
	<div id="searchbar">
		<g:form method="GET" url="[controller:'content', action:'search']" name="searchForm">
		    <input
		 		onblur="this.style.color = '#CDE5C4';" 
				onfocus="this.style.color = '#48802C';this.value='';"
				type="text" accessKey="s" name="q" value="${params.q?.encodeAsHTML() ?: ''}"/>		
		</g:form>		
	</div>
</div>
