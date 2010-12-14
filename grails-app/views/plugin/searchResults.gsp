<%@ page import="org.grails.plugin.Plugin" %>
<%@ page import="org.springframework.util.ClassUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.SearchableUtils" %>
<%@ page import="org.codehaus.groovy.grails.plugins.searchable.lucene.LuceneUtils" %>
<html>
<head>
    <meta content="pluginNav" name="layout"/>
    <title>Plugin Search Results</title>
    <script type="text/javascript">
        var focusQueryInput = function() {
            document.getElementById("q").focus();
        }
    </script>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'content.css')}"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'plugins.css')}"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'ratings.css')}"/>
    <link rel="stylesheet" href="${createLinkTo(dir: 'css', file: 'search.css')}"/>
</head>
<body onload="focusQueryInput();">
<div id="main">
    <g:set var="haveQuery" value="${params.q?.trim()}"/>
    <g:set var="haveResults" value="${searchResult?.results}"/>
    <g:set var="query" value="${params.q?.encodeAsHTML()}"/>
    <div class="title">
        <span>
            <g:if test="${haveQuery && haveResults}">
                Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
                results for <strong>${query}</strong>
            </g:if>
		    <g:if test="${parseException}">
		        <p>Your query - <strong>${query}</strong> - is not valid.</p>
		    </g:if>
		    <g:elseif test="${haveQuery && !haveResults}">
		        <p>Nothing matched your query - <strong>${query}</strong></p>
		    </g:elseif>
        </span>
    </div>


    <g:if test="${haveResults}">
        <tmpl:pluginList plugins="${searchResult.results}" total="${searchResult.total}" pageParams="[q: params.q]" />
    </g:if>
</div>
</body>
</html>
