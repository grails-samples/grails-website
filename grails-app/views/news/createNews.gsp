<%@ page import="org.grails.wiki.WikiPage" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Create News</title>
    <meta content="subpage" name="layout" />
</head>
<body>
    <g:render template="newsForm" model="${pageScope.variables}"></g:render>
</body>
</html>
