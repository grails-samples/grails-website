<head>
    <meta content="master" name="layout"/>
    <title>Grails Plugin Search Results</title>
    <r:require modules="plugin"/>
</head>

<body>
<g:set var="haveQuery" value="${params.q?.trim()}"/>
<g:set var="haveResults" value="${searchResult?.results}"/>
<g:set var="query" value="${params.q?.encodeAsHTML()}"/>

<div id="content" class="content-aside-2" role="main">

    <g:render template="sideNav" bean="${tags}"/>

    <div id="main" class="plugins">
        <g:render template="searchBar"/>

        <g:if test="${haveQuery && haveResults}">
            Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
            results for <strong>${query}</strong>
        </g:if>
        <g:if test="${parseException}">
            <p>Your query - <strong>${query}</strong> - is not valid.</p>
        </g:if>
        <g:elseif test="${clauseException}">
            <p>Your query - <strong>${query}</strong> - cannot be handled, sorry. Be more restrictive with your wildcards, like '*'.
            </p>
        </g:elseif>
        <g:elseif test="${haveQuery && !haveResults}">
            <p>Nothing matched your query - <strong>${query}</strong></p>
        </g:elseif>

    </div>

</div>

</body>
