<div class="aside">
    <aside class="notif">
        <p>Welcome to the Grails plugin portal. The place where you can find information about the latest plugins available for the Grails framework.</p>
        <span class="extra"></span>
    </aside>
    <aside class="filters">
        <h4>Popular filters</h4>
        <ul>
            <li <g:if test="${params.filter == 'featured'}">class="active"</g:if>><a href="/plugins?filter=featured">Featured</a></li>
            <li <g:if test="${params.filter == 'highest_voted'}">class="active"</g:if>><a href="/plugins?filter=highest_voted">Highest Voted</a></li>
            <li <g:if test="${params.filter == 'recently_updated'}">class="active"</g:if>><a href="/plugins?filter=recently_updated">Recently Updated</a></li>
            <li <g:if test="${params.filter == 'newest'}">class="active"</g:if>><a href="/plugins?filter=newest">Newest</a></li>
            <li class="last<g:if test="${params.filter == 'official'}"> active</g:if>"><a href="/plugins?filter=official">Supported by SpringSource</a></li>
        </ul>
    </aside>
    <aside class="tags">
        <h4>Popular tags</h4>
        <ul>
            <g:each in="${tags[0]}" var="tag" status="i">
                <li class="
                    <g:if test="${params.tag == tag.toString()}"> active</g:if>
                    <g:if test="${i == tags.size() - 1}"> last</g:if>
                "><a href="/plugins?tag=${tag.toString()}">${tag.toString().capitalize()}</a></li>
            </g:each>
        </ul>
        <ul>
            <g:each in="${tags[1]}" var="tag" status="i">
                <li class="
                    <g:if test="${params.tag == tag.toString()}"> active</g:if>
                    <g:if test="${i == tags.size() - 1}"> last</g:if>
                "><a href="/plugins?tag=${tag.toString()}">${tag.toString().capitalize()}</a></li>
            </g:each>
        </ul>
    </aside>

    <aside class="notif">
        <p>
            If you are interested in creating and distributing a plugin in the Grails central repository, take a look at
            the <a href="http://grails.org/Creating+Plugins" target="_blank">Publishing Plugins Guide</a>.
        </p>
        <p style="padding-top: 10px;">
            Are you ready to publish your plugin and need access to the Grails plugin repository,
            <a href="/plugins/submitPlugin">please fill out this form</a>.
        </p>
    </aside>
</div>