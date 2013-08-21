            <p class="buttons">
                <g:if test="${plugin.documentationUrl}">
                    <a href="${plugin.documentationUrl.encodeAsHTML()}" target="_blank" class="btn blueLight doc"><i class="icon-book"></i>&nbsp;Documentation</a>
                </g:if>
                <g:if test="${plugin.scmUrl}">
                    <a href="${plugin.scmUrl.encodeAsHTML()}" target="_blank" class="btn blueLight source"><i class="icon-code"></i>&nbsp;Source</a>
                </g:if>
                <g:if test="${plugin.issuesUrl}">
                    <a href="${plugin.issuesUrl.encodeAsHTML()}" target="_blank" class="btn blueLight issues"><i class="icon-bug"></i>&nbsp;Issues</a>
                </g:if>
            </p>