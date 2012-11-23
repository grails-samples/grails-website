<form class="search" action="/plugins/search" name="plugin-search" method="GET">
    <input type="text" id="q" name="q" value="${params.q?.encodeAsHTML()}" placeholder="Search for a plugin"/>
    <a href="#" id="search-button" class="btn btn-search">
        <i class="icon-search icon-white"></i>
    </a>
</form>
