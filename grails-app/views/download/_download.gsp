<g:set var="downloadObj" value="${download.value}"/>
<g:set var="binDownload" value="${downloadObj ? downloadObj[0] : null}"/>
<g:set var="docDownload" value="${downloadObj ? downloadObj[1] : null}"/>

<h3>Current ${download.key} release: ${binDownload?.softwareVersion}</h3> 
<ul>
    <li><a href="${binDownload?.releaseNotes}">Release Notes</a></li>
</ul>

<cache:text id="${'downloadPage_'+binDownload?.softwareVersion}">
    <table class="download-table">
        <tr><th>Distribution</th><th>Mirror</th></tr>
        <g:each var="file" in="${binDownload?.files}">
            <tr>
                <td><strong>${file.title}</strong></td>
                <td>
                    <g:form controller="download" action="downloadFile">
                        <div style="display: inline-block;">
                        <g:select optionKey="id" optionValue="name" name="mirror" from="${file.mirrors}" />
                        </div>
                        <div style="display: inline-block; float: right;">
                        <g:submitButton name="Download" value="Download" />
                        </div>
                    </g:form>
                </td>
                <td style="width: 5em">
                    <a class="ajaxLink" href="#">Show URL</a> 
                </td>
            </tr>
            <tr><td colspan="3"><span style="display: none;"></span></td></tr>
        </g:each>

        <g:set var="docFile" value="${docDownload?.files?.iterator()?.next()}"></g:set>
        <g:if test="${docFile}">
            <tr>
                <td><strong>Documentation</strong></td>
                <td>
                    <g:form controller="download" action="downloadFile">
                        <div style="display: inline-block;">
                        <g:select optionKey="id" optionValue="name" name="mirror" from="${docFile.mirrors}" />
                        </div>
                        <div style="display: inline-block; float: right;">
                        <g:submitButton name="Download" value="Download" />
                        </div>
                    </g:form>
                </td>
                    <td style="width: 5em">
                        <a class="ajaxLink" href="#">Show URL</a> 
                    </td>
            </tr>
            <tr><td colspan="3"><span style="display: none;"></span></td></tr>
        </g:if>
    </table>

</cache:text>
