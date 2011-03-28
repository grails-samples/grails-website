<g:setProvider library="yui"/>

<head>
    <title>${content?.title}</title>
    <meta content="subpage" name="layout" />
    <g:render template="wikiJavaScript"/>
</head>
<body>
    <div id="contentPane">
        <div id="wikiLastUpdated">Last updated by ${latest?.author?.login} <prettytime:display date="${content.lastUpdated}"/></div>
        <g:render template="viewActions" model="[content:content]" />
        <div id="editPane" style="margin-top:10px;">
            <g:if test="${ content?.deprecated }">
                <wiki:deprecated uri="${ content?.deprecatedUri }"/>
            </g:if>
            <wiki:text key="${content?.title}">
                ${content?.body}
            </wiki:text>
        </div>
    </div>

    <g:render template="previewPane"/>
</body>
</html>
