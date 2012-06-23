<section class="previousRelease">
    <h3>Download previous release</h3>
    <div class="dropdown dropdown-full" id="selectRelease">
        <a class="dropdown-toggle" data-toggle="dropdown">
            <span id="current_download">Selected a previous release</span>
            <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <g:each in="${stableDownloads}" var="downloadMap">
                <li><a class="release_option"
                   data-binary-url="${downloadMap['binary'].mirrors[0][0].urlString}"
                   <g:if test="${downloadMap['documentation']?.mirrors[0]}">
                   data-documentation-url="${downloadMap['documentation'].mirrors[0][0].urlString}"
                   </g:if>
                   data-release-notes="TO COME SOON"
                >Release: ${downloadMap['download'].softwareVersion}
                    (<g:formatDate format="MM/dd/yyyy" date="${downloadMap['download'].releaseDate.toDate()}"/>)</a>
                </li>
            </g:each>
            <g:if test="${betaDownloads.size() > 0}">
                <li class="divider"></li>
                <g:each in="${betaDownloads}" var="downloadMap">
                    <li><a class="release_option"
                       data-binary-url="${downloadMap['binary'].mirrors[0][0].urlString}"
                       <g:if test="${downloadMap['documentation']?.mirrors}">
                       data-documentation-url="${downloadMap['documentation'].mirrors[0][0].urlString}"
                       </g:if>
                       data-release-notes="TO COME SOON"
                    >Beta Release: ${downloadMap['download'].softwareVersion}
                    (<g:formatDate format="MM/dd/yyyy" date="${downloadMap['download'].releaseDate.toDate()}"/>)</a></li>
                </g:each>
            </g:if>
        </ul>
    </div>
    <div id="release_container" class="release hide">
        <span class="extra"></span>
        <p id="release_notes">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud</p>
        <p class="links">
            <a id="release_binary" href="#" class="btn light">Distribution (zip)</a> |
            <a id="release_documentation" href="#" class="btn light">Documentation (zip)</a>
        </p>
    </div>
</section>

<script type="text/javascript">
    $(function() {
        $('.release_option').click(function() {
            $('#current_download').html($(this).html());
            $('#release_binary').attr('href', $(this).data('binary-url'));
            $('#release_documentation').attr('href', $(this).data('documentation-url'));
            $('#release_notes').html($(this).html());
            $('#release_container').removeClass('hide');
        });
    });
</script>
