Tags :


<g:if test="${plugin.tags.size() > 0}">
    <span id="plugin-tags-${plugin.id}">    
        <g:each in="${plugin.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="/plugins/tag/${tag}">${tag}</a></g:each>
    </span>
</g:if>
<g:else>
    <span id="plugin-tags-${plugin.id}">    
    /     
</span>
</g:else>
<a href="#" id="plugin-tag-edit${plugin.id}"><img src="/img/edit-tag.png"  /></a>
<ul id="pluginTags${plugin.id}" data-plugin="${plugin.name}" style="display:none;">
    <g:each in="${plugin.tags}" var="tag"><li data-plugin="${plugin.name}">${tag}</li></g:each>    
</ul>
<r:script>
    var pluginTags = $('#pluginTags${plugin.id}')
    pluginTags.tagit({
        onTagAdded: onTagAddedFunction,
        onTagRemoved: onTagRemovedFunction,
        onTagClicked:onTagClickFunction,
        availableTags: tags
        
    });
    $('#plugin-tag-edit${plugin.id}').click(function() {
        $(this).hide()
        $('#plugin-tags-${plugin.id}').hide()
        $('#pluginTags${plugin.id}').show()
        $('#pluginTags${plugin.id} input').focus()
    })
</r:script>

