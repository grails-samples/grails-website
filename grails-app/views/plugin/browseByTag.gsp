<head>
    <title>Plugins tagged with '${tagName}'</title>
    <meta content="pluginNav" name="layout"/>
</head>
<body>
    <h2>Plugins with tag '${tagName}'</h2>
    Showing <strong>${offset + 1}</strong> - <strong>${offset + currentPlugins.size()}</strong> of <strong>${totalPlugins}</strong>
    <tmpl:pluginList plugins="${currentPlugins}" total="${totalPlugins}" pageParams="[tagName: tagName]" />
</body>
</html>

