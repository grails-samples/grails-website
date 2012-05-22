<section class="aside">
    <aside id="reference">
        <h3><img src="${resource(dir: 'img/aside', file: 'reference.png')}" alt="" title=""/>Instructions</h3>
        <ul>
            <li class="instructions">
                <p>
                    Each plugin goes through an approval process to be included in the maven repository.
                    Part of the process is the involvement of the community to offer suggestions and approval.
                </p>
                <p>Please fill out the form to the right and provide a description of the plugin and it's usages.</p>
            </li>
        </ul>
    </aside>
    <aside id="reference">
        <h3><img src="${resource(dir: 'img/aside', file: 'reference.png')}" alt="" title=""/>Links</h3>
        <ul>
            <li><a href="/plugins">Plugins</a></li>
            <li
                <g:if test="${actionName == 'submitPlugin'}">class="active"</g:if>
            ><a href="/plugins/submitPlugin">Submit a Plugin</a></li>
            <li
                <g:if test="${['pendingPlugins','showPendingPlugin'].contains(actionName)}">class="active"</g:if>
            ><a href="/plugins/pending">View Pending Plugins</a></li>
            <li><a href="http://grails.org/Creating+Plugins" target="_blank">Publishing Plugins Guide</a></li>
        </ul>
    </aside>
</section>