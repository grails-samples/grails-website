<div class="aside">
    <aside class="notif">
        <p>Welcome to the Grails plugin portal. The place where you can find information about the latest plugins available for the Grails framework.</p>
        <span class="extra"></span>
    </aside>
    <aside class="filters">
        <h4>Popular filters</h4>
        <ul>
            <li><a href="#">Featured</a></li>
            <li><a href="#">Top Installed</a></li>
            <li><a href="#">Highest Voted</a></li>
            <li><a href="#">Recently Updated</a></li>
            <li><a href="#">Newest</a></li>
            <li class="last"><a href="#">Supported by SpringSource</a></li>
        </ul>
    </aside>
    <aside class="tags">
        <h4>Popular tags</h4>
        <ul>
            <g:each in="${tags[0]}" var="tag" status="i">
                <li<g:if test="${i == tags.size() - 1}"> class="last"</g:if>><a href="">${tag.toString().capitalize()}</a></li>
            </g:each>
        </ul>
        <ul>
            <g:each in="${tags[1]}" var="tag" status="i">
                <li<g:if test="${i == tags.size() - 1}"> class="last"</g:if>><a href="">${tag.toString().capitalize()}</a></li>
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