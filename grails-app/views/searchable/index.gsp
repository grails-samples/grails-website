<%@ page import="grails.util.GrailsNameUtils" %>
<%@ page import="org.grails.plugin.Plugin" %>
<%@ page import="org.grails.content.Content"%>
<%@ page import="org.springframework.util.ClassUtils" %>
<html>
<head>
    <meta content="master" name="layout"/>
    <title>Search Results</title>
    <script type="text/javascript">
        var focusQueryInput = function() {
            document.getElementById("q").focus();
        }
    </script>
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
                <g:else>
                    &nbsp;
                </g:else>
            </span>
        </div>

        <g:if test="${parseException}">
            <p>Your query - <strong>${query}</strong> - is not valid.</p>
        </g:if>
        <g:elseif test="${clauseException}">
            <p>Your query - <strong>${query}</strong> - cannot be handled, sorry. Be more restrictive with your wildcards, like '*'.</p>
        </g:elseif>
        <g:elseif test="${haveQuery && !haveResults}">
            <p>Nothing matched your query - <strong>${query}</strong></p>
        </g:elseif>
        <g:elseif test="${haveResults}">
            <div id="results" class="results">
                <g:each var="group" in="${searchResult.results}">
                <div class="resultGroup">
                    <h3>${group.key}</h3>

                    <g:each var="result" in="${group.value}" status="index">
                    <div class="result">

                        <g:set var="className" value="${result.title?.encodeAsHTML()}"/>

                        <div class="name">

                            %{--
                                The result may be either a WikiPage, Plugin, or Screencast object.  If it is a Plugin, we'll want to
                                link it properly to the Plugin domain.  Otherwise it gets treated like a normal WikiPage
                            --}%
                            <g:if test="${result instanceof Plugin}">
                                <g:link controller="plugin" action="show" params="${[name:result.name]}">${className}</g:link>
                                <g:set var="desc" value="${result.summary ?: 'No description'}"/>
                            </g:if>
                            <g:elseif test="${result instanceof Content}">
                                <g:link controller="content" id="${result.title}">${className}</g:link>
                                <g:set var="desc" value="${result.body}"/>
                            </g:elseif>
                            <g:elseif test="${result instanceof org.compass.core.lucene.LuceneResource}">
                                <a href="${resource(dir: 'doc/latest/guide', file: result.url[0].stringValue)}">${result.title[0].stringValue}</a>
                                <g:set var="desc" value="${searchResult.highlights[index] ?: result.body[0].stringValue}"/>
                            </g:elseif>
                            <g:else>
                                <g:set var="itemName" value="${GrailsNameUtils.getShortName(result.class.name)}"/>
                                <g:link controller="${GrailsNameUtils.getPropertyName(itemName)}"
                                        action="show" id="${result.id}">${itemName} > ${className}</g:link>
                                <g:set var="desc" value="${result.description ?: 'No description'}"/>
                            </g:else>
                        </div>

                        <div class="desc">
                            <text:summarize encodeAs="HTML" ><text:htmlToText><wiki:text>${desc}</wiki:text></text:htmlToText> </text:summarize>
                        </div>

                    </div>
                    </g:each>
                </div>
                </g:each>
            </div>

            <div>
                <div class="paging">
                    <g:if test="${haveResults}">
                        Page:
                        <g:set var="totalPages" value="${Math.ceil(searchResult.total / searchResult.max)}"/>
                        <g:if test="${totalPages == 1}"><span class="currentStep">1</span></g:if>
                        <g:else><g:paginate controller="content" action="search" params="[q: params.q]" total="${searchResult.total}" prev="&lt; previous" next="next &gt;"/></g:else>
                    </g:if>
                </div>
            </div>
        </g:elseif>
    </div>
</body>
</html>
