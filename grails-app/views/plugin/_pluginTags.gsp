<span>Tags:</span>
<span id="plugin-tags-${plugin.id}">
    <g:if test="${plugin.tags.size() > 0}">
        <g:each in="${plugin.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="/plugins/tag/${tag}">${tag}</a></g:each>
    </g:if>
    <g:else>
        /
    </g:else>
</span>

<g:if test="${enableEdit}">
<g:if test="${request.user != null}">
    <a href="#" id="plugin-tag-edit${plugin.id}"><img src="/img/icons/edit-tag.png"  /></a>
</g:if>
<g:else>
    <g:link controller="user" action="login" params="[targetUri:request.forwardURI]"><img src="/img/icons/edit-tag.png"  /></g:link>
</g:else>
<ul id="pluginTags${plugin.id}" data-plugin="${plugin.name}" style="display:none;">
    <g:each in="${plugin.tags}" var="tag"><li data-plugin="${plugin.name}">${tag}</li></g:each>    
</ul>
<asset:script>
    var pluginTags = $('#pluginTags${plugin.id}')
    pluginTags.tagit({
        onTagAdded: onTagAddedFunction,
        onTagRemoved: onTagRemovedFunction,
        onTagClicked:onTagClickFunction,
        availableTags: tags
        
    });

    <g:if test="${request.user != null}">
        $('#plugin-tag-edit${plugin.id}').click(function() {
            $('#pluginTags${plugin.id} input').focus();
            $('#plugin-tags-${plugin.id}').hide();
            $('#pluginTags${plugin.id}').show();
            return false;
        });
        var timer;
        $('#pluginTags${plugin.id} input').blur(function(){
            $('#pluginTags${plugin.id} input').data("focus", "false");
            if (timer)
                clearTimeout(timer);
            timer = setInterval(function(){
                if ($('#pluginTags${plugin.id} input').data("focus") == "false") {
                    $('#plugin-tag-edit${plugin.id}').show();
                    $('#plugin-tags-${plugin.id}').show();
                    $('#pluginTags${plugin.id}').hide();
                    if ($("#pluginTags${plugin.id} li").length == 0) {
                        $('#plugin-tags-${plugin.id}').text("/");
                    } else {
                        var str = $("#pluginTags${plugin.id} li .tagit-label").map(function() {
                            return "<a href='/plugins/tag/" + $(this).text() + "'>" + $(this).text() + "</a>";
                        }).get().join(', ');
                        $('#plugin-tags-${plugin.id}').html(str);
                    }
                }
                clearTimeout(timer);
            },400);
        }).focus(function() {
            $('#pluginTags${plugin.id} input').data("focus", "true");
            $('#plugin-tag-edit${plugin.id}').hide();
            $('#plugin-tags-${plugin.id}').hide();
            $('#pluginTags${plugin.id}').show();
        });

    </g:if>
</asset:script>
</g:if>
