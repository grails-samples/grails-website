<section class="search">
    <form action="/plugins/search" name="plugin-search" method="GET">
        <input type="search" id="q" name="q" value="${params.q?.encodeAsHTML()}" placeholder="Search for a plugin"/>
        <a href="#" id="search-button" class="zoom">Search</a>
    </form>
</section>
