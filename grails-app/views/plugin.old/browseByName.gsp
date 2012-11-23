<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <feed:meta kind="rss" version="2.0" controller="plugin" action="latest" params="[format:'rss']"/>
    <feed:meta kind="atom" version="1.0" controller="plugin" action="latest" params="[format:'atom']"/>

    <title>Grails Plugins</title>
    <meta content="pluginNav" name="layout"/>
</head>
<body>

    <cache:block id="allPluginList">
        <div id="contentWindowTop"></div>
        <div id="contentBody">
            <h2>All Plugins (${totalPlugins} Total)</h2>
            <div id="currentPlugins">
                <g:each var="entry" in="${currentPlugins}">
                    <div class="pluginGroup">
                            
                        <h3><a name="${entry.key}">${entry.key}</a></h3>
                        <ul>
                            <g:each var="plugin" in="${entry.value}">
                                <g:if test="${plugin.name}">
                                    <li><g:link action="show" params="${[name:plugin.name]}">${plugin.name?.encodeAsHTML()}</g:link> - <wiki:shorten text="${plugin.title}" length="50" /></li>
                                </g:if>									
                            </g:each>						
                        </ul>
                    </div>
                </g:each>
            </div>
        </div>
        <div id="contentFooter"></div>
	
    </cache:block>
</body>
</html>
