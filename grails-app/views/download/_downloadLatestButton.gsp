<div class="buttons">
    <div class="btn-group">
        <a href="${downloadFile.mirrors[0].urlString}" class="btn primary">Download Grails ${softwareVersion}</a>
        <a href="#" class="btn primary dropdown-toggle" data-toggle="dropdown">
            <span class="caret"></span>
        </a>
        <ul class="dropdown-menu">
            <g:each in="${downloadFile.mirrors}" var="mirror" status="i">
                <g:if test="${i > 0}">
                    <li class="divider"></li>
                </g:if>
                <li><g:link url="${mirror.urlString}">${mirror.name}</g:link></li>
            </g:each>
        </ul>
    </div>
</div>
