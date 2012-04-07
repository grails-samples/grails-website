
<g:each var='tag' in="${plugin.tags.sort()}">
    <span class="tag"><g:link action="browseByTag" params="[tagName: tag]">${tag?.encodeAsHTML()}</g:link>&nbsp;
    <g:if test="${!disabled}">
        <g:set var="imgTag"><img id="remove_${tag?.encodeAsHTML()}_tag_from_${plugin.id}" src="${resource(dir: 'images/famfamfam', file: 'delete.png')}"/></g:set>

        %{-- If logged in, we're going to attach the normal ajax click listener --}%
        <shiro:isLoggedIn>
            <g:remoteLink class="iconLink" update="pluginTags" controller="plugin" action="removeTag" id="${plugin.id}" params="[tagName: tag]">
                ${imgTag}</g:remoteLink>
        </shiro:isLoggedIn>
        %{-- If not logged in, we'll add a custom listener that will defer to login form --}%
        <shiro:isNotLoggedIn>
            ${imgTag}
            <script>
                YAHOO.util.Event.onDOMReady(function() {
                    // on show, put the dialog in the right place
                    YAHOO.util.Event.on("remove_${tag?.encodeAsJavaScript()}_tag_from_${plugin.id}", 'click', function() {
                        window.location = "${createLink(controller:'user', action:'login', params:[originalURI:request.forwardURI])}";
                    });
                });
            </script>
        </shiro:isNotLoggedIn>
    </g:if>
    </span>
</g:each>
