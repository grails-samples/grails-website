<section class="previousRelease">
    <h3>Download previous release</h3>
    <div class="dropdown dropdown-full" id="selectRelease">
        <a class="dropdown-toggle" data-toggle="dropdown">
            <span id="current_download">Select a major version</span>
            <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <g:each in="${majorVersions}" var="version">
            <li><a class="release_option">${version}</a></li>
            </g:each>
        </ul>
    </div>
    <div id="release_container" class="release hide"></div>
</section>

<r:script>
$(function() {
    $('.release_option').click(function() {
        $("#release_container").empty();
        $("#release_container").append(
            $('.majorVersion').has("h3:contains('" + $(this).text() + "')").clone()
        );
        $("#release_container .links a").addClass("btn light");
        $('#release_container').removeClass('hide');
    });
});
</r:script>
