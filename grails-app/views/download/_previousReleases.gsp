<section class="previousRelease">
    <div class="head">
        <h3>Download previous release</h3>
        <p>Looking for older version ? Select and download previous release.</p>
        <div class="previousSelect">
            <div class="dropdown dropdown-full" id="selectRelease">
                <a class="dropdown-toggle" data-toggle="dropdown">
                    <span id="current_download">Select a major version</span>
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a class="release_option">Select a major version</a></li>
                    <g:each in="${majorVersions}" var="version">
                    <li><a class="release_option">${version}</a></li>
                    </g:each>
                </ul>
            </div>
        </div>
    </div>
    <div id="release_container" class="release hide"></div>
</section>

<asset:script>
$(function() {
    $('.release_option').click(function() {
        $("#current_download").text($(this).text());
        if ($(this).text() == "Select a major version") {
            $('#release_container').addClass('hide');
        } else {
            $("#release_container").empty();
            $("#release_container").append(
                $('.majorVersion').has("h4:contains('" + $(this).text() + "')").clone()
            );
            $("#release_container .links a").addClass("btn light");
            $('#release_container').removeClass('hide');
        }
    });
});
</asset:script>
