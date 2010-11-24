<head>
    <title>Plugins tagged with '${tagName}'</title>
    <meta content="pluginNav" name="layout"/>

    <rateable:resources />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ratings.css')}"/>

</head>
<body>
    <h2>Plugins with tag '${tagName}'</h2>
    <div id="currentPlugins">
        <g:each var="plugin" in="${currentPlugins}">
            <tmpl:pluginPreview plugin="${plugin}" />
        </g:each>
    </div>
    <div id="paginationPlugins">
        <g:paginate total="${totalPlugins}" params="[tagName:tagName]" next="&gt;" prev="&lt;"></g:paginate>
    </div>
</body>
</html>

