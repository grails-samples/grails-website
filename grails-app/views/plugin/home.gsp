<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Plugins Portal</title>
    <meta content="pluginNav" name="layout"/>
    <content tag="pageCss">
        <feed:meta kind="rss" version="2.0" controller="plugin" action="latest" params="[format:'rss']"/>
        <feed:meta kind="atom" version="1.0" controller="plugin" action="latest" params="[format:'atom']"/>
    </content>
</head>
<body>
    <!-- IMPORTANT: DO NOT delete the link below, it is commented out, but used for plugin resolution -->
    <!-- <g:link url="http://plugins.grails.org/.plugin-meta" absolute="true">.plugin-meta</g:link> -->
    <!-- <g:link url="http://grails.org/.plugin-meta" absolute="true">.plugin-meta</g:link> -->    
    <g:render template="/common/messages" model="[message: message]"/>
    <tmpl:pluginList plugins="${currentPlugins}" total="${totalPlugins}" pageParams="[category: category]" />
</body>
</html>
