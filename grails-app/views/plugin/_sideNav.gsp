<div class="aside">
    <aside class="notif">
        <p>Welcome to the Grails plugin portal, where you can find out about all the publicly available Grails plugins.</p>
        <span class="extra"></span>
    </aside>
    <aside class="filters">
        <h4>Filters<g:if test="${activeFilter != 'all'}">&nbsp;&nbsp;&nbsp;<a style="font-size: smaller;" href="/plugins?filter=all">clear filter</a></g:if></h4>
        <ul>
            <li <g:if test="${activeFilter == 'featured'}">class="active"</g:if>><a href="/plugins?filter=featured">Featured</a></li>
            <li <g:if test="${activeFilter == 'installed'}">class="active"</g:if>><a href="/plugins?filter=installed">Top Installed</a></li>
            <li <g:if test="${activeFilter == 'popular'}">class="active"</g:if>><a href="/plugins?filter=popular">Popular</a></li>
            <li <g:if test="${activeFilter == 'recentlyUpdated'}">class="active"</g:if>><a href="/plugins?filter=recentlyUpdated">Recently Updated</a></li>
            <li <g:if test="${activeFilter == 'newest'}">class="active"</g:if>><a href="/plugins?filter=newest">Newest</a></li>
            <li class="last<g:if test="${activeFilter == 'supported'}"> active</g:if>"><a href="/plugins?filter=supported">Supported by SpringSource</a></li>
        </ul>
    </aside>

    <aside class="tags">
        <h4>Popular tags<g:if test="${activeTag}">&nbsp;&nbsp;&nbsp;<a style="font-size: smaller;" href="/plugins?filter=all">clear tag</a></g:if></h4>
        <ul>
            <g:each in="${tags[0]}" var="tag" status="i">
                <li class="
                    <g:if test="${activeTag == tag}"> active</g:if>
                    <g:if test="${i == tags.size() - 1}"> last</g:if>
                "><a href="/plugins/tag/${tag}">${tag.capitalize()}</a></li>
            </g:each>
        </ul>
        <ul>
            <g:each in="${tags[1]}" var="tag" status="i">
                <li class="
                    <g:if test="${activeTag == tag}"> active</g:if>
                    <g:if test="${i == tags.size() - 1}"> last</g:if>
                "><a href="/plugins/tag/${tag}">${tag.capitalize()}</a></li>
            </g:each>
        </ul>
    </aside>

    <aside class="alert alert-block">
        <p>
            Interested in your own plugin appearing here? Take a look at
            the <a href="http://grails.org/Creating+Plugins" target="_blank">Publishing Plugins Guide</a>
            and when you're ready, <a href="/plugins/submitPlugin">please fill out this form</a>.
        </p>
        <p style="padding-top: 10px;">
            <strong>NEW:</strong> Get involved by voicing your opinion on new plugins waiting to be approved in the
            <a href="/plugins/pending">Pending Plugin</a> portal.
        </p>
    </aside>
</div>
