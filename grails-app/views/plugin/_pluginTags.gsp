Tags :
<ul id="pluginTags${plugin.id}" data-plugin="${plugin.name}">
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
</r:script>

