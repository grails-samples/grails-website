<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <feed:meta kind="rss" version="2.0" controller="plugin" action="latest" params="[format:'rss']"/>
    <feed:meta kind="atom" version="1.0" controller="plugin" action="latest" params="[format:'atom']"/>
    <rateable:resources />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ratings.css')}"/>

    <title>Plugins Portal</title>
    <meta content="pluginNav" name="layout"/>
</head>
<body>
    <tmpl:pluginList plugins="${currentPlugins}" total="${totalPlugins}" pageParams="[category: category]" />
</body>
</html>
