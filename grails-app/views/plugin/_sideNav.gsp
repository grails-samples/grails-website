<div class="aside">
    <aside class="filters">
        <h3>Filters<g:if test="${activeFilter != 'all'}">&nbsp;&nbsp;&nbsp;<a style="font-size: smaller;" href="/plugins?filter=all">clear filter</a></g:if></h3>
        <ul>
            <li <g:if test="${activeFilter == 'all'}">class="active"</g:if>>
                <a href="/plugins?filter=all">
                    <g:img dir="img/icons" file="plugin.png" />
                    All Plugins
                </a>
            </li>            
            <li <g:if test="${activeFilter == 'featured'}">class="active"</g:if>><a href="/plugins?filter=featured"><g:img dir="img/plugin" file="featured.png" /> Featured</a></li>
            <li <g:if test="${activeFilter == 'installed'}">class="active"</g:if>><a href="/plugins?filter=installed"><g:img dir="img/plugin" file="topInstalled.png" /> Top Installed</a></li>
            <li <g:if test="${activeFilter == 'popular'}">class="active"</g:if>><a href="/plugins?filter=popular"><g:img dir="img" file="star-on.png" /> Popular</a></li>
            <li <g:if test="${activeFilter == 'recentlyUpdated'}">class="active"</g:if>><a href="/plugins?filter=recentlyUpdated"><g:img dir="img/plugin" file="recentlyUpdated.png" /> Recently Updated</a></li>
            <li <g:if test="${activeFilter == 'newest'}">class="active"</g:if>><a href="/plugins?filter=newest"><g:img dir="img/plugin" file="newest.png" /> Newest</a></li>
            <li class="last<g:if test="${activeFilter == 'supported'}"> active</g:if>"><a href="/plugins?filter=supported"><g:img dir="img/plugin" file="supported.png" /> Plugin Collection</a></li>
            <li>
                <a href="/plugins/pending">
                    <g:img dir="img/icons" file="pending.png"/>
                    Pending Plugins
                </a>
            </li>                
        </ul>
    </aside>

    <aside class="tags">
        <h3>Popular tags<g:if test="${activeTag}">&nbsp;&nbsp;&nbsp;<a style="font-size: smaller;" href="/plugins?filter=all">clear tag</a></g:if></h3>
        <ul>
            <g:each in="${tags[0]}" var="tag" status="i">
                <li class="
                    <g:if test="${activeTag == tag}"> active</g:if>
                    <g:if test="${i == tags.size() - 1}"> last</g:if>
                "><a href="/plugins/tag/${tag}">${tag.capitalize()}</a></li>
            </g:each>
            <g:each in="${tags[1]}" var="tag" status="i">
                <li class="
                    <g:if test="${activeTag == tag}"> active</g:if>
                    <g:if test="${i == tags.size() - 1}"> last</g:if>
                "><a href="/plugins/tag/${tag}">${tag.capitalize()}</a></li>
            </g:each>
        </ul>
    </aside>

</div>
