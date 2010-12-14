<div id="currentPlugins">
    <g:each var="plugin" in="${plugins}">
        <tmpl:pluginPreview plugin="${plugin}" />
    </g:each>
</div>
<div id="paginationPlugins">
    <g:paginate total="${total}" params="${pageParams}" next="&gt;" prev="&lt;"></g:paginate>
</div>

