<head>
    <title><g:message code="website.browse.tags" default="Web Site Tags"></g:message></title>
</head>
<body>
    <div class="artifactControls">
        <div class="menuButton"><g:link class="list" action="list">Web Site List</g:link></div>
    </div>
    <g:if test="${tags}">
    <tags:tagCloud tags="${tags}" action="search" idProperty="tag"/>
    </g:if>
    <g:else>
    <p>No tags found</p>
    </g:else>
</body>
