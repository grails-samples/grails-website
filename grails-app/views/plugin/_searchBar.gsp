<div class="searchBox">
    <g:form name="pluginSearch" action="search">
        <input class="searchInput" type="text" name="q" value="${q?.encodeAsHTML()}"/>
        <input class="searchButton" type="image" src="${resource(dir:'images/new/plugins/Buttons', file:'search_btn.png')}" value="Search"/>
        or <g:link action="browseTags">browse tags</g:link>
    </g:form>
</div>
