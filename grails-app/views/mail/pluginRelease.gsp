<html>
<head><title>${ plugin.title } ${ version } released</title></head>
<body>
    <p>A new version of ${ plugin.title } has been released. Please see the <a href="${ url }">plugin's portal page</a> for more information.</p>
    <g:if test="${ plugin.summary }">
        <h3>About the plugin</h3>
        <p>${ plugin.summary }</p>
    </g:if>
</body>
</html>