<asset:script>
    var tagsInitialized = false;
    var tags = [
        ${allTags.collect { "\"$it\""}.join(',')}
    ]

    var onTagAddedFunction =  function(event, tag) {
        if(${request.user == null} && tagsInitialized) {
            window.location = "${createLink(controller:'user', action:'login')}";
        }
        else if(tagsInitialized){
            var link = "${createLink(action:"addTag")}";
            var label = pluginTags.tagit('tagLabel', tag)
            var plugin = $("#" + this.id).data("plugin")
            $.ajax({url:link, type:"POST",data: {tag: label, id: plugin}})
        }
    }
    var onTagRemovedFunction =  function(event, tag) {
        if(${request.user == null} && tagsInitialized) {
            window.location = "${createLink(controller:'user', action:'login')}";
        }
        else if(tagsInitialized){
            var link = "${createLink(action:"removeTag")}";
            var label = pluginTags.tagit('tagLabel', tag)
            var plugin = $("#" + this.id).data("plugin")
            $.ajax({url:link, type:"POST",data: {tag: label, id: plugin}})

        }
    }    
    var onTagClickFunction = function(event, tag) {
            var label = pluginTags.tagit('tagLabel', tag)
            var link = "/plugins/tag/" + label;

            window.location = link;
    }     
</asset:script>