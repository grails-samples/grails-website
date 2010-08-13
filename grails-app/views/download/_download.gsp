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
            <g:form controller="download" action="downloadFile">
                <tr>
                    <td><strong>${file.title}</strong></td>
                    <td>
                        <g:select optionKey="id" optionValue="name" name="mirror" from="${file.mirrors}" />

                    </td>
                    <td width="70" class="downloadCell"> <g:submitButton name="Download" value="Download" /></td>
                </tr>
            </g:form>
        </g:each>

        <g:set var="docFile" value="${docDownload?.files?.iterator()?.next()}"></g:set>
        <g:if test="${docFile}">
            <g:form controller="download" action="downloadFile">
                <tr>
                    <td><strong>Documentation</strong></td>
                    <td>
                        <g:select optionKey="id" optionValue="name" name="mirror" from="${docFile.mirrors}" />

                    </td>
                    <td width="70" class="downloadCell"> <g:submitButton name="Download" value="Download" /></td>
                </tr>
            </g:form>
        </g:if>
    </table>

</cache:text>
