<%@ page import="grails.util.GrailsNameUtils" %>
<%@ page import="org.grails.plugin.Plugin" %>
<%@ page import="org.grails.content.Content"%>
<%@ page import="org.springframework.util.ClassUtils" %>
<html>
<head>
    <meta content="master" name="layout"/>
    <title>Search Results</title>
    <r:script type="text/javascript">
$(document).ready(function() {
    $("#quickSearch input[name='q']").val('${query.encodeAsJavaScript() ?: ''}');
    $("#quickSearch input[name='q']").focus();
});
    </r:script>
</head>
<body>

<div id="content" class="content-single" role="main">
    <section id="main" class="items search">
        <g:set var="haveQuery" value="${query}"/>
        <g:set var="resultCount" value="${searchResult?.results.inject(0) { tot, entry -> tot + entry.value.size() } }"/>
        <div class="title">
            <span>
                <g:if test="${haveQuery && resultCount}">
                    Showing <strong>${searchResult.offset + 1}</strong> - <strong>${resultCount + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
                    results for <strong>${query?.encodeAsHTML()}</strong>
                </g:if>
                <g:else>
                    &nbsp;
                </g:else>
            </span>
        </div>

        <g:if test="${parseException}">
            <p>Your query - <strong>${query?.encodeAsHTML()}</strong> - is not valid.</p>
        </g:if>
        <g:elseif test="${clauseException}">
            <p>Your query - <strong>${query?.encodeAsHTML()}</strong> - cannot be handled, sorry. Be more restrictive with your wildcards, like '*'.</p>
        </g:elseif>
        <g:elseif test="${haveQuery && !resultCount}">
            <p>Nothing matched your query - <strong>${query?.encodeAsHTML()}</strong></p>
        </g:elseif>
        <g:elseif test="${resultCount}">
            <div id="results" class="results">
                <g:each var="group" in="${searchResult.results}">
                <g:if test="${group.value?.size()}">
                <div class="resultGroup">
                    <h3>${group.key}</h3>

                    <g:each var="result" in="${group.value}" status="index">
                    <article class="item result">

                        <g:set var="className" value="${result.title?.encodeAsHTML()}"/>

                        <div class="name">

                            %{--
                                The result may be either a WikiPage, Plugin, or Screencast object.  If it is a Plugin, we'll want to
                                link it properly to the Plugin domain.  Otherwise it gets treated like a normal WikiPage
                            --}%
                            <g:if test="${result instanceof Plugin}">
                                <h4><g:link uri="/plugin/${result.name}">${className}</g:link></h4>
                                <g:set var="desc" value="${result.summary ?: 'No description'}"/>
                            </g:if>
                            <g:elseif test="${result instanceof Content}">
                                <h4><g:link controller="content" id="${result.title}">${className}</g:link></h4>
                                <g:set var="desc" value="${result.body}"/>
                            </g:elseif>
                            <g:elseif test="${result instanceof org.compass.core.lucene.LuceneResource}">
                                <h4><a href="${resource(dir: 'doc/latest', file: result.url[0].stringValue)}">${result.title[0].stringValue}</a>
                                <g:set var="desc" value="${searchResult.highlights[index] ?: result.body[0].stringValue}"/></h4>
                            </g:elseif>
                            <g:else>
                                <g:set var="itemName" value="${GrailsNameUtils.getShortName(result.class.name)}"/>
                                <h4><g:link controller="${GrailsNameUtils.getPropertyName(itemName)}"
                                        action="show" id="${result.id}">${itemName} > ${className}</g:link></h4>
                                <g:set var="desc" value="${result.description ?: 'No description'}"/>
                            </g:else>
                        </div>

                        <div class="desc">
                            <text:summarize length="250" encodeAs="HTML" ><text:htmlToText><wiki:text>${desc}</wiki:text></text:htmlToText> </text:summarize>
                        </div>

                    </article>
                    </g:each>
                </div>
                </g:if>
                </g:each>
            </div>

            <div>
                <div class="pager">
                    <g:if test="${resultCount}">
                        <g:set var="totalPages" value="${Math.ceil(searchResult.total / searchResult.max)}"/>
                        <g:if test="${totalPages > 1}">
                        <g:paginate controller="content" action="search" params="[q: query]" total="${searchResult.total}" prev="&lt; previous" next="next &gt;"/>
                        </g:if>
                    </g:if>
                </div>
            </div>
        </g:elseif>
    </section>

</div>

</body>
</html>
