<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <feed:meta kind="rss" version="2.0" controller="plugin" action="latest" params="[format:'rss']"/>
    <feed:meta kind="atom" version="1.0" controller="plugin" action="latest" params="[format:'atom']"/>

    <title>Plugins Portal</title>
    <meta content="pluginNav" name="layout"/>
    <content tag="pageCss">
        <rateable:resources />
    </content>
</head>
<body>
    <tmpl:pluginList plugins="${currentPlugins}" total="${totalPlugins}" pageParams="[category: category]" />
</body>
</html>
