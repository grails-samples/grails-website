<head>
    <title>Plugins</title>
    <meta content="pluginNav" name="layout"/>
</head>
<body>
    <h2>Plugin tags</h2>
    <g:if test="${tags}">
    <tags:tagCloud tags="${tags}" action="browseByTag" idProperty="tagName"/>
    </g:if>
    <g:else>
    <p>No tags found</p>
    </g:else>
</body>
</html>


