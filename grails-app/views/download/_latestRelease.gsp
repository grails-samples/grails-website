<g:set var="download" value="${latestDownload ? latestDownload['download'][0] : null}"/>
<g:set var="binaries" value="${latestDownload ? latestDownload['binary'] : null}"/>
<g:set var="documentation" value="${latestDownload ? latestDownload['documentation'][0] : null}"/>

<section class="downloads">

    <h2>Download latest release
        <small>(<g:formatDate format="MM/dd/yyyy" date="${download.releaseDate.toDate()}"/>)</small>
    </h2>

    <p>Download Grails now and follow the easy installation instructions to get writing amazing web applications in minutes.</p>

    <p class="url">
        <input type="text" readonly="readonly" value="${binaries[0].mirrors[0][0].urlString}"/>
    </p>

    <div class="buttons">
        <div class="btn-group">
            <a href="${binaries[0].mirrors[0][0].urlString}" class="btn primary">Download Grails ${download.softwareVersion}</a>
            <a href="#" class="btn primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <g:each in="${binaries[0].mirrors[0]}" var="mirror" status="i">
                    <g:if test="${i > 0}">
                        <li class="divider"></li>
                    </g:if>
                    <li><g:link url="${mirror.urlString}">${mirror.name}</g:link></li>
                </g:each>
            </ul>
        </div>
    </div>

    <p class="links">
        <g:link url="${documentation.mirrors[0][0].urlString}" class="btn light">Documentation (zip)</g:link> |
        <g:link controller="learn" action="installation" class="btn light">Installation</g:link>
    </p>

</section>