<head>
    <title><g:message code="website.browse.tags" default="Web Site Tags"></g:message></title>
</head>
<body>
    <div class="artifactControls">
        <div class="menuButton"><g:link class="list" action="list">Web Site List</g:link></div>
    </div>
    <tags:tagCloud tags="${tags}" action="search" idProperty="tag"/>
</body>
