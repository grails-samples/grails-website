<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>All Wiki Pages</title>
    <meta content="subpage" name="layout"/>
</head>
<body>
    <h2>All Wiki Pages (${totalWikiPages} Total)</h2>
    <p>Pages with a strikethrough have been deprecated.</p>
    <div id="wikiPages">
        <g:each var="entry" in="${wikiPages}">
            <div class="pageGroup">
                    
                <h3><a name="${entry.key}">${entry.key}</a></h3>
                <ul>
                    <g:each var="wikiPage" in="${entry.value}">
                        <g:if test="${wikiPage.title}">
                            <g:if test="${wikiPage.deprecated}">
                            <li class="deprecated">
                            </g:if>
                            <g:else>
                            <li>
                            </g:else>
                            <g:link controller="wikiPage" action="show" id="${wikiPage.id}">${wikiPage.title?.encodeAsHTML()}</g:link>
                            </li>
                        </g:if>									
                    </g:each>						
                </ul>
            </div>
        </g:each>
    </div>
</body>
</html>

