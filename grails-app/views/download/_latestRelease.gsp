<%-- Don't use a variable name that matches a tag namespace! --%>
<g:set var="downloadDesc" value="${latestDownload ? latestDownload['download'] : null}"/>
<g:set var="binary" value="${latestDownload ? latestDownload['binary'][0] : null}"/>
<g:set var="documentation" value="${latestDownload ? latestDownload['documentation'][0] : null}"/>

<section class="downloads">

    <h2>Download latest release
        <small>(<g:formatDate format="dd MMM yyyy" date="${downloadDesc.releaseDate.toDate()}"/>)</small>
    </h2>

    <p>Download Grails now and follow the simple installation instructions to start writing amazing web applications in minutes.</p>

    <p class="url">
        <input type="text" readonly="readonly" value="${binary.mirrors[0].urlString}"/>
    </p>

    <g:render template="/download/downloadLatestButton" model="[downloadFile: binary, softwareVersion: downloadDesc.softwareVersion]"/>

    <p class="links">
        <g:link url="${documentation.mirrors[0].urlString}" class="btn light">Documentation (zip)</g:link> |
        <g:link controller="learn" action="installation" class="btn light">Installation</g:link>
    </p>

</section>
